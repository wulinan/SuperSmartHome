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
package com.tos.logical.relations.bt.composite;

import com.tos.logical.relations.bt.model.BTExecution;

/**
 *
 * @author wulinan
 */
public class BTSelector extends BTComposite {
    

    @Override
    public void init(Object nData, Object nActor) {
        // we need to init the children to be able to use the isRunnable()
        int size = this.childrenNode.size();
        for (int i=0;i<size;++i) {
            this.childrenNode.get(i).init(nData,nActor);
        }
    }

    @Override
    /*
    * process()
    * The order of the children is very important ! Process will test each child until it finds one with a isRunnable returning true, and then run this child
    */
    public int process() {
        
        this.execution.setRunning();
        
        int size = this.childrenNode.size();
        int childStatus = BTExecution.CONST_EXEC_FAILURE; // if we do not process a child, we consider it a failure
        
        for (int i=0;i<size;++i) {
            
            if (this.childrenNode.get(i).isRunnable()) { // the first one to be runnable is processed
                
                childStatus = this.childrenNode.get(i).process();
                break;
                
            }
        }
        
        this.execution.setStatus(childStatus);
        
        return  this.execution.getStatus();
    }
    
}
