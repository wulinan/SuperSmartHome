package com.tos.message;

import com.tos.module.driver.IServerThread;

public interface MessageHandler {
	String format = "%s#%s#%s#%s";
	/**
	 * 管理某一类或多类消息，如果是多类就在handleMsg里就行区分
	 * @param uuid
	 * @param socket
	 * @param msg
	 */
	void handleMsg(String uuid,IServerThread socket,String msg);
}
