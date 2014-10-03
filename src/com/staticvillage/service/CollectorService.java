package com.staticvillage.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class CollectorService {

	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(1235);
			
			Socket client = server.accept();
			while(client.isConnected()){
				BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
				
				StringBuilder builder = new StringBuilder();
				String in = null;
				while((in = reader.readLine()) != null){
					builder.append(in);
				}
				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
