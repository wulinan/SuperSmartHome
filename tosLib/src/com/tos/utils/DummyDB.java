package com.tos.utils;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

/**
 * db数据，自定义格式: uuid,index,device_class_name
 * @author wulinan
 *
 */
public class DummyDB {
	RandomAccessFile db;
	String fileName = "lib.db";
	//(device_class_name,index) -uuid
	Map<DBNode, String> dataMap = new HashMap<DBNode, String>();
	
	public static DummyDB Instance ;// new DummyDB(".");

	public synchronized static DummyDB getInstance(){
		if(Instance==null)
			throw new RuntimeException("please set dir first!!");
		return Instance;
	}
	
	public synchronized static void setDir(String dir){
		if (Instance != null) {
			return;
		}
		Instance = new DummyDB(dir);
	}
	
	private DummyDB(String dir) {
		try {
//			System.out.println("in create DummyDB!!");
			db = new RandomAccessFile(dir+"/"+fileName, "rw");
			initMap();
			db.close();
//			System.out.println("DummyDB created!!!!");
			Instance = this;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initMap() throws IOException{
		String line = db.readLine();
		while(line != null){
//			System.out.println(line);
			
			String[] foos = line.split(",");
			if(foos.length == 3){
				String uuid = foos[0];
				int index = Integer.parseInt(foos[1]);
				String className = foos[2];
				DBNode node = new DBNode(className, index);
				dataMap.put(node, uuid);
			}
			line = db.readLine();
		}
	}
	
	/**
	 * 测试是否包含该device，index为注册的第几个
	 * @param deivceClass
	 * @param index
	 * @return
	 */
	public boolean containDevice(Class deivceClass,int index){
		DBNode node = new DBNode(deivceClass.getName(), index);
		return dataMap.containsKey(node);
	}
	
	public String getDeviceUuid(Class deivceClass,int index){
		DBNode node = new DBNode(deivceClass.getName(), index);
		if(dataMap.containsKey(node)){
			return dataMap.get(node);
		}
		return null;
	}
	
	/**
	 * 将uuid写如数据库
	 * @param deviceClass
	 * @param index
	 * @param uuid
	 * @return
	 */
	public boolean writeToDB(Class deviceClass,int index,String uuid){
		boolean res = true;
		DBNode node = new DBNode(deviceClass.getName(), index);
		dataMap.put(node, uuid);		
		try {
			FileWriter writer = new FileWriter(fileName, false);
			for(DBNode node2 : dataMap.keySet()){
				writer.write(String.format("%s,%s,%s", uuid, node2.index,node2.className));
			}
			writer.close();
		} catch (IOException e) {
			res=false;
			e.printStackTrace();
		}
		return res;
	}
	
	
}

class DBNode{
	String className;
	int index;
	public DBNode(String klassName,int index) {
		this.className = klassName;
		this.index = index;
	}
	
	@Override
	public int hashCode() {
		return this.className.hashCode()&(this.index<<10);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DBNode){
			DBNode dbNode = (DBNode) obj;
			if(dbNode.className.equals(this.className) && dbNode.index==this.index)
				return true;
		}
		return false;
	}
}
