package com.tos.utils;

public enum Command {
	
	Register("register"),OnLine("onl"),OffLine("offl"),HeartBeat("hb");
	
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
