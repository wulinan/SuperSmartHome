package com.tos.module.devices;

import java.util.Map;

import com.tos.module.driver.ServerThread;

public class Device {
	private String uuid;
	private ServerThread serverThread;
	private Map<String, String> propertyToValue;
	
	/**
	 * 执行一个设备叠操作，即给某个设备发送消息
	 * @param cmd
	 * @return
	 */
	public boolean operator(String cmd){
		return false;
	}

	/**
	 * 当某个设备的状态发生变化时，会调用该函数
	 * @param newValue
	 */
	public void onStatusChange(String newValue){
		
	}
	
	/**
	 * 当某个设备的某个属性变化，会调用该函数
	 * @param newValue
	 */
	public void onPropertyChange(String property,String newValue){
		
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
}
