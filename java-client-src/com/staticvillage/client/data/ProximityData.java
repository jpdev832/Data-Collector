package com.staticvillage.client.data;

import com.staticvillage.client.data.AbstractSensorData;

public class ProximityData extends AbstractSensorData {
	public long millimeters;

	@Override
	public String getName() {
		return "ProximityData";
	}
	
	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
