package com.tos.module.apiprovider;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tos.message.QueryResultMessageHandler;

@Path(value = "/devices")
public class DeviceApiManager {
	private static String isOn = "no";
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
	
	@GET
	@Path("/state")
	@Produces(MediaType.TEXT_PLAIN)
	public String getProfile(){
		return "ok";
	}
	
	@GET
	@Path("/start/camera")
	@Produces(MediaType.TEXT_PLAIN)
	public String startCamera(){
		QueryResultMessageHandler.getInstance().startCamera();
		return "ok";
	}
	
	@GET
	@Path("/situationModeCtrl/{modeid}")
	@Produces(MediaType.TEXT_PLAIN)
	public String putProfile(@PathParam("modeid") String modeid){
		if (modeid.equals("leave")||modeid.equals("sleep")) {
			isOn = "no";
			return modeid;
		}else if (modeid.equals("home")) {
			isOn = "yes";
			return "home";
		}
		return "Failed";
		
	}
}
