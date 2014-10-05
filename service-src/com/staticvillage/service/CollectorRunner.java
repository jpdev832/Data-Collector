package com.staticvillage.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import com.staticvillage.data.Collector;

public class CollectorRunner implements Runnable {
	private Socket client;
	private Collector collector;
	
	public CollectorRunner(Socket client){
		this.client = client;
		
		collector = new Collector();
	}
	
	@Override
	public void run() {
		BufferedReader reader;
		
		try {
			collector.init();
			reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		while(!client.isClosed()){
			try {
				if(reader.ready()){
					System.out.println("connected");
					StringBuilder builder = new StringBuilder();
					String in = null;
					
					while((in = reader.readLine()) != null){
						builder.append(in);
					}
					
					String json = builder.toString();
					System.out.println("json captured: "+json);
					collector.add(json);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		collector.close();
		System.out.println("Client disconnected");
	}

}
