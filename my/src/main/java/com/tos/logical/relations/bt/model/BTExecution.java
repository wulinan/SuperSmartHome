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
package com.tos.logical.relations.bt.model;

/**
 * BTExecution
 * Object controlling the good execution of a node, it includes the execution status of the node associated
 * a Composite node has only one child
 * @author valentin
 */
public class BTExecution {
    
    /* status
    * 0 Uninitialized
    * Unitialized means we need to initialize it first before processing it
    * 1 failure
    * Failure means something went wrong during the execution of this node
    * 2 success
    * Success means the node was succesfully executed
    * 3 running
    * Running means the execution of this node hasn't been finished yet and will be executed at the next iteration
    * 127 default
    * No need to initialize it, but doesn't ahve a result. Should not be used
    */
    private int status = 0;
    
    public final static int CONST_EXEC_UNINITIALIZED = 0;
    public final static int CONST_EXEC_INITIALIZED = 1;
    public final static int CONST_EXEC_FAILURE = 2;
    public final static int CONST_EXEC_SUCCESS = 3;
    public final static int CONST_EXEC_RUNNING = 4;
    public final static int CONST_EXEC_DEFAULT = 127;
    
    
    public BTExecution() {
        this.status = BTExecution.CONST_EXEC_UNINITIALIZED; // Node not initialized by default
    }
    
    public BTExecution(int nStatus) {
        this.status = nStatus;
    }
    
    public void setInitialized() {
        this.status = BTExecution.CONST_EXEC_INITIALIZED;
    }
    
    public void setSuccess() {
        this.status = BTExecution.CONST_EXEC_SUCCESS;
    }
    
    public void setFailure() {
        this.status = BTExecution.CONST_EXEC_FAILURE;
    }
    
    public void setRunning() {
        this.status = BTExecution.CONST_EXEC_RUNNING;
    }
    
    public void setStatus(int nStatus) {
        this.status = nStatus;
    }
    
    public boolean isRunning() {
        if (this.status == BTExecution.CONST_EXEC_RUNNING) {
            return true;
        }
        return false;
    }
    
    public boolean hasSucceeded() {
        if (this.status == BTExecution.CONST_EXEC_SUCCESS) {
            return true;
        }
        return false;
    }
    
    public boolean hasFailed() {
        if (this.status == BTExecution.CONST_EXEC_FAILURE) {
            return true;
        }
        return false;
    }
    
    public boolean isInitialized() {
        if (this.status == BTExecution.CONST_EXEC_UNINITIALIZED) {
            return false;
        }
        return true;
    }
    
    public int getStatus() {
        return this.status;
    }
}
