package com.tos.module.devices;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import com.tos.module.driver.IServerThread;
import com.tos.module.driver.SocketServerThread;

public class PlayerDeviceManager extends DeviceManager{
	private static PlayerDeviceManager instance = new PlayerDeviceManager();
	
	public static PlayerDeviceManager getInstance() {
		return instance;
	}
	
	@Override
	public List<Device> getAllDevices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllDevicesUUID() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean operateDevice(String uuid, String cmd) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addDeviceStatusListner(Device device, DeviceStatusListner listner) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean playeUrl(String url,Device device){
		System.out.println("------------test------------");
		try {
			String hostIp = InetAddress.getLocalHost().getHostAddress();
			url=String.format("http://%s:9999/sample.avi", hostIp);
			device.operator("pr", url);
			return true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}


	@Override
	public void addDevice(Device device) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public synchronized void registerDevice(String uuid, IServerThread thread) {
		// TODO Auto-generated method stub
		super.registerDevice(uuid, thread);
		
		playeUrl("http://192.168.0.102:9999/sample.", uuidToDevice.get(uuid));
	}
	

}
