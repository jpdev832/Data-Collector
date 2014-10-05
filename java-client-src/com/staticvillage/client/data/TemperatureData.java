package com.staticvillage.client.data;

import com.staticvillage.client.data.AbstractSensorData;

public class TemperatureData extends AbstractSensorData {
	public double celsius;

	@Override
	public String getName() {
		return "TemperatureData";
	}
	
	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
