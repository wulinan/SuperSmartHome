/*
 * Copyright 2015 wulinan.
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
 * @author wulinan
 */
public class BTRepeater extends BTDecorator {
    
    private int nbIteration;
    private int remainingIteration;
    
    public BTRepeater(String name, int nbIteration) {
        super(name);
        this.nbIteration = nbIteration;
        // we set the number of iteration to do
        this.remainingIteration = this.nbIteration;
    }
    
    public BTRepeater() {
        super("BTRepeater-noname");
    }
    
    public BTRepeater(String name, BTNode child) {
        super(name, child);  
    }
    
    @Override
    public int process() {
        
        int childStatus;
        // we call the basic behavior of a BTDecorator node
        childStatus = super.process();
        
        // by default, we are running
        this.execution.setRunning();
        
        // we decrement the number of remaining iteration if child has succeeded
        if (childStatus == BTExecution.CONST_EXEC_SUCCESS) {
            --this.remainingIteration;
            // did we achieve the numebr of iteration ? If yes, it's a success
            if (this.remainingIteration <= 0) {
                this.execution.setSuccess();
                // we reinitialize the number of iteration
                this.remainingIteration = this.nbIteration;
            }
        }
        // we do nothing if it's still running
        
        // If there is a failure, we stop the iteration and send it to its parent node
        if (childStatus == BTExecution.CONST_EXEC_FAILURE) {
            this.execution.setFailure();
        }
        
        return this.execution.getStatus();
        
        
    }
    
    
}
