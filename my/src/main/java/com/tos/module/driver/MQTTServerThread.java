package com.tos.module.driver;

import java.nio.ByteBuffer;

import io.moquette.parser.proto.messages.AbstractMessage;
import io.moquette.parser.proto.messages.PublishMessage;
import io.moquette.server.Server;

public class MQTTServerThread extends IServerThread{
	private String clientId ;//uuid
	Server mqttBroker;
	String topic = "cmd";
	
	public MQTTServerThread(Server broker) {
		mqttBroker = broker;
	}
	
	public  void sendMessage(String msgContent){
		if(msgContent == null)
			return;
		PublishMessage msg =  new PublishMessage();
		msg.setTopicName(topic);
		msg.setPayload(ByteBuffer.wrap(msgContent.getBytes()));
		msg.setClientId(clientId);
		msg.setQos(AbstractMessage.QOSType.MOST_ONE );
		mqttBroker.internalPublish(msg);
	}

	
	public void setClientId(String uuid){
		this.clientId = uuid;
	}
	// 服务器接收客户端消息
	public  void receiveMessage(){
		//不需要写在这
	}
	
	@Override
	public void run() {
		
	}
	
}
