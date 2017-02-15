package com.tos.sample;

import com.tos.interfaces.LightingDevice;
import com.tos.manager.DeviceType;
import com.tos.manager.TosServiceManager;

public class LightingDemo implements LightingDevice {
	
	static{
		TosServiceManager.getInstance().registerDevice(DeviceType.Light, new LightingDemo(),0);
	}
	
	@Override
	public String getRegisterUuid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String heartBeat() {
		return "hahah,im'ok";
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
	public boolean turnDown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean turnUp() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeColor(String colorname) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registered(String msg) {
		System.out.println("it's ok!!!"+msg);
	}
	
	//test
	public static void main(String[] args) {
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public long getHeartbeatInterval() {
		// TODO Auto-generated method stub
		return 10;
	}
}
