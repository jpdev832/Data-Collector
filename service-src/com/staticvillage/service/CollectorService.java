package com.staticvillage.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CollectorService {

	public static void main(String[] args) {
		try {
			System.out.println("Starting Collector Service...");
			ServerSocket server = new ServerSocket(1235);
			
			System.out.println("service started");
			while(true){
				System.out.println("waiting for clients...");
				Socket client = server.accept();
				client.setSoTimeout(3000);
				System.out.println("launching a new connector");
				CollectorRunner runner = new CollectorRunner(client);
				
				new Thread(runner).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
