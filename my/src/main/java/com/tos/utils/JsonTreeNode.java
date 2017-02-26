package com.tos.utils;

import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tos.App;
import com.tos.logical.relations.bt.BehaviorTree;
import com.tos.logical.relations.bt.model.BTNode;

public class JsonTreeNode {


	public static void main(String[] args) {
		//
		String json = App.getTestJson();
		GsonBuilder builder = new GsonBuilder();
//		RuntimeTypeAdapterFactory
		Map<String, BTNode> map = new HashMap<String,BTNode>();
		Gson gson = builder.setPrettyPrinting()
				/*.registerTypeAdapter(map.getClass(), 
				new JsonDeserializer<HashMap<String,BTNode>>() {
					HashMap<String, Class> map1 = new HashMap<String, Class>(){
						{
							put("Sequence", BTSequence.class);
							put("BTQueryNode", BTQueryNode.class);
						}
					};
					
					@Override
					public HashMap<String, BTNode> deserialize(JsonElement json, Type typeOfT,
							JsonDeserializationContext context) throws JsonParseException {
						if(json.isJsonObject()){
							HashMap<String, BTNode> map = new HashMap<>();
							for(Entry<String, JsonElement> entry :json.getAsJsonObject().entrySet()){
								String id = entry.getKey();
								JsonObject jsonObject =  entry.getValue().getAsJsonObject();
								String name = jsonObject.get("name").getAsString();
								BTNode object = context.deserialize(jsonObject, map1.get(name));
								if(name.equals("BTQueryNode")){
									BTQueryNode node1 = (BTQueryNode) object;
									node1.cmd_id = jsonObject.get("properties").getAsJsonObject().get("cmd_id").getAsString();
								}
								map.put(name, object);
							}
							return map;
						}
						return null;
					}
				})
				
	*/
		.create();
//		System.out.println(json);
		BehaviorTree test = gson.fromJson(json, BehaviorTree.class);
		test.initFromJson();
		System.out.println(gson.toJson(test));
		
	}

}
