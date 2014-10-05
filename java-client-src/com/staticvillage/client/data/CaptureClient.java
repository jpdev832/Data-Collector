package com.staticvillage.client.data;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import com.google.gson.Gson;
import com.staticvillage.client.data.AbstractSensorData;

public class CaptureClient {
	private String host;
	private int port;
	private int bufferSize;
	private String appId;
	private String sessionId;
	private ArrayList<AbstractSensorData> bufferObjs;
	private SimpleDateFormat format;
	
	private Socket client;
	
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
	
	public void connect() throws UnknownHostException, IOException{
		client = new Socket(host, port);
	}
	
	//override connect
	
	public void close() throws IOException{
		if(client != null)
			client.close();
	}
	
	public void report(AbstractSensorData data) throws IOException {
		data.name = data.getName();
		data.app_id = appId;
		data.session_id = sessionId;
		data.timestamp = format.format(new Date());
		
		bufferObjs.add(data);
		
		if(bufferObjs.size() == bufferSize){
			String output = (new Gson()).toJson(bufferObjs);
			send(output);
			
			bufferObjs.clear();
		}
	}
	
	protected void send(String output) throws IOException{
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		writer.write(output);
		writer.flush();
		writer.close();
	}
}
