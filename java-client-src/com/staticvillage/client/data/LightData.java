package com.staticvillage.client.data;

import com.staticvillage.client.data.AbstractSensorData;

public class LightData extends AbstractSensorData {
	public long lux;

	@Override
	public String getName() {
		return "LightData";
	}
	
	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
