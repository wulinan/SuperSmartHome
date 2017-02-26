package com.tos.utils;

import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tos.App;
import com.tos.logical.relations.bt.BehaviorTree;
import com.tos.logical.relations.bt.composite.BTSequence;
import com.tos.logical.relations.bt.model.BTNode;
import com.tos.logical.relations.bt.model.BTOperatorNode;
import com.tos.logical.relations.bt.model.BTQueryNode;

public class JsonTreeNode {


	public static void main(String[] args) {
		//
		String json = App.getTestJson();
		GsonBuilder builder = new GsonBuilder();
//		RuntimeTypeAdapterFactory
		Map<String, BTNode> map = new HashMap<String,BTNode>();
		final RuntimeTypeAdapterFactory<BTNode> typeFactory = RuntimeTypeAdapterFactory  
		        .of(BTNode.class, "name") // Here you specify which is the parent class and what field particularizes the child class.
		        .registerSubtype(BTSequence.class, "Sequence") // if the flag equals the class name, you can skip the second parameter. This is only necessary, when the "type" field does not equal the class name.
		        .registerSubtype(BTOperatorNode.class, "BTOperatorNode")
		        .registerSubtype(BTQueryNode.class,"BTQueryNode")
		       ;
		Gson gson = builder.setPrettyPrinting()
		.registerTypeAdapterFactory(typeFactory).excludeFieldsWithoutExposeAnnotation().create();
//		System.out.println(json);
		BehaviorTree test = gson.fromJson(json, BehaviorTree.class);
		test.initFromJson();
//		System.out.println(json);
		System.out.println();
		System.out.println(gson.toJson(test));
//		test.initFromJson();
		
	}

}
