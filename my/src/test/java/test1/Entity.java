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
package test1;

import com.tos.logical.relations.bt.BehaviorTree;

import test1.*;


/**
 *
 * @author wulinan
 */
public class Entity {

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public Entity(double posX, double posY, String name) {
        this.posX = posX;
        this.posY = posY;
        this.name = name;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public void setTarget(Entity target) {
        this.target = target;
    }
    
    public Entity getTarget() {
        return this.target;
    }

    public BehaviorTree getAI() {
        return AI;
    }

    public void setAI(BehaviorTree AI) {
        this.AI = AI;
    }
    
    public void initAI(TestData data) {
        this.AI.init(data, this);
    }
    
    public void update() {
        
        
        if (this.AI != null) {
        	System.out.println("update");
            this.AI.execute();
        }
    }
    
    private double posX;
    private double posY;
    private Entity target;
    private String name;
    private BehaviorTree AI;
    
}
