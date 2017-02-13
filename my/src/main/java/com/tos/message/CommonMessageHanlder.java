package com.tos.message;

import com.tos.enums.Command;
import com.tos.module.driver.ServerThread;
import com.tos.module.driver.SocketsManager;

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

	public void handleMsg(String uuid, ServerThread socket, String msg) {
		String[] cmds = msg.split("#");
		switch (Command.getCmd(cmds[0])) {
		case HeartBeat:
			this.handleHeartBeat(uuid, socket, msg);
			break;
		case OffLine:
			break;
		
		case OnLine:
			break;

		default:
			break;
		}
	}
	
	public void handleHeartBeat(String uuid, ServerThread socket, String msg){
		String returnCMD = String.format(MessageHandler.format, Command.HeartBeat.toCmd(),
				"command",uuid,"----get---");
		System.out.println("handleHeartBeat"+returnCMD);
		SocketsManager.getInstance().sendToSocket(uuid, socket, returnCMD);	
	}

}
