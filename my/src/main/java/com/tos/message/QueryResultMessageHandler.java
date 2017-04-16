package com.tos.message;

import com.tos.module.driver.IServerThread;
import com.tos.utils.Message;

public class QueryResultMessageHandler implements MessageHandler {

	@Override
	public void handleMsg(String uuid, IServerThread socket, Message msg) {


		String queryId = msg.getOperation(); 
		

	}

}
