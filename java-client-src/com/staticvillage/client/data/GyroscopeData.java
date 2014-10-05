package com.staticvillage.client.data;

import com.staticvillage.client.data.AbstractSensorData;

public class GyroscopeData extends AbstractSensorData {
	public double x;
	public double y;
	public double z;
	
	@Override
	public String getName() {
		return "GyroscopeData";
	}
	
	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}

}
