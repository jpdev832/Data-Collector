package com.staticvillage.data;

import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class LightData extends AbstractSensorData {
	public long lux;
	
	@Override
	public BasicDBObject Process(JsonObject obj) {
		lux = obj.get("x").getAsLong();
		
		BasicDBObject dbObj = new BasicDBObject("lux", lux);
		return dbObj;
	}

	@Override
	public AbstractSensorData initFromDBObject(DBObject object) {
		// TODO Auto-generated method stub
		return null;
	}
}
