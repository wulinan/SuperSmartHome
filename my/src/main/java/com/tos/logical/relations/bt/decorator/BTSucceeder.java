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

import com.tos.logical.relations.bt.model.BTNode;

/**
 *
 * @author wulinan
 */
public class BTSucceeder extends BTDecorator {
    
    public BTSucceeder(String name) {
        super(name);
    }
    
    public BTSucceeder() {
        super("BTSucceeder-noname");
    }
    
    public BTSucceeder(String name, BTNode child) {
        super(name, child);  
    }
    
    @Override
    public int process() {
        // we call the basic behavior of a BTDecorator node
        super.process();
        // Whatever the result of the child, we consider it's a success
        this.execution.setSuccess();
        
        return this.execution.getStatus();
        
        
    }
    
}
