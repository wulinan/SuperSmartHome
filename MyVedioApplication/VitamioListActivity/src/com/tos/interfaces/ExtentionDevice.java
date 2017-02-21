package com.tos.interfaces;

import java.util.List;

/**
 * 扩展设备接口
 * @author wulinan
 *
 */
public interface ExtentionDevice extends Device {
	
	/**
	 * 获取设备要扩展的设备操作，设备注册的时候，管理器会自己调用此方法
	 * @return
	 */
	public List<ExtentionOperation> getExtenionOprations();
	
	/**
	 * 执行设备的一个操作，并返回是否成功，和数据
	 * @param opId
	 * @return
	 */
	public ExecuteResult executeExtentionOpration(String opId);
	
}

class ExecuteResult{
	public String data;
	public boolean isSucceess;
}

class ExtentionOperation{
	public String opid;
	public String name;
	public String desc;
}
