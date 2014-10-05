package com.staticvillage.client.data;

import com.staticvillage.client.data.AbstractSensorData;

public class AccelerometerData extends AbstractSensorData {
	public double x;
	public double y;
	public double z;
	
	@Override
	public String getName() {
		return "AccelerometerData";
	}
	
	@Override
	public String toJson() {
		return null;
	}
}
