package com.tos.enums;

public enum Event {
	Register("register"),OnLine("online"),OffLine("offline"),HeartBeat("hb"),CMDResponse("cmdack"),
	ErrorResponse("errack"),Alert("alert"),Query("query"),
	Operation("operate"),
	Reponse("response"),
	PutUrlPlay("puturl"),
	GetPlayerIp("getPlayerIp"),
	TurnOn("turn_on"),TurnOff("turn_off"), PlayRemote("playurl"),PlayLocal("playlocal"),GetUrlPlay("geturl");
	private String cmd;
	private Event(String cmd){
		this.cmd = cmd;
	}
	
	public String toCmd(){
		return this.cmd;
	}
	
	public static Event getCmd(String cmd){
		for(Event c:values()){
			if(c.cmd.equals(cmd))
				return c;
		}
		throw new IllegalArgumentException("no such cmd = " + cmd);
	}
}

