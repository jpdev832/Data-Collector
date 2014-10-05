package com.staticvillage.client.data;

public abstract class AbstractSensorData {
	public static final String PACKAGE = "com.staticvillage.data";
	
	public String name;
	public String app_id;
	public String session_id;
	public String timestamp;
	
	public abstract String getName();
	public abstract String toJson();
}
