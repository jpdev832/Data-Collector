package com.staticvillage.android.data;

import org.json.JSONException;
import org.json.JSONObject;

import android.hardware.SensorEvent;

import com.staticvillage.android.data.AbstractSensorData;

public class LightData extends AbstractSensorData {
	public long lux;

	@Override
	public String getName() {
		return "LightData";
	}
	
	@Override
	public String toJson() {
		JSONObject obj = new JSONObject();
		try {
			obj = getBaseObject(obj);
			obj.put("lux", lux);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
		return obj.toString();
	}
	
	@Override
	public void initFromSensorEvent(SensorEvent event) {
		float[] values = event.values;
		lux = (long)values[0];
		
		timestamp = formatTimestamp(event.timestamp);
	}
}
