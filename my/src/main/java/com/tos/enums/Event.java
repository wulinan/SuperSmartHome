package com.tos.enums;

public enum Event {
	
	Register("register"),OnLine("onl"),OffLine("offl"),HeartBeat("hb"),CMDResponse("cmdack"),
	ErrorResponse("errack"),Alert("alert");
	
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

