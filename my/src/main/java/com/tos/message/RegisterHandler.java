package com.tos.message;

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

public class RegisterHandler implements MessageHandler {
	private final static Logger logger = LogManager.getLogger(RegisterHandler.class);
	//消息ID-消息类型-设备ID-消息体
	//例子：register－command－0-type
	String format = "%s#%s#%s#%s";
	public void handleMsg(String uuid, IServerThread socket, String msg) {
			
			UUID newUuid = UUID.randomUUID();
			if(uuid == null || "0".equals(uuid)){
				uuid = newUuid.toString();
			}
			if(socket instanceof SocketServerThread){
				SocketsManager.getInstance().putUuidToSocktes(uuid, socket);
			}else if (socket instanceof MQTTServerThread) {
				MQTTManager.getInstance().putUuidToSocktes(uuid, (MQTTServerThread)socket);
			}
			String returnCMD = String.format(this.format, Event.Register.toCmd(),
					"command",uuid,"OK");
			SocketsManager.getInstance().sendToClient(uuid, socket, returnCMD);	
//			logger.fine("send to client "+ returnCMD);
			String msg1 = String.format("{\"message\":{\"ack\":0,\"error\":\"\",\"data\":{\"device_id\":\"%s\"}}}", uuid);
			logger.info(msg1);
			String[] msgs = msg.split("#");
			DeviceType type = DeviceType.valueOf(msgs[3]);
			ManagerFactory.getDeviceManager(type).registerDevice(uuid, socket);
			//此处可以添加数据库逻辑，相关服务逻辑
	}

}
