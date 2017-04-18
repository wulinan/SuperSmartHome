package com.tos.message;

import java.util.logging.Logger;

import com.tos.enums.Event;
import com.tos.module.devices.Device;
import com.tos.module.devices.StreamMediaManager;
import com.tos.module.driver.IServerThread;
import com.tos.utils.LogManager;
import com.tos.utils.Message;

/**
 * 	设备上线事件
	设备下线事件
	命令反馈事件
	故障反馈事件
	警示提醒事件
	心跳事件

 * @author wulinan
 *
 */

public class CommonMessageHanlder implements MessageHandler{
	private static final Logger logger = LogManager.getLogger(CommonMessageHanlder.class);
			
	public void handleMsg(String uuid, IServerThread socket, Message msg) {
		String cmd = msg.getOperation();
		switch (Event.getCmd(cmd)) {
		case HeartBeat:
			MessageManager.getInsatnce().putUuidToThread(uuid, socket);
			
			this.handleHeartBeat(uuid, socket, msg);
			break;
		case OffLine:
			
			break;
		case OnLine:
			MessageManager.getInsatnce().putUuidToThread(uuid, socket);
			handleOnlineEvent(uuid, socket, msg);
			break;
		
	
		default:
			break;
		}
		
	}
	
	

	public void handleHeartBeat(String uuid, IServerThread socket, Message msg){
		String returnCMD = new Message(uuid, Event.HeartBeat.toCmd(),null, "---get---").toJson();
		logger.finer("handleHeartBeat "+returnCMD);
		
		MessageManager.getInsatnce().sendMessage(uuid, socket, returnCMD);
	}
	
	public void handleOnlineEvent(String uuid,IServerThread socket,Message msg){
		String returnCMD = new Message(uuid, Event.OnLine.toCmd(),null, "OK").toJson();
		MessageManager.getInsatnce().sendMessage(uuid, socket, returnCMD);
	}



}
