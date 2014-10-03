package com.staticvillage.data;

import java.util.Date;

import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;

public abstract class AbstractSensorData {
	public static final String PACKAGE = "com.staticvillage.data";
	
	public int appId;
	public Date timestamp;
	
	public abstract BasicDBObject Process(JsonObject obj);
}
