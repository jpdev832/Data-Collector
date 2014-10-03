package com.staticvillage.data;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class Collector {
	public static final String KEY_NAME 		= "name";
	public static final String KEY_APPID 		= "app_id";
	public static final String KEY_SESSION_ID 	= "session_id";
	public static final String KEY_TIMESTAMP 	= "timestamp";
	public static final String DB_NAME 			= "sensor_data";
	
	private MongoClient client;
	private DB db;
	private HashMap<String, DBCollection> colls;
	
	public Collector(){}
	
	public void init() throws UnknownHostException{
		client = new MongoClient("localhost");
		db = client.getDB(DB_NAME);
		
		colls = new HashMap<String, DBCollection>();
	}
	
	public void close() {
		if(client != null)
			client.close();
	}
	
	public void add(String data){
		JsonObject root 	= new JsonParser().parse(data).getAsJsonObject();
		JsonArray dataArray = root.getAsJsonArray("data");
		
		for(int i=0; i<dataArray.size(); i++) {
			JsonObject obj 		= dataArray.get(i).getAsJsonObject();
			String className 	= obj.get("name").getAsString();
			String appId 		= obj.get("app_id").getAsString();
			String sessionId 	= obj.get("session_id").getAsString();
			String timestamp 	= obj.get("timestamp").getAsString();
			
			DBCollection coll = null;
			if(!db.collectionExists(className)) {
				BasicDBObject settings = new BasicDBObject("capped", true).append("size", 1048576);
				coll = db.createCollection(className, settings);
			}
			
			if(!colls.containsKey(className))
				colls.put(className, coll);
			
			coll = colls.get(className);
			
			try {
				Class<?> clazz = Class.forName(AbstractSensorData.PACKAGE+"."+className);
				AbstractSensorData sensorData = (AbstractSensorData)clazz.newInstance();
				
				BasicDBObject dbObj = sensorData.Process(obj);
				dbObj.append("app_id", appId);
				dbObj.append("session_id", sessionId);
				dbObj.append("timestamp", timestamp);
				
				coll.insert(dbObj);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			
		}
	}
}
