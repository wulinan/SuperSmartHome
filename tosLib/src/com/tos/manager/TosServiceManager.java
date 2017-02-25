package com.tos.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.tos.interfaces.Device;
import com.tos.utils.Command;
import com.tos.utils.DummyDB;

import com.tos.utils.Broadcast;
import com.tos.utils.BroadcastListener;

public class TosServiceManager {
	private String serverIp = null;
	private int serverPort = 2016;
	private boolean foundServer = false;
	
    private Socket client;
	private PrintWriter out;
	protected String format = "%s#%s#%s#%s";
	
	private ReadLineThread readLineThread;
	
	private static TosServiceManager instance = new TosServiceManager();
	
	//用于心跳
	ScheduledExecutorService heartBeatService = Executors
              .newSingleThreadScheduledExecutor();
	
	private int serverUDPPort = 2017;// udp端口
	private int clientUDPPort = 2018;// 客户端设备端口
	private Broadcast broadcast = new Broadcast(clientUDPPort, serverUDPPort);

    private List<String> cached = new ArrayList<>();
	ScheduledFuture<?> scheduledGetServerIpTask;
//
	public static TosServiceManager getInstance(){
		return instance;
	}
	
	public  String dir = ".";

	public TosServiceManager(){

		broadcast.registerListener(new BroadcastListener() {
			@Override
			public void messageArrived(String msg) {
                if(serverIp==null){
                System.out.println("［debug］find server: "+msg);
                String[] msgs= msg.split(":");
                String ip = msgs[0];
                int port = Integer.parseInt(msgs[1]);
                
                    serverIp = ip;
                    serverPort=port;
                    startSocket();
                }
			}
		});
		broadcast.startReceive();
        broadcast.sendData("query_ip_port");

	}
	
	
	public void startSocket(){
		try {
			System.out.println("-------------startSocket-------------");
			client = new Socket(serverIp, serverPort);
			out = new PrintWriter(client.getOutputStream(), true);
			this.readLineThread = new ReadLineThread(client);
            if(!cached.isEmpty()){
                for(String cmd :cached){
                    out.println(cmd);
                }
            }
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
	public String registerDevice(DeviceType type, Device device, int index){
		DummyDB db = DummyDB.getInstance();
		if(db.containDevice(device.getClass(), index)){
			String uuid = db.getDeviceUuid(device.getClass(), index);
			System.out.println("online!!!!"+uuid);
			startHeartBeat(device, uuid);
		}else{
			String cmd = String.format(format,Command.Register.toCmd(),"command",0,type);
			uuidToDevice.put("0", device);
            if(serverIp==null){
                cached.add(cmd);

            }else {
                out.println(cmd);
            }
		}
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
			String uuid = cmds[2];
			//开始注册心跳
			DummyDB db = DummyDB.getInstance();
			db.writeToDB(device.getClass(), 0, uuid);
			startHeartBeat(device, cmds[2]);
			break;
		
			case HeartBeat:
				System.out.println("get heart beat resopnse:"+cmds[2]+"   " + cmds[3]);
				break;

		default:
			break;
		}
	}
	
	public void startHeartBeat(final Device device,final String uuid){
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

	public  void  setWorkDir(String dir){
        DummyDB.setDir(dir);
        this.dir = dir;
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
					TosServiceManager.getInstance().handleMsg(result);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
