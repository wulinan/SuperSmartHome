package com.tos;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.tos.enums.DeviceType;
import com.tos.logical.relations.RelationManager;
import com.tos.logical.relations.bt.BehaviorTree;
import com.tos.message.MessageManager;
import com.tos.module.devices.Device;
import com.tos.module.devices.ManagerFactory;
import com.tos.module.driver.SocketsManager;
import com.tos.utils.LogManager;

/**
 * Hello world!
 *整个项目函数入库
 */
public class App 
{
	
	private static SocketsManager socketsManager = SocketsManager.getInstance();
	private static MessageManager messageManager = MessageManager.getInsatnce();
	private static RelationManager relationManager = RelationManager.getInstance();
	 
	public static boolean Debug = true;
	private static Logger logger = LogManager.getLogger(App.class);
	
    public static void main( String[] args )
    {
    	try {
			System.out.println(InetAddress.getLocalHost().getHostAddress());
			logger.info("------------10s以后开始测试AI-------------");
//			System.out.println("------------10s以后开始测试AI-------------");
			TimeUnit.SECONDS.sleep(10);
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
