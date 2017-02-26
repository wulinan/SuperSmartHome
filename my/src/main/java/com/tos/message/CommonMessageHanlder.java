package com.tos.message;

import java.util.logging.Logger;

import com.tos.enums.Event;
import com.tos.module.driver.ServerThread;
import com.tos.module.driver.SocketsManager;
import com.tos.utils.LogManager;

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
			
	public void handleMsg(String uuid, ServerThread socket, String msg) {
		String[] cmds = msg.split("#");
		switch (Event.getCmd(cmds[0])) {
		case HeartBeat:
			//更新uuid 和socket
			SocketsManager.getInstance().putUuidToSocktes(uuid, socket);
			this.handleHeartBeat(uuid, socket, msg);
			break;
		case OffLine:
			
			break;
		case OnLine:
			SocketsManager.getInstance().putUuidToSocktes(uuid, socket);
			break;
		default:
			break;
		}
		
	}
	
	public void handleHeartBeat(String uuid, ServerThread socket, String msg){
		String returnCMD = String.format(MessageHandler.format, Event.HeartBeat.toCmd(),
				"command",uuid,"----get---");
		logger.finer("handleHeartBeat "+returnCMD);
		SocketsManager.getInstance().sendToSocket(uuid, socket, returnCMD);	
	}
	
	public void handleOnlineEvent(String uuid,ServerThread socket,String msg){
		String returnCMD = String.format(MessageHandler.format, Event.OnLine.toCmd(),
				"command",uuid,"ok");
		SocketsManager.getInstance().sendToSocket(uuid, socket, returnCMD);
	}

}
