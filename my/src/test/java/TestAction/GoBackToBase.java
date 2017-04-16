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
package TestAction;

import com.tos.logical.relations.bt.model.BTExecution;

/**
 *
 * @author wulinan
 */
public class GoBackToBase extends ActionModel {

    @Override
    public int process() {
        // we move to the nearest food
        double speedX;
        double speedY;
        speedX = (this.data.base.getPosX()-this.actor.getPosX())/Math.max(1,this.norm(this.actor,this.data.base));
        speedY = (this.data.base.getPosY()-this.actor.getPosY())/Math.max(1,this.norm(this.actor,this.data.base));
        
        this.actor.setPosX(this.actor.getPosX()+speedX);
        this.actor.setPosY(this.actor.getPosY()+speedY);
        
        System.out.println(this.actor.getName()+" is moving toward "+this.data.base.getName()+" at speed ("+speedX+";"+speedY+")");
        
        if (Math.abs(this.data.base.getPosX()-this.actor.getPosX()) <= this.EPS && Math.abs(this.data.base.getPosY()-this.actor.getPosY()) <= this.EPS)
            return BTExecution.CONST_EXEC_SUCCESS;
        
        return BTExecution.CONST_EXEC_RUNNING;
    }
    
}
