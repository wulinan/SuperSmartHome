package com.tos.module.devices;

import java.util.List;

import com.tos.module.driver.ServerThread;

public class LigtingDeviceManager extends DeviceManager{

	
	
	
	private static LigtingDeviceManager instance =  new LigtingDeviceManager();
	
	public static LigtingDeviceManager getInstance() {
		return instance;
	}
	
	@Override
	public synchronized List<Device> getAllDevices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized List<String> getAllDevicesUUID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized void addDevice(Device device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized boolean operateDevice(String uuid, String cmd) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public synchronized boolean addDeviceStatusListner(Device device, DeviceStatusListner listner) {
		// TODO Auto-generated method stub
		return false;
	}

	

}
