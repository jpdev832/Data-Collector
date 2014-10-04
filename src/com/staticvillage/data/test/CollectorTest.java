package com.staticvillage.data.test;

import static org.junit.Assert.*;

import java.net.UnknownHostException;
import java.util.ArrayList;

import org.junit.Test;

import com.google.gson.Gson;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.staticvillage.data.AccelerometerData;
import com.staticvillage.data.Collector;

public class CollectorTest {

	@Test
	public void test() {
		Collector collector = new Collector();
		try {
			collector.init();
			
			ArrayList<AccelerometerData> items = new ArrayList<AccelerometerData>();
			AccelerometerData i1 = new AccelerometerData();
			i1.x = 10;
			i1.y = 1.23456;
			i1.z = 32.5632;
			items.add(i1);
			AccelerometerData i2 = new AccelerometerData();
			i2.x = 10;
			i2.y = 1.23456;
			i2.z = 32.5632;
			items.add(i2);
			AccelerometerData i3 = new AccelerometerData();
			i3.x = 10;
			i3.y = 1.23456;
			i3.z = 32.5632;
			items.add(i3);
			AccelerometerData i4 = new AccelerometerData();
			i4.x = 10;
			i4.y = 1.23456;
			i4.z = 32.5632;
			items.add(i4);
			AccelerometerData i5 = new AccelerometerData();
			i5.x = 10;
			i5.y = 1.23456;
			i5.z = 32.5632;
			items.add(i5);
			
			for(AccelerometerData item : items){
				item.name 		= "AccelerometerData";
				item.app_id 	= "testapp1";
				item.session_id = "thisshouldberandomandunique";
				item.timestamp 	= "06:24:23:12 2014-10-13";
			}
			
			Gson gson = new Gson();
			String output = gson.toJson(items);
			
			collector.add(output);
			collector.close();
			
			MongoClient client = new MongoClient("localhost");
			DB db = client.getDB(Collector.DB_NAME);
			
			DBCollection coll = db.getCollection("AccelerometerData");
			assertEquals(5, coll.count());
			
			DBObject obj = coll.findOne();
			double x = (Double) obj.get("x");
			double y = (Double) obj.get("y");
			double z = (Double) obj.get("z");
			
			assertEquals(10, x, 0.00001);
			assertEquals(1.23456, y, 0.00001);
			assertEquals(32.5632, z, 0.00001);
			
			db.dropDatabase();
			client.close();
		} catch (UnknownHostException e) {
			fail("Init failed");
		}
	}

}
