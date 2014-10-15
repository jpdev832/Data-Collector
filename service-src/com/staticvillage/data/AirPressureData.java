package com.staticvillage.data;

import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class AirPressureData extends AbstractSensorData {
	public long units;
	
	@Override
	public BasicDBObject Process(JsonObject obj) {
		units = obj.get("x").getAsLong();
		
		BasicDBObject dbObj = new BasicDBObject("units", units);
		return dbObj;
	}

	@Override
	public AbstractSensorData initFromDBObject(DBObject object) {
		// TODO Auto-generated method stub
		return null;
	}
}
