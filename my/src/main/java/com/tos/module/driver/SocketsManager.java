package com.tos.module.driver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

import com.tos.message.MessageManager;

public class SocketsManager extends ServerSocket {

	private static SocketsManager Instance;
	static {
		try {
			Instance = new SocketsManager();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static final int SERVER_PORT = 2016;// 服务器端口
	private static Object lock = new Object();// 同步锁
	private static boolean hasMessage = false;// 是否输出消息标志
	private ServerMsgListener mServerMsgListener;
	private ConcurrentHashMap<String, ServerThread> uuidToSocket = new ConcurrentHashMap<String,ServerThread>();
	private ConcurrentHashMap<ServerThread, String> socketToUuid = new ConcurrentHashMap<ServerThread, String>();
	private Queue<ServerThread> socketCache = new LinkedBlockingDeque<ServerThread>();

	private SocketsManager() throws IOException {
		super(SERVER_PORT);
		new Thread(new Runnable() {
			public void run() {
				try {
					System.out.println("socket manager start!");
					while (true) {
						Socket socket = accept();
						socketCache.add(new ServerThread(socket));
						System.out.println(socket.getInetAddress().getHostAddress() + "connected");
						
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		}).start();
		
	}

	public synchronized void putUuidToSocktes(String uuid,ServerThread socket){
		uuidToSocket.put(uuid, socket);
		socketToUuid.put(socket,uuid);
	}
	
	public static SocketsManager getInstance() {
		return Instance;
	}

	public synchronized void pushMessage(ServerThread socket, String msg) {
		if(socketToUuid.containsKey(socket)){
			String uuid = socketToUuid.get(socket);
			MessageManager.getInsatnce().handleSocketMessage(uuid, socket, msg);
		}else {
			//注册
			MessageManager.getInsatnce().handleSocketMessage(null,socket, msg);
		}
	}
	
	public  synchronized void sendToSocket(String uuid,ServerThread socket, String msg){
		if(uuidToSocket.containsKey(uuid)){
			uuidToSocket.get(uuid).sendMessage(msg);
		}else {
			if(uuid!=null){
				putUuidToSocktes(uuid, socket);
			}
			uuidToSocket.get(uuid).sendMessage(msg);
		}
		
		
	}
	
	public static void main(String[] args) {
		System.out.println("------test-------");
	}
}

