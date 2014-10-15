package com.staticvillage.data;

import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class ProximityData extends AbstractSensorData {
	public long millimeters;
	
	@Override
	public BasicDBObject Process(JsonObject obj) {
		millimeters = obj.get("mm").getAsLong();
		
		BasicDBObject dbObj = new BasicDBObject("mm", millimeters);
		return dbObj;
	}

	@Override
	public AbstractSensorData initFromDBObject(DBObject object) {
		// TODO Auto-generated method stub
		return null;
	}
}
