package com.staticvillage.data;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
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
