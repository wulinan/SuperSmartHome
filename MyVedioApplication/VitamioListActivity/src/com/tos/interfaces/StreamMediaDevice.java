package com.tos.interfaces;

import java.util.List;

public interface StreamMediaDevice extends Device {
	
	/**
	 * 获取一个媒体的url
	 * @param mediaName
	 * @return
	 */
	
	public String getStreamUrl(String mediaName);
	
	/**
	 * 获取该设备所有的媒体
	 * @return
	 */
	public List<String> getAllMediaNames();
	
	
	//TODO 还有其他的媒体传输方式嘛？？？
}
