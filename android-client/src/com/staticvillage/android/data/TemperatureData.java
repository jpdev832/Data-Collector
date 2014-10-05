package com.staticvillage.android.data;

import org.json.JSONException;
import org.json.JSONObject;

import android.hardware.SensorEvent;

import com.staticvillage.android.data.AbstractSensorData;

public class TemperatureData extends AbstractSensorData {
	public double celsius;

	@Override
	public String getName() {
		return "TemperatureData";
	}
	
	@Override
	public String toJson() {
		JSONObject obj = new JSONObject();
		try {
			obj = getBaseObject(obj);
			obj.put("celcius", celsius);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
		return obj.toString();
	}
	
	@Override
	public void initFromSensorEvent(SensorEvent event) {
		float[] values = event.values;
		celsius = values[0];
		
		timestamp = formatTimestamp(event.timestamp);
	}
}
