package com.staticvillage.android.data;

import org.json.JSONException;
import org.json.JSONObject;

import android.hardware.SensorEvent;

import com.staticvillage.android.data.AbstractSensorData;

public class GravityData extends AbstractSensorData {
	public double x;
	public double y;
	public double z;
	
	@Override
	public String getName() {
		return "GravityData";
	}
	
	@Override
	public String toJson() {
		JSONObject obj = new JSONObject();
		try {
			obj = getBaseObject(obj);
			obj.put("x", x);
			obj.put("y", y);
			obj.put("z", z);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
		return obj.toString();
	}

	@Override
	public void initFromSensorEvent(SensorEvent event) {
		float[] values = event.values;
		x = values[0];
		y = values[1];
		z = values[2];
		
		timestamp = formatTimestamp(event.timestamp);
	}
}
