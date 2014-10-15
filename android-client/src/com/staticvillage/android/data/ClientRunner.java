package com.staticvillage.android.data;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class ClientRunner extends Thread {
	public Handler mHandler;
	
	private String host;
	private int port;
	private Socket client;
	private BufferedWriter writer;
	
	public ClientRunner(String host, int port){
		this.host = host;
		this.port = port;
	}
	
	@Override
	public void run() {
		Looper.prepare();
		
		try {
			client = new Socket(host, port);
			writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		mHandler = new Handler() {
            public void handleMessage(Message msg) {
                String json = (String) msg.obj;
                
                try {
					send(json);
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
            }
        };
		
		Looper.loop();
		
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void send(String output) throws IOException{
		writer.write(output);
		writer.newLine();
		writer.flush();
	}
}
