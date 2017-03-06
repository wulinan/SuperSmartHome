package com.tos.utils;

public enum Command {

	Register("register"),OnLine("onl"),OffLine("offl"),HeartBeat("hb"),CMDResponse("cmdack"),
	ErrorResponse("errack"),Alert("alert"),Query("query"),


	TurnOn("turn_on"),TurnOff("turn_off"), PlayRemote("pr"),PlayLocal("pl");

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
