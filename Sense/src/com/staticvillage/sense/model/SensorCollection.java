package com.staticvillage.sense.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SensorCollection {
	private StringProperty name;
	
	public SensorCollection(String name) {
		this.name = new SimpleStringProperty(name);
	}
	
	public String getName(){
		return name.get();
	}
	
	public void setName(String name){
		this.name.set(name);
	}
	
	public StringProperty nameProperty(){
		return name;
	}
}
