package com.tos.module.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

import com.tos.utils.LogManager;

public class ServerThread extends IServerThread {
	private static final Logger logger = LogManager.getLogger(ServerThread.class);
	private Socket client;
	private PrintWriter out;
	private BufferedReader in;
	
	public Socket getClient(){
		return client;
	}

	public ServerThread(Socket s) throws IOException {
		client = s;
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
	
}
