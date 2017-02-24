/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tos.logical.relations.bt.model;

/**
 * BTNode
 * Basic representation of a node in a behavior tree
 * This class is abstract, a node os always a composite node, a decorator node or a leaf
 * @author valentin
 */
public abstract class BTNode {
    
    /* status
    * -1 Uninitialized
    * Unitialized means we need to initialize it first before processing it
    * 0 failure
    * Failure means something went wrong during the execution of this node
    * 1 success
    * Success means the node was succesfully executed
    * 2 running
    * Running means the execution of this node hasn't been finished yet and will be executed at the next iteration
    */
    protected BTExecution execution;
    /*
    * unique name represneting this node
    */
    protected String name;
    
    /*
    * non defined object containing all useful information to process this node
    */
    //protected Object data;
    
    /*
    * The actor (entity or whatever) which behavior is represented by a behavior tree containing this node
    */
    //protected Object actor;
    
    public BTNode() {
        this.execution = new BTExecution();
    }
    
    public BTNode (String nName) {
        this.execution = new BTExecution();
        this.name = nName;
    }
    
    public BTNode (String nName, int status) {
        this.execution = new BTExecution(status);
        this.name = nName;
    }
    
    /* init()
    * initialize a node when it is first called by its parent
    * You should set the execution status to initalized if you overwirte this function
    * It can avoid problems when checking if the node has been initialized in the future bun never runned
    */
    public void init(Object nData, Object nActor) {
        //this.data = nData;
        this.execution.setInitialized();
    }
    
    /* process()
    * Process the action associated with a node
    * You should ALWAYS set the status of the execution of this ndoe berfore returning the value
    * The best way is to set the execution status to its new value and always return the execution status by this.execution.getStatus()
    * @return the status of this node to its parent
    */
    public abstract int process();
    
    /* isRunnable()
    * Compute if this node can be run (conditions met) or not
    * You should ALWAYS initiate the node before testing if it is runnable
    * Used in BTSelector and random selector nodes
    * @return true if the node can be processed, false else
    */
    public boolean isRunnable() {
        return true; // by default, the node is runnable
    }
    
    @Override
    public String toString() {
        return "BTNode-"+this.name;
    }
    
    public BTNode getRunning() {
        if (this.execution.isRunning()) {
            return this;
        }
        // we got here but we are not the last running node, there is some mistake
        return null;
    }
    

}
