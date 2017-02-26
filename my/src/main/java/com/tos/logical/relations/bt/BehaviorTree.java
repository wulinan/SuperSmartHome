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
package com.tos.logical.relations.bt;

import java.util.Map;

import com.google.gson.annotations.Expose;
import com.tos.logical.relations.bt.model.BTNode;

/**
 *
 * @author valentin
 */
public class BehaviorTree {
	//json
	@Expose
	public String id;
	@Expose
	public String title ;
	@Expose
	public String description ;
	@Expose
	public Map<String, String> properties;

	@Expose
	public Map<String, BTNode> nodes;
	@Expose
	private String root;
	@Expose
    private BTNode rootNode;
    
    
    
    public BehaviorTree() {
        
    }
    
    public BehaviorTree(Object nData, Object nActor) {
        this.rootNode.init(nData, nActor);
    }
    
    public void initFromJson(){
    	
    	setRoot(nodes.get(root));
    	getRoot().initFromJson(nodes);
    	
    }
    
    public void setRoot(BTNode node) {
        this.rootNode = node;
    }
    
//    public void add(BTNode node) {
//        this.setRoot(node);
//    }
    
    public BTNode getRoot() {
        return this.rootNode;
    }
    
    public BTNode getRunning() {
        return (this.rootNode.getRunning());
    }
    
    public void init(Object nData, Object nActor) {
        this.rootNode.init(nData, nActor);
    }
    
    public int execute() {
        return this.rootNode.process();
    }
    
}
