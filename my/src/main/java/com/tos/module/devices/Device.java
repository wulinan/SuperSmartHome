package com.tos.module.devices;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.tos.enums.Event;
import com.tos.module.driver.ServerThread;

public class Device {
	private String uuid;
	private ServerThread serverThread;
	private Map<String, String> propertyToValue;
	private Map<String, QueryRes> queryIdToResult = new HashMap<>();
	private Set<String> waitQueryIds = new HashSet<>();
	
	/**
	 * 	//消息ID-消息类型-设备ID-消息体
	 *例子：register－command－0-type
	 */
	String format = "%s#%s#%s#%s";
	
	/**
	 * 执行一个设备的操作，即给某个设备发送消息
	 * @param cmd
	 * @return
	 */
	public boolean operator(String cmd,String data){
		String msg = String.format(format, cmd,"command",uuid,data);
		serverThread.sendMessage(msg);
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
	
	/**
	 * 请求设备的某个状态
	 * @param cmd
	 * @return 返回一个queryid，用于查询具体结果。返回null表示失败<br>
	 * 通过queryResult来查询结果。
	 * 消息格式register#msgid#设备uuid＃cmd
	 */
	public String query(String cmd){
		String queryId = UUID.randomUUID().toString();
		String msg = String.format(format, Event.Query,queryId,uuid,cmd);
		serverThread.sendMessage(msg);
		waitQueryIds.add(queryId);
		return queryId;
	}
	
	/**
	 * 获取查询结果，如果还在查询，返回null
	 * @param queryId
	 * @return
	 */
	public QueryRes queryResult(String queryId){
		if(waitQueryIds.contains(queryId))
			return null;
		if(queryIdToResult.containsKey(queryId)){
			return queryIdToResult.get(queryId);
		}
		return null;
	}
	
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public static class QueryRes{
		public boolean hasResult = false;
		public String result;
	}
}
