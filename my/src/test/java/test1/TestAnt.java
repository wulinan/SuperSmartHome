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

import test1.*;

import TestAction.*;
import java.util.ArrayList;

import com.tos.logical.relations.bt.BehaviorTree;
import com.tos.logical.relations.bt.composite.BTSequence;

/**
 *
 * @author wulinan
 */
public class TestAnt {
    
    public static void main (String[] args){
        
        // we define the basic entities
        Entity base = new Entity(0,0,"base");
        ArrayList<Entity> ants = new ArrayList<Entity>();
        ants.add(new Entity(0,0,"ant0"));
        ArrayList<Entity> food = new ArrayList<Entity>();
        for (int i=0;i<5;++i) {
            food.add(new Entity(Math.round(40*Math.random()-20),Math.round(40*Math.random()-20),"food"+i));
        }
        
        // we create a behavior for ants
        BehaviorTree bt = new BehaviorTree();
        bt.setRoot(new BTSequence());
        ((BTSequence) (bt.getRoot())).add(new FindNearestFood());
        ((BTSequence) (bt.getRoot())).add(new GoToFood());
        ((BTSequence) (bt.getRoot())).add(new GoBackToBase());
        ants.get(0).setAI(bt);
//        bt.add(new BTSelector());
        
        
        
        
        // we had the entities to our engine/balckboard
        TestData data = new TestData(base);
        data.food = food;
        data.ants = ants;
        data.ants.get(0).initAI(data);
        
        
        // main loop - we test the AI
        while(true) {
            //long time1 = System.currentTimeMillis();
             data.ants.get(0).update();
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            //long time = System.currentTimeMillis()-time1;
            //System.out.println("time:"+time+"ms");
        }
        
        
        
        
        
    }
    
}
