package com.staticvillage.android.data;

import org.json.JSONException;
import org.json.JSONObject;

import android.hardware.SensorEvent;

import com.staticvillage.android.data.AbstractSensorData;

public class ProximityData extends AbstractSensorData {
	public long millimeters;

	@Override
	public String getName() {
		return "ProximityData";
	}
	
	@Override
	public String toJson() {
		JSONObject obj = new JSONObject();
		try {
			obj = getBaseObject(obj);
			obj.put("millimeters", millimeters);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
		return obj.toString();
	}
	
	@Override
	public void initFromSensorEvent(SensorEvent event) {
		float[] values = event.values;
		millimeters = (long)values[0];
		
		timestamp = formatTimestamp(event.timestamp);
	}
}
