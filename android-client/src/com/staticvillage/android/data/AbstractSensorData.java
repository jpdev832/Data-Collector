package com.staticvillage.android.data;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.hardware.SensorEvent;

public abstract class AbstractSensorData {
	public static final String PACKAGE = "com.staticvillage.data";
	
	public String name;
	public String app_id;
	public String session_id;
	public String timestamp;
	
	public JSONObject getBaseObject(JSONObject obj){
		try {
			obj.put("name", this.name);
			obj.put("app_id", this.app_id);
			obj.put("session_id", this.session_id);
			obj.put("timestamp", this.timestamp);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	public String formatTimestamp(long timestamp){
		return formatTimestamp(new Date(timestamp));
	}
	public String formatTimestamp(Date timestamp){
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss:SS");
		return format.format(timestamp);
	}
	
	public String getClassName(){
		return PACKAGE+"."+getName();
	}
	
	public abstract void initFromSensorEvent(SensorEvent event);
	public abstract String getName();
	public abstract String toJson();
}
