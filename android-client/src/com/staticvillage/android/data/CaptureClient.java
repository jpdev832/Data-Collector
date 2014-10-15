package com.staticvillage.android.data;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Message;

import com.staticvillage.android.data.AbstractSensorData;

public class CaptureClient {
	private String host;
	private int port;
	private int bufferSize;
	private String appId;
	private String sessionId;
	private ArrayList<AbstractSensorData> bufferObjs;
	private SimpleDateFormat format;
	private ClientRunner runner;
	
	public CaptureClient(String appId){
		if(bufferSize < 1)
			bufferSize = 1;
		
		this.appId = appId;
		this.sessionId = UUID.randomUUID().toString();
		this.host = "localhost";
		this.port = 1235;
		this.bufferSize = 10;
		this.format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss:SS");
		
		bufferObjs = new ArrayList<AbstractSensorData>(this.bufferSize);
	}
	
	public boolean isConnected(){
		return runner != null;
	}
	
	public void connect() throws UnknownHostException, IOException{
		connect(this.host);
	}
	public void connect(String host) throws UnknownHostException, IOException{
		connect(host, this.port);
	}
	public void connect(String host, int port) throws UnknownHostException, IOException{
		if(runner == null){
			this.sessionId = UUID.randomUUID().toString();
			runner = new ClientRunner(host, port);
			runner.start();
		}
	}
	
	public void close() throws IOException{
		if(runner != null){
			runner.mHandler.getLooper().quit();
			runner = null;
		}
	}
	
	public void report(AbstractSensorData data) throws IOException, JSONException {
		//add fix me later to notify that its not connected
		if(runner == null)
			return;
		
		data.name = data.getName();
		data.app_id = appId;
		data.session_id = sessionId;
		
		if(data.timestamp == null)
			data.timestamp = format.format(new Date());
		
		bufferObjs.add(data);
		
		if(bufferObjs.size() == bufferSize){
			JSONArray jsonArray = new JSONArray();
			
			for(AbstractSensorData s : bufferObjs)
				jsonArray.put(new JSONObject(s.toJson()));
			
			String output = jsonArray.toString();
			Message msg = new Message();
			msg.obj = output;
			
			runner.mHandler.sendMessage(msg);
			
			bufferObjs.clear();
		}
	}
}
