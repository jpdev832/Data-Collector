package com.staticvillage.android.data;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.UUID;

import com.staticvillage.android.data.AbstractSensorData;

public class CaptureClient {
	private String host;
	private int port;
	private int bufferSize;
	private String appId;
	private String sessionId;
	private ArrayList<AbstractSensorData> bufferObjs;
	
	private Socket client;
	
	public CaptureClient(String appId){
		if(bufferSize < 1)
			bufferSize = 1;
		
		this.appId = appId;
		this.sessionId = UUID.randomUUID().toString();
		this.host = "localhost";
		this.port = 1235;
		this.bufferSize = 10;
		
		bufferObjs = new ArrayList<AbstractSensorData>(this.bufferSize);
	}
	
	public boolean isConnected(){
		return client == null;
	}
	
	public void connect() throws UnknownHostException, IOException{
		connect(this.host);
	}
	public void connect(String host) throws UnknownHostException, IOException{
		connect(host, this.port);
	}
	public void connect(String host, int port) throws UnknownHostException, IOException{
		client = new Socket(host, port);
	}
	
	public void close() throws IOException{
		if(client != null){
			client.close();
			client = null;
		}
	}
	
	public void report(AbstractSensorData data) throws IOException {
		data.name = data.getName();
		data.app_id = appId;
		data.session_id = sessionId;
		
		bufferObjs.add(data);
		
		if(bufferObjs.size() == bufferSize){
			String output = data.toJson();
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
