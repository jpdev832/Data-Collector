package com.staticvillage.client.data;

import com.staticvillage.client.data.AbstractSensorData;

public class AirPressureData extends AbstractSensorData {
	public long units;
	
	@Override
	public String getName() {
		return "AirPressureData";
	}
	
	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}
}
