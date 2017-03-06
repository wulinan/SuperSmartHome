package com.tos.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
/**
 * http://www.ibm.com/developerworks/cn/java/l-jchat/
 * 
 * 
 * 
 * 不能广播的话，看：
 * http://stackoverflow.com/questions/18747134/getting-cant-assign-requested-address-java-net-socketexception-using-ehcache
 * This was caused by an IPv6 address being returned from java.net.NetworkInterface.getDefault(). I'm on a Macbook and was using wireless -- p2p0 (used for AirDrop) was returned as the default network interface but my p2p0 only has an IPv6 ether entry [found by running ipconfig].

Two solutions, both of which worked for me (I prefer the first because it works whether you are using a wired or wireless connection)

Start the JVM with -Djava.net.preferIPv4Stack=true. This caused java.net.NetworkInterface.getDefault() to return my vboxnet0 network interface -- not sure what you'll get if you're not running a host-only VM.
Turn off wireless and use a wired connection

 * @author wulinan
 *
 */
public class Broadcast {

//	private MulticastSocket receiver;
    private DatagramSocket receiver;
	private DatagramSocket sender;
	private InetAddress broadcastGroup;
	
	private BroadcastReceiveThread thread;
	
	private List<BroadcastListener> listeners = new ArrayList<BroadcastListener>();

	private int mySidePort;
	private int otherSidePort;
	private static final String groupName = "255.255.255.255";
    ScheduledExecutorService heartBeatService = Executors
            .newSingleThreadScheduledExecutor();
	public Broadcast(int myPort, int otherPort) {
		try{
			this.mySidePort = myPort;
			this.otherSidePort = otherPort;
			broadcastGroup = InetAddress.getByName(groupName);
			
//			sender = new DatagramSocket(otherSidePort);
//			receiver = new MulticastSocket(mySidePort);
//			receiver.joinGroup(broadcastGroup);
//
			thread = new BroadcastReceiveThread(this);

			//thread.start();
            receiver = new DatagramSocket(mySidePort);
//           thread.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}

	// 数据发送方法
	public void sendData(String Msg) {
		byte[] b = new byte[1024];
		DatagramPacket packet;
		try {
			// 字节序列b 包括需要发送的数据
			b = Msg.getBytes();
			// 构造一个数据包，BroadcastGroup是数据广播组标示符(波段)，
			// ClientPort是数据广播目标端口(频率)。
			packet = new DatagramPacket(b, b.length, broadcastGroup, this.otherSidePort);
			// 发送数据包
			sender.send(packet);
		} catch (Exception e) {
		}
	}

	// 数据接收方法
	public String receiveData() {
		byte[] b = new byte[1024];
		// 构造一个空的数据包
		DatagramPacket packet = new DatagramPacket(b, 1024);
		String InMsg;
		try {
			// 接收数据
			receiver.receive(packet);
		} catch (IOException e) {
		}
		// 丛数据包中获得接收到的数据
		b = packet.getData();

		InMsg = new String(b,0,packet.getLength());
		for (BroadcastListener listener : listeners) {
			listener.messageArrived(InMsg);
		}
		return InMsg;
	}
	
	public void stopReceive(){
		thread.stopReceive();
	}
	
	public synchronized void registerListener(BroadcastListener listener){
		listeners.add(listener);
	}
	
	public void startReceive(){
		thread.start();
	}

	public void close(){
		receiver.close();
		stopReceive();
	}

}

class BroadcastReceiveThread extends Thread {

	protected Broadcast broadcast;
	
	private boolean stopReceiveData;

	public BroadcastReceiveThread(Broadcast b) {
		// TODO Auto-generated constructor stub
		this.broadcast = b;
	}

	@Override
	public void run() {
		while (!stopReceiveData) {
			String mString = broadcast.receiveData();

		}
	}
	
	public void stopReceive(){
		stopReceiveData = true;
	}

}
