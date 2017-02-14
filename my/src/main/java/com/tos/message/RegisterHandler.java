package com.tos.message;

import java.util.UUID;

import com.tos.enums.Event;
import com.tos.module.driver.ServerThread;
import com.tos.module.driver.SocketsManager;

public class RegisterHandler implements MessageHandler {
	//消息ID-消息类型-设备ID-消息体
	//例子：register－command－0-type
	String format = "%s#%s#%s#%s";
	public void handleMsg(String uuid, ServerThread socket, String msg) {
			UUID newUuid = UUID.randomUUID();
			uuid = newUuid.toString();
			SocketsManager.getInstance().putUuidToSocktes(uuid, socket);
			String returnCMD = String.format(this.format, Event.Register.toCmd(),
					"command",uuid,"OK");
			SocketsManager.getInstance().sendToSocket(uuid, socket, returnCMD);	
			//此处可以添加数据库逻辑，相关服务逻辑
	}

}
