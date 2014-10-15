package com.staticvillage.data;

import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MagneticFieldData extends AbstractSensorData {
	public double x;
	public double y;
	public double z;
	
	@Override
	public BasicDBObject Process(JsonObject obj) {
		app_id 		= obj.get(AbstractSensorData.APP_ID).getAsString();
		session_id 	= obj.get(AbstractSensorData.SESSION_ID).getAsString();
		timestamp 	= obj.get(AbstractSensorData.TIMESTAMP).getAsString();
		
		x = obj.get("x").getAsDouble();
		y = obj.get("y").getAsDouble();
		z = obj.get("z").getAsDouble();
		
		BasicDBObject dbObj = new BasicDBObject("x", x)
										.append("y", y)
										.append("z", z);
		
		return dbObj;
	}

	@Override
	public AbstractSensorData initFromDBObject(DBObject object) {
		// TODO Auto-generated method stub
		return null;
	}
}
