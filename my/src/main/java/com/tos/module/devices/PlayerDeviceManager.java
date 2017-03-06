package com.tos.module.devices;

import java.util.List;

import com.tos.module.driver.ServerThread;

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
		device.operator("pr", "http://192.168.0.102:9999/sample.avi");
		return false;
	}


	@Override
	public void addDevice(Device device) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public synchronized void registerDevice(String uuid, ServerThread thread) {
		// TODO Auto-generated method stub
		super.registerDevice(uuid, thread);
		
		playeUrl("http://192.168.0.102:9999/sample.", uuidToDevice.get(uuid));
	}
	

}
