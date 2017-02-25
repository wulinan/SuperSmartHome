package com.tos.module.devices;

import com.tos.enums.DeviceType;

/**
 * 用于管理各种manager
 * @author wulinan
 *
 */
public class ManagerFactory {
	
	public static DeviceManager getDeviceManager(DeviceType type){
		switch (type) {
		case Light:
			return LigtingDeviceManager.getInstance();
			
		case StreamMedia:
			return StreamMediaManager.getInstance();
			
		case Player:
			return PlayerDeviceManager.getInstance();

		default:
			throw new RuntimeException("can not find " + type);
			
		}
	}
	
 
}
