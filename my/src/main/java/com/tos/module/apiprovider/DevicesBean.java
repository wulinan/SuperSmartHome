package com.tos.module.apiprovider;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DevicesBean {
	public List<DeviceBean> devices;
	public String type;
}
