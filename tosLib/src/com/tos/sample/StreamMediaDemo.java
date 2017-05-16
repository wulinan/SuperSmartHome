package com.tos.sample;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import com.tos.interfaces.StreamMediaDevice;
import com.tos.manager.DeviceType;
import com.tos.manager.TosServiceManager;
import com.tos.utils.VideoStreamServer;

public class StreamMediaDemo implements StreamMediaDevice{
	
	public static int ClientPort = 9999;
	private static String filePath = "/Users/wulinan/";
	public VideoStreamServer server;
	public StreamMediaDemo() {
		VideoStreamServer.main(ClientPort, filePath);
		
	}
	
	

//	//@Override
	public String getRegisterUuid() {
		// TODO Auto-generated method stub
		return null;
	}

//	//@Override
	public String heartBeat() {
		// TODO Auto-generated method stub
		return null;
	}

//	//@Override
	public boolean turnOn() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean turnOff() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean restart() {
		// TODO Auto-generated method stub
		return false;
	}

//	//@Override
	public boolean reset() {
		// TODO Auto-generated method stub
		return false;
	}

//	//@Override
	public float syncTime(float timestamp) {
		// TODO Auto-generated method stub
		return 0;
	}

//	//@Override
	public String queryInfo(String code) {
		// TODO Auto-generated method stub
		return null;
	}

//	//@Override
	public String getStreamUrl(String mediaName) {
		if(mediaName == null){
			try {
				String hostIp = InetAddress.getLocalHost().getHostAddress();
				String url = String.format("http://%s:9999/sample.avi",hostIp);
				return url;
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	//@Override
	public List<String> getAllMediaNames() {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	//test
	public static void main(String[] args) throws IOException {
		
		TosServiceManager.getInstance().setWorkDir(".");
		TosServiceManager.getInstance().registerDevice(DeviceType.StreamMedia, new StreamMediaDemo(),0);
		System.out.println("start!!");
	}



	//@Override
	public void registered(String msg) {
		// TODO Auto-generated method stub
		
	}



	//@Override
	public long getHeartbeatInterval() {
		// TODO Auto-generated method stub
		return 10000;
	}



	//@Override
	public String queryArrive(String code) {
		// TODO Auto-generated method stub
		return null;
	}
}
