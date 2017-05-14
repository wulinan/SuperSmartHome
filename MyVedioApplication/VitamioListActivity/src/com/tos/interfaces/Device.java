package com.tos.interfaces;

import com.tos.utils.Message;

public interface Device {
	
	
	/**
	 * 注册以后，获取该设备的uuid
	 * @return
	 */
	public String getrRegesterUuid();
	
	/**
	 * 心跳函数，用于测试设备是否正常，返回状态码
	 * @return
	 */
	public String heartBeat();
	
	/**
	 * 打开设备
	 * @return
	 */
	public boolean turnOn();
	
	/**
	 * 关闭设备
	 * @return
	 */
	public boolean turnOff();
	
	/**
	 * 重启
	 * @return
	 */
	public boolean restart();
	
	/**
	 * 恢复出场设置
	 * @return
	 */
	public boolean reset();
	
	/**
	 * 同步时间时钟
	 * @param timestamp
	 * @return
	 */
	public float syncTime(float timestamp);
	
	/**
	 * 获取某个消息
	 * @param code
	 * @return
	 */
	public String queryInfo(String code);
	 
	public void registered(String msg) ;
	
	/**
	 * 返回心跳的时间间隔
	 * @return
	 */
	public long getHeartbeatInterval();

	public String queryArrive(String code, Message msg);
}
