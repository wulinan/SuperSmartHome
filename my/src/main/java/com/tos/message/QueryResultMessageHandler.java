package com.tos.message;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Logger;

import com.tos.enums.Event;
import com.tos.module.devices.Device;
import com.tos.module.devices.PlayerDeviceManager;
import com.tos.module.devices.StreamMediaManager;
import com.tos.module.driver.IServerThread;
import com.tos.utils.LogManager;
import com.tos.utils.Message;

public class QueryResultMessageHandler implements MessageHandler {
	
	private static final  Logger logger = LogManager.getLogger(QueryResultMessageHandler.class);
	private Map<String, QueryListner> listners = new HashMap<>();
	public void query(String queryid, QueryListner listner){
		listners.put(queryid, listner);
	}
	
	public void queryArrive(String queryid, Message msg){
//		logger.info(msg.toJson());
		listners.get(queryid).resultArrive(msg);
	}
	
	private QueryResultMessageHandler() {
		// TODO Auto-generated constructor stub
	}
	
	private static QueryResultMessageHandler instance = new QueryResultMessageHandler();
	
	public static QueryResultMessageHandler getInstance() {
		return instance;
	}
	
	@Override
	public void handleMsg(String uuid, IServerThread socket, Message msg) {
		
		switch (Event.getCmd(msg.getCtrlCode())) {
		case GetUrlPlay:
			logger.info(msg.toJson());
			handleGetUrl(uuid,socket,msg);
			break;
		case Reponse:
			logger.info(msg.toJson());
			queryArrive(msg.getQuery_id(), msg);
			break;
		
		case PutUrlPlay:
			logger.info(msg.toJson());
			handlePutUrl(uuid,socket,msg);
			break;
			
		default:
			break;
		}

	}
	
	public void handleGetUrl(String uuid, final IServerThread socket, final Message msg) {
		if(uuid.equals("0"))
			uuid = null;
		Device device = StreamMediaManager.getInstance().getDevice(msg.getOperate_data());
		if(device == null){
			throw new RuntimeException("no stream media device");
		}else{
//			System.out.println("get url----------");
			device.query(Event.GetUrlPlay.toCmd(),new QueryListner() {
				
				@Override
				public void resultArrive(Message msg1) {
						String url= msg1.getOperate_data();
						Message toMessage =  new Message(msg.getDevice_id(),Event.Operation.toCmd(),Event.PlayRemote.toCmd(),url);
//						System.out.println(toMessage.toJson());
						socket.sendMessage(toMessage.toJson());

						logger.info(toMessage.toJson());
				}
			});
		}
	}
	public void handlePutUrl(String uuid, final IServerThread socket, final Message msg) {
		Device device = null;
		if(uuid.equals("0")){
			device = PlayerDeviceManager.getInstance().getDevice(null);
		}
		else {
			device = PlayerDeviceManager.getInstance().getDevice(null);
		}
		String url = msg.getOperate_data();
		String time = msg.getExtra_data();
		System.out.println(device);
		System.out.println(uuid);
		Message toMessage =  new Message(device.getUuid(),Event.Operation.toCmd(),Event.PlayRemote.toCmd(),url);
		if (time == null){
			time ="0";
		}
		toMessage.setExtra_data(time);
		device.getServerThread().sendMessage(toMessage.toJson());
		System.out.println(toMessage.toJson());
	}

}
