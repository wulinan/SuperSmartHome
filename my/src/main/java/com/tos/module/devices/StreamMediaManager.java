package com.tos.module.devices;

import java.util.List;

public class StreamMediaManager extends DeviceManager {
	
	private static StreamMediaManager instance = new StreamMediaManager();
	
	public static StreamMediaManager getInstance() {
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
	public void addDevice(Device device) {
		// TODO Auto-generated method stub
		
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
	
	
	public String getMediaStreamUrl(String mediaName){
		return null;
	}


	
}
