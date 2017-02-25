package com.tos.logical.relations.bt.model;

import com.tos.logical.relations.bt.model.BTOperatorUntilTimeOut.OperatorEntity;
import com.tos.module.devices.Device;


/**
 * 用于让设备执行某个操作
 * @author wulinan
 *
 */
public class BTOperatorode extends BTLeaf {
    protected Device actor;
    protected OperatorEntity data;
    
    @Override
    public void init(Object nData, Object nActor) {
        this.actor = (Device) nActor;
        this.data = (OperatorEntity) nData;
    }
	@Override
	public int process() {
		String cmd = data.cmd;
		boolean res = actor.operator(cmd,data.cmdData);
		if(res){
			return BTExecution.CONST_EXEC_SUCCESS;
		}
		return BTExecution.CONST_EXEC_FAILURE;
		
	}

}
