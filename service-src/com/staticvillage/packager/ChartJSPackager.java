package com.staticvillage.packager;

import java.net.UnknownHostException;

import com.staticvillage.data.Retriever;

public class ChartJSPackager {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Retriever ret = new Retriever();
		try {
			ret.init();
			String s = ret.retrieveBySessionId("AccelerometerData", "5b47d485-7397-4ea8-960a-60483aadd71f");
			System.out.println(s);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
