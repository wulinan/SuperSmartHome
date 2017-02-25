package com.tos.enums;

public enum DeviceType {
	Light,
	Player,
	StreamMedia,
	TemperatureCtrl,
	Extension,
	SimpleOne;
	
	public static void main(String[] args) {
	DeviceType type = DeviceType.valueOf("light");
	System.out.println(type);
	}
	
}
