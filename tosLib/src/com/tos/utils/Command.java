package com.tos.utils;

public enum Command {
	
	Register("register"),OnLine("online"),OffLine("offline"),HeartBeat("hb"),CMDResponse("cmdack"),
	ErrorResponse("errack"),Alert("alert"),Query("query"),
	Operation("operate"),
	Reponse("response"),
	PutUrlPlay("puturl"),
	TurnOn("turn_on"),TurnOff("turn_off"), PlayRemote("playurl"),PlayLocal("playlocal"),GetUrlPlay("geturl");
	
	private String cmd;
	private Command(String cmd){
		this.cmd = cmd;
	}
	
	public String toCmd(){
		return this.cmd;
	}
	
	public static Command getCmd(String cmd){
		for(Command c:values()){
			if(c.cmd.equals(cmd))
				return c;
		}
		throw new IllegalArgumentException("no such cmd = " + cmd);
	}
}
