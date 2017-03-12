package com.tos.message;

import com.tos.module.driver.IServerThread;
import com.tos.module.driver.ServerThread;

public class QueryResultMessageHandler implements MessageHandler {

	@Override
	public void handleMsg(String uuid, IServerThread socket, String msg) {

		//消息格式 query#queryId#设备uuid＃data
		String[] msgs = msg.split("#");
		String queryId = msgs[1]; 
		

	}

}
