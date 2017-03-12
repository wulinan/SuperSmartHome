package com.tos.module.driver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

import com.tos.message.MessageManager;
import com.tos.utils.LogManager;

public class SocketsManager extends ServerSocket implements ConnetionManager{
	private static final Logger logger = LogManager.getLogger(SocketsManager.class);
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
	// private static boolean hasMessage = false;// 是否输出消息标志
	// private ServerMsgListener mServerMsgListener;
	private ConcurrentHashMap<String, IServerThread> uuidToSocket = new ConcurrentHashMap<String, IServerThread>();
	private ConcurrentHashMap<IServerThread, String> socketToUuid = new ConcurrentHashMap<IServerThread, String>();
	private Queue<ServerThread> socketCache = new LinkedBlockingDeque<ServerThread>();

	private int myPort = 2017;// udp端口
	private int otherPort = 2018;// 客户端设备端口
	private Broadcast broadcast = new Broadcast(myPort, otherPort,SERVER_PORT);

	private SocketsManager() throws IOException {
		super(SERVER_PORT);
		logger.info("----------start socketmanager---------");
		new Thread(new Runnable() {
			public void run() {
				try {
					logger.info("socket manager start!");
					while (true) {
						Socket socket = accept();
						socketCache.add(new ServerThread(socket));
						logger.info(socket.getInetAddress().getHostAddress() + "connected");

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

		broadcast.startReceive();
		broadcast.registerListener(new BroadcastListener() {
			public void messageArrived(String msg) {
				logger.fine("BroadcastListener receive msg:"+msg);
				if (msg == "query_ip_port") {
					String hostIp;
					try {
						hostIp = InetAddress.getLocalHost().getHostAddress();
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return;
					}
					broadcast.sendData(hostIp + ":"+ SERVER_PORT);
				
				}
			}
		});
	
	}


	public synchronized void putUuidToSocktes(String uuid, IServerThread socket) {
		uuidToSocket.put(uuid, socket);
		socketToUuid.put(socket, uuid);
	}

	public static SocketsManager getInstance() {
		return Instance;
	}

	public synchronized void pushMessage(IServerThread socket, String msg) {
		if (socketToUuid.containsKey(socket)) {
			String uuid = socketToUuid.get(socket);
			MessageManager.getInsatnce().handleSocketMessage(uuid, socket, msg);
		} else {
			// 注册
			MessageManager.getInsatnce().handleSocketMessage(null, socket, msg);
		}
	}

	public synchronized void sendToClient(String uuid, IServerThread socket, String msg) {
		if (uuidToSocket.containsKey(uuid)) {
			uuidToSocket.get(uuid).sendMessage(msg);
		} else {
			if (uuid != null) {
				putUuidToSocktes(uuid, socket);
			}
			uuidToSocket.get(uuid).sendMessage(msg);
		}

	}

	public static void main(String[] args) {
		logger.info("------test-------");
		try {
			logger.info(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
