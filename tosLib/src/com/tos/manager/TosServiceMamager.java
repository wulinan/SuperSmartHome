package com.tos.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.tos.interfaces.Device;
import com.tos.utils.Command;

public class TosServiceMamager {
	private String serverIp = "127.0.0.1";
	private int serverPort = 2016;
	private boolean foundServer = false;
	
    private Socket client;
	private PrintWriter out;
	protected String format = "%s#%s#%s#%s";
	
	private ReadLineThread readLineThread;
	
	private static TosServiceMamager instance = new TosServiceMamager();
	
	//用于心跳
	ScheduledExecutorService heartBeatService = Executors
              .newSingleThreadScheduledExecutor();
	
	public static TosServiceMamager getInstance(){
		return instance;
	}
	
	
	public TosServiceMamager(){
		try {
			client = new Socket(serverIp, serverPort);
			out = new PrintWriter(client.getOutputStream(), true);
			this.readLineThread = new ReadLineThread(client);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	HashMap<String, Device> uuidToDevice = new HashMap<>();
	
	
	/**
	 * 注册一个设备实例
	 * //设计的msg为: (标识位)#消息ID#消息类型#(消息长度)#设备ID#消息体#(校验码)#(标识位)
		//打括号的先不用，真正的消息格式如下：     
		//消息ID#消息类型#设备ID#消息体
		//例子：register#command#0#type
	 * @param type
	 * @param device
	 * @return
	 */
	public String registerDevice(DeviceType type, Device device){
		String cmd = String.format(format,Command.Register.toCmd(),"command",0,type);
		uuidToDevice.put("0", device);
		out.println(cmd);
		return null;
	} 
	
	/**
	 * 发现服务器ip
	 * @return
	 */
	public boolean findServer(){
		return false;
	}
	
	public void handleMsg(String msg){
		String[] cmds = msg.split("#");
		switch (Command.getCmd(cmds[0])) {
		case Register:
			Device device= uuidToDevice.get("0");
			uuidToDevice.put(cmds[2], device);
			System.out.println("uuid="+cmds[2]);
			device.registered(msg);
			//开始注册心跳
			startHeartBeat(device, cmds[2]);
			break;
		
			case HeartBeat:
				System.out.println("get heart beat resopnse:"+cmds[2]+"   " + cmds[3]);
				break;

		default:
			break;
		}
	}
	
	public void startHeartBeat(Device device,String uuid){
		 Runnable runnable = new Runnable() {
		      public void run() {
		        // task to run goes here
		    	String cmd = String.format(format, Command.HeartBeat.toCmd(),"command",
		    			uuid,device.heartBeat());
		    	out.println(cmd);
		        System.out.println("send heart beat uuid = "+uuid);
		      }
		    };
		  heartBeatService.scheduleAtFixedRate(runnable, 0, device.getHeartbeatInterval(), TimeUnit.SECONDS);
		   
	}
}

//用于监听服务器端向客户端发送消息线程类
class ReadLineThread extends Thread{
	private BufferedReader buff;
	Socket client ;
	public ReadLineThread(Socket client){
		try{
			this.client = client;
			buff = new BufferedReader(new InputStreamReader(client.getInputStream()));
			start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void run(){
		try{
			while(true){
				String result = buff.readLine();
				if(result == null || result.isEmpty()){
					
				}else {
					TosServiceMamager.getInstance().handleMsg(result);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}