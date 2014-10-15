package com.staticvillage.data;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class Retriever {
	public static final String KEY_NAME 		= "name";
	public static final String KEY_APPID 		= "app_id";
	public static final String KEY_SESSION_ID 	= "session_id";
	public static final String KEY_TIMESTAMP 	= "timestamp";
	public static final String DB_NAME 			= "sensor_data";
	
	private MongoClient client;
	private DB db;
	
	public Retriever(){}
	
	/**
	 * Initialize collector connect to db
	 * 
	 * @throws UnknownHostException
	 */
	public void init() throws UnknownHostException{
		client = new MongoClient("localhost");
		db = client.getDB(DB_NAME);
	}
	
	/**
	 * Close connection to the database and stop collecting data
	 */
	public void close() {
		if(client != null)
			client.close();
	}
	
	public String[] getCollections(){
		Set<String> names = db.getCollectionNames();
		String[] sName = new String[names.size() - 1];
		
		int index = 0;
		for(String name : names){
			if(name.equals("system.indexes"))
				continue;
			
			sName[index] = name;
			index++;
		}
		
		return sName;
	}
	
	public String[] getAppIds(String collection){
		DBCollection coll = db.getCollection(collection);
		List<DBObject> items = coll.distinct(AbstractSensorData.APP_ID);
		
		String[] sItems = new String[items.size()];
		
		int index = 0;
		for(Object s : items){
			sItems[index] = (String)s;
			index++;
		}
		
		return sItems;
	}
	
	public String[] getSessionIds(String collection){
		DBCollection coll = db.getCollection(collection);
		List<BasicDBObject> items = coll.distinct(AbstractSensorData.SESSION_ID);
		
		String[] sItems = new String[items.size()];
		
		int index = 0;
		for(Object s : items){
			sItems[index] = (String)s;
			index++;
		}
		
		return sItems;
	}
	
	public String[] getTimestamps(String collection){
		DBCollection coll = db.getCollection(collection);
		List<BasicDBObject> items = coll.distinct(AbstractSensorData.TIMESTAMP);
		
		String[] sItems = new String[items.size()];
		
		int index = 0;
		for(Object s : items){
			sItems[index] = (String)s;
			index++;
		}
		
		return sItems;
	}
	
	public List<AbstractSensorData> retrieve(String collection, DBObject object, AbstractSensorData data){
		DBCollection col = db.getCollection(collection);
		DBCursor cursor = col.find(object);
		
		Class<?> clazz = data.getClass();
		
		ArrayList<AbstractSensorData> c = new ArrayList<AbstractSensorData>(cursor.count());
		try {
		    while (cursor.hasNext()) {
		    	AbstractSensorData sensorData = (AbstractSensorData)clazz.newInstance();
		    	sensorData.Process((JsonObject)(new JsonParser()).parse(cursor.next().toString()));
		    	
		        c.add(sensorData);
		    }
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
		    cursor.close();
		}
		
		return c;
	}
	
	public String retrieveAll(String collection){
		DBCollection col = db.getCollection(collection);
		DBCursor cursor = col.find();
		
		Collection<String> c = new ArrayList<String>();
		try {
		    while (cursor.hasNext()) {
		        c.add(cursor.next().toString());
		    }
		} finally {
		    cursor.close();
		}
		
		Gson gson = new Gson();
		String ret = gson.toJson(c);
		
		return ret;
	}
	
	public String retieveByName(String collection, String name){
		DBCollection col = db.getCollection(collection);
		DBCursor cursor = col.find(new BasicDBObject(KEY_NAME, name));
		
		Collection<String> c = new ArrayList<String>();
		try {
		    while (cursor.hasNext()) {
		        c.add(cursor.next().toString());
		    }
		} finally {
		    cursor.close();
		}
		
		Gson gson = new Gson();
		String ret = gson.toJson(c);
		
		return ret;
	}
	
	public String retrieveByAppId(String collection, String appId){
		DBCollection col = db.getCollection(collection);
		DBCursor cursor = col.find(new BasicDBObject(KEY_APPID, appId));
		
		Collection<String> c = new ArrayList<String>();
		try {
		    while (cursor.hasNext()) {
		        c.add(cursor.next().toString());
		    }
		} finally {
		    cursor.close();
		}
		
		Gson gson = new Gson();
		String ret = gson.toJson(c);
		
		return ret;
	}
	
	public String retrieveBySessionId(String collection, String sessionId){
		DBCollection col = db.getCollection(collection);
		DBCursor cursor = col.find(new BasicDBObject(KEY_SESSION_ID, sessionId));
		
		Collection<String> c = new ArrayList<String>();
		try {
		    while (cursor.hasNext()) {
		        c.add(cursor.next().toString());
		    }
		} finally {
		    cursor.close();
		}
		
		Gson gson = new Gson();
		String ret = gson.toJson(c);
		
		return ret;
	}
}
