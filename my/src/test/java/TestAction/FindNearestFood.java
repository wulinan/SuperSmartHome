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
public class FindNearestFood extends ActionModel {
    
    @Override
    public int process() {

        // we search for the enarest food
        double minDistance = Double.MAX_VALUE;
        int selectedTarget = -1;
        double dist = 0;
        int size = this.data.food.size();
        
        if (size > 0) {
        
            for (int i = 0; i < size; ++i) {
                dist = this.norm(this.actor, this.data.food.get(i));
                if (dist < minDistance) {
                    minDistance = dist;
                    selectedTarget = i;
                }
                    
            }
            
        } else {
            return BTExecution.CONST_EXEC_FAILURE;
        }
        
        this.actor.setTarget(this.data.food.get(selectedTarget));
        System.out.println(this.actor.getName()+" selected "+this.actor.getTarget().getName()+" as its new target (pos : ("+this.actor.getTarget().getPosX()+";"+this.actor.getTarget().getPosY()+")");
        return BTExecution.CONST_EXEC_SUCCESS;
    }

}
