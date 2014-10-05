package com.staticvillage.data;

import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;

public class OrientationData extends AbstractSensorData {
	public double x;
	public double y;
	public double z;
	
	@Override
	public BasicDBObject Process(JsonObject obj) {
		x = obj.get("x").getAsDouble();
		y = obj.get("y").getAsDouble();
		z = obj.get("").getAsDouble();
		
		BasicDBObject dbObj = new BasicDBObject("x", x)
										.append("y", y)
										.append("z", z);
		
		return dbObj;
	}
}
