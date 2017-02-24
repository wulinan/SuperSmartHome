/*
 * Copyright 2015 valentin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tos.logical.relations.bt.decorator;

import com.tos.logical.relations.bt.model.BTExecution;
import com.tos.logical.relations.bt.model.BTNode;

/**
 *
 * @author valentin
 */
public class BTInverter extends BTDecorator{

    public BTInverter(String name) {
        super(name);
    }
    
    public BTInverter() {
        super("BTInverter-noname");
    }
    
    public BTInverter(String name, BTNode child) {
        super(name, child);  
    }
    
    @Override
    public int process() {
        
        // we get the status of the child, by running the process of a usual BTDecorator
        int status = super.process();
        // if the status is success, we change to failure. If it's failure, we change to success
        if (status == BTExecution.CONST_EXEC_FAILURE) {
            this.execution.setSuccess();
        } else if (status == BTExecution.CONST_EXEC_SUCCESS) {
            this.execution.setFailure();
        }
        
        return this.execution.getStatus();
    }


    
}
