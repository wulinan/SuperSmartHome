package com.tos.logical.relations.bt.model;

import com.tos.module.devices.Device;
import com.tos.utils.Properties;


/**
 * 用于让设备执行某个操作
 * @author wulinan
 *
 */
public class BTOperatorNode extends BTLeaf {
    protected Device actor;
 
    
    @Override
    public void init(Object nData, Object nActor) {
        this.actor = (Device) nActor;
        this.properties = (Properties) nData;
    }
    
    public void init(Properties data, Device nActor){
    	this.actor = nActor;
    	this.properties = data;
    }
	@Override
	public int process() {
		String cmd = properties.cmd_id;
		boolean res = actor.operator(cmd,properties.cmd_data);
		if(res){
			return BTExecution.CONST_EXEC_SUCCESS;
		}
		return BTExecution.CONST_EXEC_FAILURE;
		
	}

}
