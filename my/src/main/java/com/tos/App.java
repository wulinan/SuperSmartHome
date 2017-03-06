package com.tos;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.tos.enums.DeviceType;
import com.tos.logical.relations.RelationManager;
import com.tos.logical.relations.bt.BehaviorTree;
import com.tos.message.MessageManager;
import com.tos.module.apiprovider.ApiProviderMain;
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
			ApiProviderMain.main(args);
//			System.out.println("------------10s以后开始测试AI-------------");
			TimeUnit.SECONDS.sleep(10);
//			relationManager.runMainLoopAsThread();
//			Device device = ManagerFactory.getDeviceManager(DeviceType.Light).getDevice(null);
//			
//			BehaviorTree tree =  RelationManager.generateBTFromJson(getTestJson(),device);//RelationManager.generateTestBt(device);
//			relationManager.addBehaviorTree(tree);
			
			
			
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    
    public static String getTestJson(){
    	// 读取配置文件  
        ClassLoader cl = App.class.getClassLoader();  
        InputStream inputStream = null;  
        if (cl != null) {  
            inputStream = cl.getResourceAsStream("test.json");  
        } else {  
            inputStream = ClassLoader  
                    .getSystemResourceAsStream("test.json");  
        }  
         try {
			return IOUtils.toString(inputStream, "utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         return null;
    }
}
