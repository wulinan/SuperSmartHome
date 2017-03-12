package com.tos.module.driver;



public abstract class IServerThread extends Thread{
	
	public void run() {
		while (true) {
			receiveMessage();
		}
	}

	// 服务器向客户端发送消息
	public abstract void sendMessage(String msg);

	// 服务器接收客户端消息
	public abstract void receiveMessage();
}
