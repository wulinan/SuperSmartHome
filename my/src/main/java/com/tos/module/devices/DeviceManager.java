package com.tos.module.devices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tos.message.QueryListner;
import com.tos.module.driver.IServerThread;
import com.tos.module.driver.SocketServerThread;
import com.tos.utils.Message;


/**
 * 设备管理虚类，用于定义设备管理的通用接口
 * @author wulinan
 *
 */
public abstract class DeviceManager {
	protected Map<String, Device> uuidToDevice = new HashMap<>();
	protected List<Device> alldevices = new ArrayList<>();
	
	/**
	 * 注册一个设备
	 * @param uuid
	 * @param thread
	 */
	public synchronized void registerDevice(String uuid, IServerThread thread) {
		Device device = new Device(uuid,thread);
		alldevices.add(device);
		uuidToDevice.put(uuid, device);
	}
	
	
	/**
	 * 返回所有该类型的设备
	 * @return
	 */
	public abstract List<Device> getAllDevices();
	
	/**
	 * 获取该类型所有设备的uuid
	 * @return
	 */
	public abstract List<String> getAllDevicesUUID();
	
	/**
	 * 加入一个设备给管理器管理
	 * @param device
	 */
	public abstract void addDevice(Device device);
	
	/**
	 * 通过uuid，获取一个设备
	 * @param uuid
	 * @return
	 */
	public synchronized Device getDevice(String uuid) {
		// TODO Auto-generated method stub
		if(uuid == null || uuid.equals("0")){
			//获取一个
			if(!uuidToDevice.isEmpty()){
				for(Device dev : uuidToDevice.values())
					return dev;
			}
			return null;
		}
		
		return uuidToDevice.get(uuid);
	}
	
	public Device getDevice(int id) {
		return alldevices.get(id);
	}
	/**
	 * 通过uuid，制定一个设备，然后执行操作
	 * @param uuid
	 * @param cmd
	 * @return
	 */
	public abstract boolean operateDevice(String uuid, String cmd);
	
	/**
	 * 给某一个设备注册一个监听器，如果这个设备的某个状态发生变化时就会得到通知
	 * @param device
	 * @param listner
	 * @return
	 */
	public abstract boolean addDeviceStatusListner(Device device, DeviceStatusListner listner);
	
	
	/**
	 * 监听器接口，用于监听某一个设备的状态变化。比如开、关，
	 * @author wulinan
	 *
	 */
	public static  interface DeviceStatusListner{
		
		public void onStatusChange(String status, String newValue);
	}
	
	/**
	 * 用监听某个属性值变的变化，例如温度，水量，等等
	 * @author wulinan
	 *
	 */
	public static  interface DevicePropertyListner{
		
		public void onPropertysChange(String property, String newValue);
	}
	
}


