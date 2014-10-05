package com.staticvillage.android.log;

import java.io.IOException;
import java.net.UnknownHostException;

import com.staticvillage.android.data.AbstractSensorData;
import com.staticvillage.android.data.CaptureClient;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorLogger implements SensorEventListener {
	public enum SensorType{
		ACCELEROMETER,
		AIR_PRESSURE,
		GRAVITY,
		GYROSCOPE,
		LIGHT,
		MAGNETIC_FIELD,
		ORIENTATION,
		PROXIMITY,
		TEMPERATURE
	}
	
	private SensorManager sensorManager;
	private CaptureClient client;
	private int registeredSensors;
	
	public SensorLogger(Context context, String appId){
		this.sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
		this.registeredSensors = 0;
		
		client = new CaptureClient(appId);
	}
	
	public void addSensor(SensorType sensorType){
		Sensor sensor = sensorManager.getDefaultSensor(getSensorType(sensorType));
		sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
		
		registeredSensors++;
	}
	
	public void removeSensor(SensorType sensorType){
		Sensor sensor = sensorManager.getDefaultSensor(getSensorType(sensorType));
		sensorManager.unregisterListener(this, sensor);
		
		registeredSensors--;
	}
	
	public void removeAllSensors(){
		sensorManager.unregisterListener(this);
		
		registeredSensors = 0;
	}
	
	public void start() throws UnknownHostException, IOException{
		if(!client.isConnected())
			client.connect();
	}
	public void start(String host) throws UnknownHostException, IOException{
		if(!client.isConnected())
			client.connect(host);
	}
	public void start(String host, int port) throws UnknownHostException, IOException{
		if(!client.isConnected())
			client.connect(host, port);
	}
	
	public void stop(SensorType sensorType){		
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		removeAllSensors();
	}
	
	protected int getSensorType(SensorType sensorType){
		int type;
		
		switch(sensorType){
		case ACCELEROMETER:
			type = Sensor.TYPE_ACCELEROMETER;
			break;
		case AIR_PRESSURE:
			type = Sensor.TYPE_PRESSURE;
			break;
		case GRAVITY:
			type = Sensor.TYPE_GRAVITY;
			break;
		case GYROSCOPE:
			type = Sensor.TYPE_GYROSCOPE;
			break;
		case LIGHT:
			type = Sensor.TYPE_LIGHT;
			break;
		case MAGNETIC_FIELD:
			type = Sensor.TYPE_MAGNETIC_FIELD;
			break;
		//case ORIENTATION:
			//type = Sensor.TYPE_ORIENTATION;
			//break;
		case PROXIMITY:
			type = Sensor.TYPE_PROXIMITY;
			break;
		case TEMPERATURE:
			type = Sensor.TYPE_AMBIENT_TEMPERATURE;
			break;
		default:
			type = -2;
			break;
		}
		
		return type;
	}
	
	protected String getSensorClass(int sensorType){
		String className = null;
		
		switch(sensorType){
		case Sensor.TYPE_ACCELEROMETER:
			className = AbstractSensorData.PACKAGE+".AcceleromterData";
			break;
		case Sensor.TYPE_PRESSURE:
			className = AbstractSensorData.PACKAGE+".AirPressureData";
			break;
		case Sensor.TYPE_GRAVITY:
			className = AbstractSensorData.PACKAGE+".GravityData";
			break;
		case Sensor.TYPE_GYROSCOPE:
			className = AbstractSensorData.PACKAGE+".GyroscopeData";
			break;
		case Sensor.TYPE_LIGHT:
			className = AbstractSensorData.PACKAGE+".LightData";
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			className = AbstractSensorData.PACKAGE+".MagneticFieldData";
			break;
		//case Sensor.TYPE_ORIENTATION:
			//break;
		case Sensor.TYPE_PROXIMITY:
			className = AbstractSensorData.PACKAGE+".ProximityData";
			break;
		case Sensor.TYPE_AMBIENT_TEMPERATURE:
			className = AbstractSensorData.PACKAGE+".TemperatureData";
			break;
		default:
			className = null;
			break;
		}
		
		return className;
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		String className = getSensorClass(event.sensor.getType());
		
		try {
			Class<?> clazz = Class.forName(className);
			AbstractSensorData sensorData = (AbstractSensorData)clazz.newInstance();
			sensorData.initFromSensorEvent(event);
			
			client.report(sensorData);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
