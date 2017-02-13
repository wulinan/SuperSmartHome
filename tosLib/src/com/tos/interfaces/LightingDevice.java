package com.tos.interfaces;

public interface LightingDevice extends Device {
	
	/**
	 * 灯光调暗
	 * @return
	 */
	public boolean turnDown();
	
	/**
	 * 灯光调亮
	 * @return
	 */
	public boolean turnUp();
	
	/**
	 * 换颜色
	 * @param colorname
	 * @return
	 */
	public boolean changeColor(String colorname);
	
}
