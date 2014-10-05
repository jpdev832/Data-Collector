package com.staticvillage.data;

import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;

public class TemperatureData extends AbstractSensorData {
	public double celsius;
	
	@Override
	public BasicDBObject Process(JsonObject obj) {
		celsius = obj.get("celsius").getAsLong();
		
		BasicDBObject dbObj = new BasicDBObject("celsius", celsius);
		return dbObj;
	}
}
