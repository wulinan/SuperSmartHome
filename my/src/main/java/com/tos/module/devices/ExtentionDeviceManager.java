package com.tos.module.devices;

import java.util.List;

import com.tos.module.driver.SocketServerThread;


public class ExtentionDeviceManager extends DeviceManager {
	
	private List<ExtentionDevice> devices;
	
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
	public void addDevice(com.tos.module.devices.Device device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean addDeviceStatusListner(com.tos.module.devices.Device device, DeviceStatusListner listner) {
		// TODO Auto-generated method stub
		return false;
	}



}
