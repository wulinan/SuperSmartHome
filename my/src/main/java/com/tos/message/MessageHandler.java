package com.tos.message;

import com.tos.module.driver.IServerThread;
import com.tos.utils.Message;

public interface MessageHandler {
	/**
	 * 管理某一类或多类消息，如果是多类就在handleMsg里就行区分
	 * @param uuid
	 * @param socket
	 * @param message
	 */
	void handleMsg(String uuid,IServerThread socket,Message message);
}
