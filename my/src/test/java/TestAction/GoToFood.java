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

/**
 *
 * @author valentin
 */
public class GoToFood extends ActionModel {
    
    @Override
    public int process() {
        
        // we move to the nearest food
        double speedX;
        double speedY;
        speedX = (this.actor.getTarget().getPosX()-this.actor.getPosX())/Math.max(1,this.norm(this.actor,this.actor.getTarget()));
        speedY = (this.actor.getTarget().getPosY()-this.actor.getPosY())/Math.max(1,this.norm(this.actor,this.actor.getTarget()));
        
        this.actor.setPosX(this.actor.getPosX()+speedX);
        this.actor.setPosY(this.actor.getPosY()+speedY);
        
        System.out.println(this.actor.getName()+" is moving toward "+this.actor.getTarget().getName()+" at speed ("+speedX+";"+speedY+"), pos : ("+this.actor.getPosX()+";"+this.actor.getPosY()+")");
        
        if (Math.abs(this.actor.getPosX()-this.actor.getTarget().getPosX()) <= this.EPS && Math.abs(this.actor.getPosY()-this.actor.getTarget().getPosY()) <= this.EPS) {
            this.data.food.remove(this.actor.getTarget());
            return BTExecution.CONST_EXEC_SUCCESS;
        }
        
        return BTExecution.CONST_EXEC_RUNNING;
    }
    
    @Override
    public boolean isRunnable() {
        
        return this.actor.getTarget() != null;
    }

}
