package com.tos.message;

import java.util.HashMap;
import java.util.logging.Logger;

import com.tos.enums.Event;
import com.tos.module.driver.IServerThread;
import com.tos.module.driver.MQTTManager;
import com.tos.module.driver.MQTTServerThread;
import com.tos.module.driver.SocketServerThread;
import com.tos.module.driver.SocketsManager;
import com.tos.utils.LogManager;
import com.tos.utils.Message;

public class MessageManager {
	private static final Logger logger = LogManager.getLogger(MessageManager.class);
	
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
	public void handleSocketMessage(String uuid,IServerThread socket,String msg){
		try {
			// 设计的msg为: (标识位)#消息ID#消息类型#(消息长度)#设备ID#消息体#(校验码)#(标识位)
			// 打括号的先不用，真正的消息格式如下：
			// 消息ID#消息类型#设备ID#消息体
			// 例子：register#command#0#type
			// 设备ID不知道的时候，填0
			if(socket instanceof SocketServerThread){
			logger.finer(String.format("handle uuid=%s,ip=%s,%s\n", uuid, ((SocketServerThread)socket).getClient().getInetAddress().getHostName(),
					msg));
			}
			
			Message message = Message.fromJson(msg);
			if (uuid == null) {// 如果uuid为空并不代表设备没有注册过，需要分析消息头中的设备ID是否不为0.
				// 如果为0，说明是初次接入，则进入注册程序，否则说明设备已经注册，重新启动。系统已有该设备的uuid，需要更新相应存储内容。
				if (message.getDevice_id().equals("0")) {
					new RegisterHandler().handleMsg(null, socket, message);
					return;
				} else {
					uuid = message.getDevice_id();
					SocketsManager.getInstance().putUuidToSocktes(uuid, socket);
				}
			}
			System.out.println();
			handlerMap
			.get(message.getOperation())
			.handleMsg(uuid, socket, message);
		} catch (Exception e) {
			logger.warning(String.format("----------handle msg[%s] error-----------", msg));
			e.printStackTrace();

		}
	}
	
	public void handleMQTTMessage(String uuid, MQTTServerThread serverThread, Message message){
		handlerMap.get(message.getDevice_id()).handleMsg(uuid, serverThread, message);
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
	public void sendMessage(String uuid,IServerThread socket, String msg){
		if(socket instanceof SocketServerThread){
			SocketsManager.getInstance().sendToClient(uuid, socket,msg);
		}else if (socket instanceof MQTTServerThread) {
			MQTTManager.getInstance().sendToClient(uuid, socket,msg);
		}
	}
	
	public void putUuidToThread(String uuid, IServerThread socket) {
		if(socket instanceof SocketServerThread){
			SocketsManager.getInstance().putUuidToSocktes(uuid, socket);
		}else if (socket instanceof MQTTServerThread) {
			MQTTManager.getInstance().putUuidToSocktes(uuid, (MQTTServerThread)socket);
		}
		
	}
		
	
	
}
