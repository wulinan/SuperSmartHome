package com.tos;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.tos.enums.DeviceType;
import com.tos.logical.relations.RelationManager;
import com.tos.logical.relations.bt.BehaviorTree;
import com.tos.message.MessageManager;
import com.tos.module.devices.Device;
import com.tos.module.devices.DeviceManager;
import com.tos.module.devices.ManagerFactory;
import com.tos.module.driver.SocketsManager;

/**
 * Hello world!
 *整个项目函数入库
 */
public class App 
{
	
	private SocketsManager socketsManager = SocketsManager.getInstance();
	private MessageManager messageManager = MessageManager.getInsatnce();
	private static RelationManager relationManager = RelationManager.getInstance();
	 
	
    public static void main( String[] args )
    {
    	try {
			System.out.println(InetAddress.getLocalHost().getHostAddress());
			System.out.println("------------5s以后开始测试AI-------------");
			Thread.sleep(50000);
			relationManager.runMainLoopAsThread();
			Device device = ManagerFactory.getDeviceManager(DeviceType.Light).getDevice(null);
			
			BehaviorTree tree =  RelationManager.generateTestBt(device);
			relationManager.addBehaviorTree(tree);
			
		} catch (UnknownHostException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
}
