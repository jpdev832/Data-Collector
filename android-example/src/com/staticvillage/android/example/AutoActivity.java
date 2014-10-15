package com.staticvillage.android.example;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.staticvillage.android.data.AccelerometerData;
import com.staticvillage.android.log.SensorLogger;
import com.staticvillage.android.log.SensorLogger.SensorType;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class AutoActivity extends Activity implements OnItemClickListener, OnClickListener {
	ListView mainView;
	Button btnStart;
	Button btnStop;
	SensorLogger logger;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto);
		
		/*SensorManager sm = (SensorManager)getSystemService(SENSOR_SERVICE);
		List<Sensor> sList = sm.getSensorList(Sensor.TYPE_ALL);
		
		ArrayList<String> items = new ArrayList<String>(sList.size());
		for(Sensor s : sList){
			items.add(s.getName());
		}*/
		
		String[] items = new String[]{
			"Accelerometer",
			"Air Pressure",
			"Gravity",
			"Gyroscope",
			"Light",
			"Magnetic Field",
			"Proximity"
		};
		
		mainView = (ListView)findViewById(R.id.listViewMain);
		btnStart = (Button)findViewById(R.id.btnStart);
		btnStop = (Button)findViewById(R.id.btnStop);
		
		ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		mainView.setAdapter(a);
		
		mainView.setOnItemClickListener(this);
		btnStart.setOnClickListener(this);
		btnStop.setOnClickListener(this);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
		switch(index){
			case 0:
				Toast.makeText(this, "Acceleromter Clicked", Toast.LENGTH_SHORT).show();
				logger.addSensor(SensorType.ACCELEROMETER);
				break;
			case 1:
				Toast.makeText(this, "Air Pressure Clicked", Toast.LENGTH_SHORT).show();
				logger.addSensor(SensorType.AIR_PRESSURE);
				break;
			case 2:
				Toast.makeText(this, "Gravity Clicked", Toast.LENGTH_SHORT).show();
				logger.addSensor(SensorType.GRAVITY);
				break;
			case 3:
				Toast.makeText(this, "Gyroscope", Toast.LENGTH_SHORT).show();
				logger.addSensor(SensorType.GYROSCOPE);
				break;
			case 4:
				Toast.makeText(this, "Light", Toast.LENGTH_SHORT).show();
				logger.addSensor(SensorType.LIGHT);
				break;
			case 5:
				Toast.makeText(this, "Magnetic Field", Toast.LENGTH_SHORT).show();
				logger.addSensor(SensorType.MAGNETIC_FIELD);
				break;
			case 6:
				Toast.makeText(this, "Proximity", Toast.LENGTH_SHORT).show();
				logger.addSensor(SensorType.PROXIMITY);
				break;
			default:
				break;
		}
	}

	@Override
	public void onClick(View btn) {
		if(btn == btnStart){
			if(logger == null)
				logger = new SensorLogger(this, "androidsensorlogger");
			
			try {
				logger.start("192.168.2.122");
				
				Toast.makeText(this, "Connected!", Toast.LENGTH_SHORT).show();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(btn == btnStop){
			if(logger != null)
				logger.stop();
			
			Toast.makeText(this, "Disconnected!", Toast.LENGTH_SHORT).show();
		}
	}

}
