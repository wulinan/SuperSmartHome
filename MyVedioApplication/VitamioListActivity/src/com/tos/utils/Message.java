package com.tos.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

/**
 * {\"message\":{\"ack\":0,\"error\":\"\",\"data\":{\"device_id\":\"%s\"}}
 * @author wulinan
 *
 */
public class Message {
	static GsonBuilder builder = new GsonBuilder();
	static Gson gson = builder.create();

	@Expose
	public Data data = new Data();
	@Expose
	public int ack = 1;
	@Expose
	public String error = "";
	@Expose
	public String type = "event";//"command"

	public Message(String uuid,String operation,String operation_data){
		setOperation(operation);
		setDevice_id(uuid);
		setOperate_data(operation_data);
	}

	public Message() {
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public int getAck() {
		return ack;
	}

	public void setAck(int ack) {
		this.ack = ack;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String toJson(){

		return gson.toJson(this);
	}
	public String getDevice_id() {
		return data.device_id;
	}
	public void setDevice_id(String device_id) {
		this.data.device_id = device_id;
	}
	public String getOperation() {
		return data.operation;
	}
	public void setOperation(String operation) {
		this.data.operation = operation;
	}
	public String getDes() {
		return data.des;
	}
	public void setDes(String des) {
		this.data.des = des;
	}
	public String getOperate_data() {
		return data.operate_data;
	}
	public void setOperate_data(String operate_data) {
		this.data.operate_data = operate_data;
	}
	public static Message fromJson(String json){

		return gson.fromJson(json, Message.class);
	}

	public static void main(String[] args) {
		Message message = new Message();
		System.out.println(message.toJson());
	}

	public String getQuery_id() {
		return data.query_id;
	}
	public void setQuery_id(String query_id) {
		this.data.query_id = query_id;
	}
}

class Data{
	@Expose
	public String device_id = "";
	@Expose
	public String operation = "";
	@Expose
	public String des = "";
	@Expose
	public String operate_data = "";
	@Expose
	public String query_id;

}
