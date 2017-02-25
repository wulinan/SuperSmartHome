package com.tos.logical.relations.bt.model;

import java.util.Calendar;
import com.tos.module.devices.Device;

public class BTOperatorUntilTimeOut extends BTLeaf{
	
	 protected Device actor;
	 protected OperatorEntity data;
	 private boolean result = false;
	 private float endTime = 0;
	    
	    @Override
	    public void init(Object nData, Object nActor) {
	        this.actor = (Device) nActor;
	        this.data = (OperatorEntity) nData;
	    }
		@Override
		public int process() {
			String cmd = data.cmd;
			if(!this.result){
				this.result = actor.operator(cmd,data.cmdData);
				if(!this.result){
					this.endTime = Calendar.getInstance().getTimeInMillis() + data.time*1000;
					return BTExecution.CONST_EXEC_FAILURE;
				}
			}else{
			float now = Calendar.getInstance().getTimeInMillis();
			if(now < this.endTime){
				return BTExecution.CONST_EXEC_RUNNING;
			}
			}
			return BTExecution.CONST_EXEC_SUCCESS;
		}
		
		public static class OperatorEntity{
			public String cmd;
			public String cmdData;
			public int time;//s
		}
		
}
