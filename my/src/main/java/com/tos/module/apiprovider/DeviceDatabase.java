package com.tos.module.apiprovider;

import java.util.Arrays;
import java.util.UUID;

import com.tos.enums.Status;

/**
 * for test
 * @author wulinan
 *
 */
public class DeviceDatabase {

	public static DevicesBean getDevices() {
		DeviceBean bean1 =  new DeviceBean();
		bean1.uuid = UUID.randomUUID().toString();
		bean1.status = Status.OnLine.toString();
		
		DeviceBean bean2 =  new DeviceBean();
		bean2.uuid = UUID.randomUUID().toString();
		bean2.status = Status.Broken.toString();
		
		DevicesBean beans = new DevicesBean();
		beans.devices = Arrays.asList(bean1,bean2);
		
		return beans;
	}

	public static DevicesBean getDeivceByType(String type) {
		DevicesBean beans =  getDevices();
		beans.type = type;
		return beans;
	}

}
