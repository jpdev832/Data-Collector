package com.staticvillage.data.test;

import java.io.IOException;
import java.net.UnknownHostException;

import com.staticvillage.client.data.AccelerometerData;
import com.staticvillage.client.data.CaptureClient;

public class CaptureClientTest {

	public static void main(String[] args) {
		CaptureClient client = new CaptureClient("staticvillageappid");
		
		try {
			client.connect();
			
			for(int i=0;i<10;i++){
				AccelerometerData a = new AccelerometerData();
				a.x   		 = 12.345*i;
				a.y			 = 1.2*i;
				a.z			 = 0.03412*i;
				
				client.report(a);
			}
			
			client.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
