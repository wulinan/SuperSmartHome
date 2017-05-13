package com.tos.utils;

import java.util.ArrayList;
import java.util.List;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * {\"message\":{\"ack\":0,\"error\":\"\",\"data\":{\"device_id\":\"%s\"}}
 * @author wulinan
 *
 */
public class Message {
	static GsonBuilder builder = new GsonBuilder();
	static Gson gson = builder.create();
	
	
//	@Expose
//	public Data data = new Data();
	@SerializedName("msg_type")
	@Expose
	public String type = "event";//"event"
	
	@Expose
	public String device_id = "";
	
	@SerializedName("msg_id")
	@Expose
	public String operation = "";
	
	@SerializedName("msg_body")
	@Expose
	List<Body> bodys = new ArrayList<Body>();
	
	public Message(String uuid,String operation,String ctlCode,String operation_data){
		setOperation(operation);
		setDevice_id(uuid);
		bodys.add(new Body(ctlCode, operation_data));
//		setOperate_data(operation_data);
	}
	
	public Message() {
	}

	public String toJson(){

		return gson.toJson(this);
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public String getOperate_data() {
		return bodys.get(0).operate_data;
	}
	public void setOperate_data(String operate_data) {
		this.bodys.get(0).operate_data = operate_data;
	}
	public static Message fromJson(String json){
		
		return gson.fromJson(json, Message.class);
	}
	
	public static void main(String[] args) {
		Message message = new Message();
		System.out.println(message.toJson());
	}
	
	public String getQuery_id() {
		return bodys.get(0).extra_data;
	}
	public void setQuery_id(String query_id) {
		this.bodys.get(0).extra_data = query_id;
	}
	public String getCtrlCode() {
		return bodys.get(0).ctrlCode;
	}
	public void setCtrlCode(String ctrlCode) {
		this.bodys.get(0).ctrlCode = ctrlCode;
	}
	
	public String getExtra_data() {
		return bodys.get(0).extra_data;
	}
	public void setExtra_data(String extra_data) {
		this.bodys.get(0).extra_data = extra_data;
	}
	
}



class Body{
	
	public Body() {
		// TODO Auto-generated constructor stub
	}
	public Body(String code,String data){
		ctrlCode = code;
		operate_data = data;
	}
	@Expose
	public String ctrlCode;
	
	@SerializedName("data")
	@Expose
	public String operate_data;
	
	@SerializedName("extra_data")
	@Expose
	public String extra_data;
}
