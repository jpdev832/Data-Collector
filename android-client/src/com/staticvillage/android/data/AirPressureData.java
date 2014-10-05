package com.staticvillage.android.data;

import org.json.JSONException;
import org.json.JSONObject;

import android.hardware.SensorEvent;

import com.staticvillage.android.data.AbstractSensorData;

public class AirPressureData extends AbstractSensorData {
	public long units;
	
	@Override
	public String getName() {
		return "AirPressureData";
	}
	
	@Override
	public String toJson() {
		JSONObject obj = new JSONObject();
		try {
			obj = getBaseObject(obj);
			obj.put("units", units);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
		return obj.toString();
	}
	
	@Override
	public void initFromSensorEvent(SensorEvent event) {
		float[] values = event.values;
		units = (long)values[0];
		
		timestamp = formatTimestamp(event.timestamp);
	}
}
