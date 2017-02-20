package com.tos.sample;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.tos.interfaces.StreamMediaDevice;
import com.tos.utils.VideoStreamServer;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.Status;

public class StreamMediaDemo implements StreamMediaDevice{
	
	public static int ClientPort = 9999;
	private static String filePath = "/Users/wulinan/";
	public VideoStreamServer server;
	public StreamMediaDemo() {
		VideoStreamServer.main(ClientPort, filePath);
	}
	
	

	@Override
	public String getRegisterUuid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String heartBeat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean turnOn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean turnOff() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean restart() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reset() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float syncTime(float timestamp) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String queryInfo(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStreamUrl(String mediaName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllMediaNames() {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	//test
	public static void main(String[] args) throws IOException {
		new StreamMediaDemo();
		System.out.println("start!!");
	}



	@Override
	public void registered(String msg) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public long getHeartbeatInterval() {
		// TODO Auto-generated method stub
		return 0;
	}
}
