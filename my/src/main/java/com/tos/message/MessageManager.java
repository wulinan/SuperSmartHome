package com.tos.message;

import java.util.HashMap;

import com.tos.enums.Event;
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
			put(Event.Register.toCmd(), new RegisterHandler());   
			put(Event.HeartBeat.toCmd(), new CommonMessageHanlder());
			put(Event.OffLine.toCmd(), new CommonMessageHanlder());
			put(Event.OnLine.toCmd(), new CommonMessageHanlder());
			put(Event.Query.toCmd(), new QueryResultMessageHandler());
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
		try {
			// 设计的msg为: (标识位)#消息ID#消息类型#(消息长度)#设备ID#消息体#(校验码)#(标识位)
			// 打括号的先不用，真正的消息格式如下：
			// 消息ID#消息类型#设备ID#消息体
			// 例子：register#command#0#type
			// 设备ID不知道的时候，填0
			System.out.printf("handle uuid=%s,ip=%s,%s\n", uuid, socket.getClient().getInetAddress().getHostName(),
					msg);
			String[] commands = msg.split("#");
			if (uuid == null) {// 如果uuid为空并不代表设备没有注册过，需要分析消息头中的设备ID是否不为0.
				// 如果为0，说明是初次接入，则进入注册程序，否则说明设备已经注册，重新启动。系统已有该设备的uuid，需要更新相应存储内容。
				if (commands[2].equals("0")) {
					new RegisterHandler().handleMsg(null, socket, msg);
					return;
				} else {
					uuid = commands[2];
					SocketsManager.getInstance().putUuidToSocktes(uuid, socket);
				}
			}
			handlerMap.get(commands[0]).handleMsg(uuid, socket, msg);
		} catch (Exception e) {
			System.out.println(String.format("----------handle msg[%s] error-----------", msg));
			e.printStackTrace();

		}
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
	public void sendMessage(String uuid,ServerThread socket, String msg){
		SocketsManager.getInstance().sendToSocket(uuid, socket, msg);
	}
	
}
