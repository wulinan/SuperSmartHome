package com.tos.module.devices;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import com.tos.enums.Event;
import com.tos.message.QueryListner;
import com.tos.message.QueryResultMessageHandler;
import com.tos.module.driver.IServerThread;
import com.tos.module.driver.SocketServerThread;
import com.tos.utils.LogManager;
import com.tos.utils.Message;

public class Device {
	private static final Logger logger = LogManager.getLogger(Device.class);
	private String uuid;
	private IServerThread serverThread;

	private Map<String, String> propertyToValue;
	private Map<String, QueryRes> queryIdToResult = new HashMap<>();
	private Set<String> waitQueryIds = new HashSet<>();

	

	public Device(String uuid, IServerThread serverThread) {
		this.uuid = uuid;
		this.serverThread = serverThread;
	}
	
	/**
	 * 执行一个设备的操作，即给某个设备发送消息
	 * @param cmd
	 * @return
	 */
	public boolean operator(String cmd,String data){
		String msg = new Message(uuid, cmd, data).toJson();
		//String.format(format, cmd,"command",uuid,data);
		logger.info("deivce operator :" + msg);
		serverThread.sendMessage(msg);
		return true;
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
	 */
	public String query(String cmd){
		String queryId = UUID.randomUUID().toString();
		Message message = new Message(uuid, Event.Query.toCmd(), cmd);
		message.setQuery_id(queryId);
		String msg =  message.toJson();
				//String.format(format, Event.Query,queryId,uuid,cmd);
		serverThread.sendMessage(msg);
		waitQueryIds.add(queryId);
		return queryId;
	}
	/**
	 * 请求设备的某个状态
	 * @param cmd
	 * @return 返回一个queryid，用于查询具体结果。返回null表示失败<br>
	 * 通过queryResult来查询结果。
	 */
	public String query(String cmd, QueryListner listner){
		String queryId = UUID.randomUUID().toString();
		Message message = new Message(uuid, Event.Query.toCmd(), cmd);
		message.setQuery_id(queryId);
		String msg =  message.toJson();
				//String.format(format, Event.Query,queryId,uuid,cmd);
		QueryResultMessageHandler.getInstance().query(queryId, listner);
		serverThread.sendMessage(msg);
		waitQueryIds.add(queryId);
//		listners.put(queryId, listner);
		
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
	
	public IServerThread getServerThread() {
		return serverThread;
	}

	public void setServerThread(SocketServerThread serverThread) {
		this.serverThread = serverThread;
	}
	
	public static class QueryRes{
		public boolean hasResult = false;
		public String result;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("device[%s]", uuid);
	}
}


