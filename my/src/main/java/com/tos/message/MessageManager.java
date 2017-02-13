package com.tos.message;

import java.net.Socket;
import java.util.HashMap;

import com.tos.enums.Command;
import com.tos.module.driver.ServerThread;
import com.tos.module.driver.SocketsManager;

public class MessageManager {
	private static MessageManager instance = new MessageManager();
	
	HashMap<String, MessageHandler> handlerMap = new HashMap<String, MessageHandler>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put(Command.Register.toCmd(), new RegisterHandler());   
			put(Command.HeartBeat.toCmd(), new CommonMessageHanlder());
			put(Command.OffLine.toCmd(), new CommonMessageHanlder());
			put(Command.OnLine.toCmd(), new CommonMessageHanlder());
		}
	};
	private MessageManager(){
		
	}
	
	public static MessageManager getInsatnce(){
		return instance;
	}
	
	/**
	 * 接受socket发来的数据
	 * @param msg
	 */
	public void handleSocketMessage(String uuid,ServerThread socket,String msg){
		//设计的msg为: (标识位)#消息ID#消息类型#(消息长度)#设备ID#消息体#(校验码)#(标识位)
		//打括号的先不用，真正的消息格式如下：     
		//消息ID#消息类型#设备ID#消息体
		//例子：register#command#0#type
		//设备ID不知道的时候，填0
		System.out.printf("handle uuid=%s,ip=%s,%s\n",uuid,socket.getClient().getInetAddress().getHostName(),msg);
		String[] commands = msg.split("#");
		handlerMap.get(commands[0]).handleMsg(uuid, socket, msg);
		
	}
	
	/**
	 * 接受用http发来的数据，json格式
	 * @param jsonMsg
	 */
	public void handleHttpMessage(String jsonMsg){
		
	}
	/**
	 * 发送消息
	 * @param uuid
	 * @param msg
	 */
	public void sendMessage(String uuid,Socket socket, String msg){
		SocketsManager.getInstance();
	}
	
}
