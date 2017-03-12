package com.tos.module.driver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.tos.message.MessageManager;

import io.moquette.interception.AbstractInterceptHandler;
import io.moquette.interception.InterceptHandler;
import io.moquette.interception.messages.InterceptPublishMessage;
import io.moquette.parser.proto.messages.AbstractMessage;
import io.moquette.parser.proto.messages.PublishMessage;
import io.moquette.server.Server;
import io.moquette.server.config.ClasspathResourceLoader;
import io.moquette.server.config.IConfig;
import io.moquette.server.config.ResourceLoaderConfig;
 

public class MQTTManager implements ConnetionManager{
	private static final  MQTTManager Instance  = new MQTTManager();
	
	public static MQTTManager getInstance() {
		return Instance;
	}
	
	
	static class PublisherListener extends AbstractInterceptHandler {
		@Override
		public void onPublish(InterceptPublishMessage message) {
			String msg = new String(message.getPayload().array());
			System.out.println("moquette mqtt broker message intercepted, topic: " + message.getTopicName()
					+ ", content: " + msg);
			MessageManager.getInsatnce().handleSocketMessage(null, null, msg);
		}
		
//		@Override
//		public void onSubscribe(InterceptSubscribeMessage msg) {
//			// TODO Auto-generated method stub
//			super.onSubscribe(msg);
//			String string = msg.getTopicFilter();
//			System.out.println(string);
//		}
	}
 
	public static void main(String[] args) throws InterruptedException, IOException {
		// Creating a MQTT Broker using Moquette
		ClasspathResourceLoader loader = new ClasspathResourceLoader();
		final IConfig classPathConfig = new ResourceLoaderConfig(loader, "config/moquette.conf");

		final Server mqttBroker = new Server();
		
	
		
		final List<? extends InterceptHandler> userHandlers = Arrays.asList(new PublisherListener());
		mqttBroker.startServer(classPathConfig, userHandlers);
		
 
		System.out.println("moquette mqtt broker started, press ctrl-c to shutdown..");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("stopping moquette mqtt broker..");
				mqttBroker.stopServer();
				System.out.println("moquette mqtt broker stopped");
			}
		});
 
		Thread.sleep(4000);
 
		// Creating a MQTT Client using Eclipse Paho
		String topic = "news";
		String content = "Visit www.hascode.com! :D";
		int qos = 2;
		String broker = "tcp://0.0.0.0:1883";
		String clientId = "paho-java-client";
 
		try {
			MqttClient sampleClient = new MqttClient(broker, clientId, new MemoryPersistence());
			
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			System.out.println("paho-client connecting to broker: " + broker);
			sampleClient.connect(connOpts);
			System.out.println("paho-client connected to broker");
			System.out.println("paho-client publishing message: " + content);
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(qos);
			sampleClient.subscribe("test");
			
			
			sampleClient.setCallback(new MqttCallback() {
				
				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					System.out.println("1111111 "+new String(message.getPayload()));
					System.out.println("22222 "+ topic);
				}
				
				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void connectionLost(Throwable cause) {
					// TODO Auto-generated method stub
					
				}
			});
			sampleClient.publish(topic, message);
			System.out.println("paho-client message published");
			PublishMessage msg =  new PublishMessage();
			msg.setTopicName("test");
			msg.setPayload(ByteBuffer.wrap("hello".getBytes()));
			msg.setClientId(clientId);
			msg.setQos(AbstractMessage.QOSType.MOST_ONE );
//			
		
			
			mqttBroker.internalPublish(msg);
	
			Thread.sleep(4000);
			
			
			sampleClient.disconnect();
			System.out.println("paho-client disconnected");
		} catch (MqttException me) {
			me.printStackTrace();
		}
	}

	@Override
	public void pushMessage(IServerThread socket, String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendToClient(String uuid, IServerThread socket, String msg) {
		// TODO Auto-generated method stub
		
	}

}
