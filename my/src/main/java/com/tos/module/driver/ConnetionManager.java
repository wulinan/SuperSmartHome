package com.tos.module.driver;


public interface ConnetionManager {
	
	public  void pushMessage(IServerThread socket, String msg);

	public void sendToClient(String uuid, IServerThread socket, String msg);
}
