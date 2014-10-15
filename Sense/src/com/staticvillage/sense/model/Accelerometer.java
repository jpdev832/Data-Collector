package com.staticvillage.sense.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Accelerometer {
	private DoubleProperty x;
	private DoubleProperty y;
	private DoubleProperty z;
	
	public Accelerometer(double x, double y, double z){
		this.x = new SimpleDoubleProperty(x);
		this.y = new SimpleDoubleProperty(y);
		this.z = new SimpleDoubleProperty(z);
	}

	public double getX() {
		return x.get();
	}

	public void setX(double x) {
		this.x.set(x);
	}
	
	public DoubleProperty xProperty(){
		return x;
	}

	public double getY() {
		return y.get();
	}

	public void setY(double y) {
		this.y.set(y);
	}
	
	public DoubleProperty yProperty(){
		return y;
	}

	public double getZ() {
		return z.get();
	}

	public void setZ(double z) {
		this.z.set(z);
	}
	
	public DoubleProperty zProperty(){
		return z;
	}
}
