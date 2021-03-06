package com.tos.module.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

import com.tos.utils.LogManager;

public class SocketServerThread extends IServerThread {
	private static final Logger logger = LogManager.getLogger(SocketServerThread.class);
	private Socket client;
	private PrintWriter out;
	private BufferedReader in;
	public String address = "";
	
	public Socket getClient(){
		return client;
	}

	public SocketServerThread(Socket s) throws IOException {
		client = s;
		address = s.getInetAddress().getHostAddress();
		//client.setSendBufferSize(0);
		out = new PrintWriter(client.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		start();
	}

	public void run() {

		receiveMessage();

	}

	// 服务器向客户端发送消息
	public void sendMessage(String msg) {
		out.println(msg);
	}

	// 服务器接收客户端消息
	public void receiveMessage() {

		while (!client.isClosed()) {
			try {
				String str = in.readLine();
				if (str == null || str.equals(""))
					continue;
				logger.finer(String.format("receive message from client:%s\n", str));
				SocketsManager.getInstance().pushMessage(this, str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getAddress() {
		// TODO Auto-generated method stub
		return address;
	}
	
}
