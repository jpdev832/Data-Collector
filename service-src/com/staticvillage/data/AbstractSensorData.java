package com.staticvillage.data;

import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public abstract class AbstractSensorData {
	public static final String PACKAGE 		= "com.staticvillage.data";
	public static final String NAME 		= "name";
	public static final String APP_ID 		= "app_id";
	public static final String SESSION_ID 	= "session_id";
	public static final String TIMESTAMP 	= "timestamp";
	
	public String name;
	public String app_id;
	public String session_id;
	public String timestamp;
	
	public abstract BasicDBObject Process(JsonObject obj);
	public abstract AbstractSensorData initFromDBObject(DBObject object);
}
