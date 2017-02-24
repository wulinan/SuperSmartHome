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

import com.tos.logical.relations.bt.model.BTNode;

/**
 * BTComposite
 * Abstract class representing a Decorator node in a behavior tree
 * a Composite node has only one child
 * @author valentin
 */
public abstract class BTDecorator extends BTNode {
    
    protected BTNode child;
    
    
    public BTDecorator(String name) {
        super(name);   
    }
    
    public BTDecorator(String name, BTNode nChild) {
        super(name);
        this.child = nChild;    
    }
    
    public void add(BTNode child) {
        this.setChild(child);
    }
    
    public void setChild(BTNode nChild) {
        this.child = nChild;
    }
    
    public BTNode getChild() {
        return this.child;
    }
    
    public void init(Object nData, Object nActor) {
        // we call the BTNode init function that will associate nDta with the data attribute
        super.init(nData, nActor);
        
        // if this node doesn't have a child, there is a huge problem
        if (this.child == null) {
            // we say there is a failure at the initialization
            this.execution.setFailure();
            // we exit the initialization before trying to access the child that doesn't exist
            return;
        }
        
        // Decorator node only has one child, so we initailize it at the same time
        if (!this.execution.isInitialized()) { // Node was not initalized yet
            // we give all the information we have by default, maybe not the best thing possible
            this.child.init(nData, nActor);
        }
        
        // we can set our execution status to initailized
        this.execution.setInitialized();
        
        
    }
    
    @Override
    public int process() {
        
        int childStatus;
        
        // we run this node
        this.execution.setRunning();

        // we process the child node
        childStatus = this.child.process();
        
        // we change our status to the child status
        this.execution.setStatus(childStatus);
        
        return this.execution.getStatus();
    }
    
    public BTNode getRunning() {
        
        BTNode runningNode;
        
        if (this.execution.isRunning()) {
            // it's either this node, or another node is running below
            runningNode = this.child.getRunning();
            if (runningNode != null) { // it's the child, or something below
                return runningNode;
            }
            return this;
        }
        return null;
    }
    
}
