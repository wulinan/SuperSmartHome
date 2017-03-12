package com.tos.message;

import java.util.logging.Logger;

import com.tos.enums.Event;
import com.tos.module.driver.IServerThread;
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
			
	public void handleMsg(String uuid, IServerThread socket, String msg) {
		String[] cmds = msg.split("#");
		switch (Event.getCmd(cmds[0])) {
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
	
	public void handleHeartBeat(String uuid, IServerThread socket, String msg){
		String returnCMD = String.format(MessageHandler.format, Event.HeartBeat.toCmd(),
				"command",uuid,"----get---");
		logger.finer("handleHeartBeat "+returnCMD);
		
		MessageManager.getInsatnce().sendMessage(uuid, socket, returnCMD);
	}
	
	public void handleOnlineEvent(String uuid,IServerThread socket,String msg){
		String returnCMD = String.format(MessageHandler.format, Event.OnLine.toCmd(),
				"command",uuid,"ok");
		
		MessageManager.getInsatnce().sendMessage(uuid, socket, returnCMD);
	}



}
