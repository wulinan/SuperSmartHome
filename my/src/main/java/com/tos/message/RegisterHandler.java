package com.tos.message;

import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import com.tos.enums.DeviceType;
import com.tos.enums.Event;
import com.tos.module.devices.ManagerFactory;
import com.tos.module.driver.IServerThread;
import com.tos.module.driver.MQTTManager;
import com.tos.module.driver.MQTTServerThread;
import com.tos.module.driver.SocketServerThread;
import com.tos.module.driver.SocketsManager;
import com.tos.utils.LogManager;
import com.tos.utils.Message;

import io.moquette.server.config.ResourceLoaderConfig;

public class RegisterHandler implements MessageHandler {
	private final static Logger logger = LogManager.getLogger(RegisterHandler.class);
	public void handleMsg(String uuid, IServerThread socket, Message msg) {
			
			UUID newUuid = UUID.randomUUID();
			if(uuid == null || "0".equals(uuid)){
				uuid = newUuid.toString();
			}
			boolean res = false;
			if(socket instanceof SocketServerThread){
				Map<String, IServerThread> map =  SocketsManager.getInstance().getMap();
				res = map.containsKey(uuid);
				SocketsManager.getInstance().putUuidToSocktes(uuid, socket);
			}else if (socket instanceof MQTTServerThread) {
				MQTTManager.getInstance().putUuidToSocktes(uuid, (MQTTServerThread)socket);
			}
			String returnCMD = new Message(uuid, Event.Register.toCmd(),null, "OK").toJson();
			SocketsManager.getInstance().sendToClient(uuid, socket, returnCMD);	
			if (!res) {
				logger.fine("send to client "+ returnCMD);
			}
			
		
		
			DeviceType type = DeviceType.valueOf(msg.getOperate_data());
			ManagerFactory.getDeviceManager(type).registerDevice(uuid, socket);

//			String msg1 = String.format("{\"message\":{\"ack\":0,\"error\":\"\",\"data\":{\"device_id\":\"%s\"}}}", uuid);
//			logger.info(msg1);
			//此处可以添加数据库逻辑，相关服务逻辑
	}

}
