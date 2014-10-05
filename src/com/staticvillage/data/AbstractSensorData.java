package com.staticvillage.data;

import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;

public abstract class AbstractSensorData {
	public static final String PACKAGE = "com.staticvillage.data";
	
	public String name;
	public String app_id;
	public String session_id;
	public String timestamp;
	
	public abstract BasicDBObject Process(JsonObject obj);
}
