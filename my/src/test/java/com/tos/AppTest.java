package com.tos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends Socket
{
    
	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 2016;
	
	private Socket client;
	private PrintWriter out;
	private BufferedReader in;
	
	//与服务器连接，并输入发送消息
	public AppTest() throws Exception{
		super(SERVER_IP,SERVER_PORT);
		client = this;
		out = new PrintWriter(this.getOutputStream(),true);
		in = new BufferedReader(new InputStreamReader(this.getInputStream()));
		new ReadLineThread();
		
		while(true){
			in = new BufferedReader(new InputStreamReader(System.in));
			String input = in.readLine();
			out.println(input);
		}
	}
	
	//用于监听服务器端向客户端发送消息线程类
	class ReadLineThread extends Thread{
		private BufferedReader buff;
		public ReadLineThread(){
			try{
				buff = new BufferedReader(new InputStreamReader(client.getInputStream()));
				start();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public void run(){
			try{
				while(true){
					String result = buff.readLine();
					if("on".equals(result)) System.out.println(result);
					else if("off".equals(result)) System.out.println(result);
					else if("state".equals(result)) {
						System.out.println(result);
						out.println("on");
					}
					else System.out.println(result);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
        try{
        	new AppTest();//启动客户端
        }catch(Exception e){
        	e.printStackTrace();
        }
	}
}
