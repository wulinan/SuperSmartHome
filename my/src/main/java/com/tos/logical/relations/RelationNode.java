package com.tos.logical.relations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * 
 * 
 * 
 * 
 * 	{
		strategyID:”sid_01”,
		strategyState:”1”,
		conditionList:
		[
		{
			deviceID:”0xDFF0”,
			statusCode:”0x10”,
			operation:”=”,
			compareValue:”0x11”
		}
		],
		conditionRelation:”0”,//如果只有一个条件，则不用关注这个选项。
		actionList:
		[
			{
				deviceID:”0xAFF0”,
				commandCode:”0x10”
			}
		]
		}

 * @author wulinan
 *
 */
public class RelationNode {
	public String strategyID;
	public String strategyState;
	public ConditionNode[] conditionList;
	public String conditionRelation;
	public ActionNode[] actionList;
	
	
	public static void main(String[] args) {
		String now_json = "{strategyID:\"sid_01\","+
		"strategyState:\"1\","+
		"conditionList:"+
		"[{"+
			"deviceID:\"0xDFF0\","+
			"statusCode:\"0x10\","+
			"operation:\"=\","+
			"compareValue:\"0x11\""+
		"}],"+
		"conditionRelation:\"0\","+//如果只有一个条件，则不用关注这个选项。
		"actionList:"+
		"[{"+
				"deviceID:\"0xAFF0\","+
				"commandCode:\"0x10\""+
			"}"+
		"]}";
		GsonBuilder builder = new GsonBuilder();
		Gson gson= builder.serializeNulls().create();

		
		RelationNode node = gson.fromJson(now_json, RelationNode.class);
		System.out.println(node.conditionList[0].operation);
		String s = gson.toJson(node);
		RelationNode node2 = gson.fromJson(s, RelationNode.class);
		System.out.println(node2.conditionList[0].operation);
	}
	
}
