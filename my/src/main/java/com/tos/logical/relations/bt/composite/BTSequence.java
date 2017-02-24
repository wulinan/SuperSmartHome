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
package com.tos.logical.relations.bt.composite;

import java.util.Set;

import com.tos.logical.relations.bt.model.BTExecution;

/**
 *
 * @author valentin
 */
public class BTSequence extends BTComposite {
    
    private int sequencePosition = -1;

    @Override
    public void init(Object nData, Object nActor) {
        // If we init this node, it may be useful to init its children at the same time, because it may be followed by a 
        int size = this.children.size();
        for (int i=0;i<size;++i) {
            this.children.get(i).init(nData, nActor);
        }
        this.sequencePosition = 0;
    }

    @Override
    /*
    * process()
    * The order of the children is very important ! Process will test each child until it finds one with a isRunnable returning true, and then run this child
    */
    public int process() {
        
        if (!this.execution.isRunning()) {
            // we are at the beginning of a sequence
            this.sequencePosition = 0; // we will launch the first node of the sequence
            // we are running
            this.execution.setRunning();
        }

        int childStatus = this.children.get(this.sequencePosition).process();

        if (childStatus == BTExecution.CONST_EXEC_SUCCESS) {
            // we are still running, but we increment the next node to be called
            ++this.sequencePosition;
            
            if (this.sequencePosition == this.children.size()) { // we finished the sequence
                this.sequencePosition = 0;
                this.execution.setSuccess();
                return this.execution.getStatus();
                
            }
        }
        
        if (childStatus == BTExecution.CONST_EXEC_FAILURE) {
            this.execution.setFailure();
            return this.execution.getStatus();
        }
        
        return this.execution.getStatus();
    }
    
}
