package com.tos.module.apiprovider;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.internal.inject.Custom;

@Path(value = "/devices")
public class DeviceApiManager {
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public DevicesBean getDeviceList() {
		return DeviceDatabase.getDevices();
	}
	
	@GET
	@Path("/{type}")
	@Produces({MediaType.APPLICATION_JSON})
	public DevicesBean getDeviceByType(@PathParam("type") String type){
		return DeviceDatabase.getDeivceByType(type);
		
	}
	
	@POST
	@Path("/op/{uuid}/{opid}")
	@Produces({MediaType.APPLICATION_JSON})
	public OperatorRespose operatorDevice(@PathParam("uuid") String uuid,@PathParam("opid")String opid){
		System.out.println("test");
		return new OperatorRespose();
		
	}
	
	@POST
	@Path("/model")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public OperatorRespose operatorpattern(String json){
		System.out.println(json);
		return new OperatorRespose();
		
	}
	
}
