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
package TestAction;

import com.tos.logical.relations.bt.model.BTExecution;
import com.tos.logical.relations.bt.model.BTLeaf;

import test1.Entity;
import test1.TestData;

/**
 *
 * @author valentin
 */
public abstract class ActionModel extends BTLeaf {
    
    protected Entity actor;
    protected TestData data;
    public final double EPS = 0.1;
    
    @Override
    public void init(Object nData, Object nActor) {
        this.actor = (Entity) nActor;
        this.data = (TestData) nData;
    }
    
    
    
    public double norm(Entity actor, Entity target) {
        double norm = Math.sqrt((target.getPosX()-actor.getPosX())*(target.getPosX()-actor.getPosX()) + (target.getPosY()-actor.getPosY())*(target.getPosY()-actor.getPosY()));
        return norm;
    }
    
}
