package com.staticvillage.sense.view;

import java.lang.reflect.Field;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.mongodb.BasicDBObject;
import com.staticvillage.data.AbstractSensorData;
import com.staticvillage.data.AccelerometerData;
import com.staticvillage.data.Retriever;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class SenseController {
	private String[] collections = new String[]{
		"AccelerometerData",
		"AirPressureData",
		"GravityData",
		"GyroscopeData",
		"LightData",
		"MagneticFieldData",
		"OrientationData",
		"ProximityData",
		"TemperatureData"
	};
	
	private ObservableList<String> obsCollection;
	private ObservableList<String> obsAppId;
	private ObservableList<String> obsSessionId;
	private ObservableList<String> obsTimestamp;
	
	private ObservableList<XYChart.Series<String, Double>> obsSeriesData;
	
	private Class<?> cls;
	
    @FXML
    private Button btnRetrieve;

    @FXML
    private ChoiceBox<String> lstSessionId;

    @FXML
    private LineChart<String, Double> lineChart;

    @FXML
    private ChoiceBox<String> lstAppId;

    @FXML
    private ChoiceBox<String> lstTimestamp;

    @FXML
    private Button btnClear;

    @FXML
    private ChoiceBox<String> lstCollections;
    
    @FXML
    private void initialize() {
    	obsCollection = FXCollections.observableArrayList();
    	obsAppId = FXCollections.observableArrayList();
    	obsSessionId = FXCollections.observableArrayList();
    	obsTimestamp = FXCollections.observableArrayList();
    	obsSeriesData = FXCollections.observableArrayList();

        //try {
        	/*Retriever retriever = new Retriever();
			retriever.init();
			
			String[] colls = retriever.getCollections();
			retriever.close();*/
	        
	        for(String c :collections){
	        	obsCollection.add(c);
	        }
	        
	        lstCollections.setItems(obsCollection);
	        lstAppId.setItems(obsAppId);
	        lstSessionId.setItems(obsSessionId);
	        lstTimestamp.setItems(obsTimestamp);
	        
	        lineChart.setData(obsSeriesData);
	        
	        lstCollections.getSelectionModel().selectFirst();
		/*} catch (UnknownHostException e) {
			e.printStackTrace();
		}*/
    }

    @FXML
    void onAppIdSelected() {
    	System.out.println("AppId Clicked");
    }

    @FXML
    void onSessionIdSelected() {

    }

    @FXML
    void onTimestampSelected() {

    }

    @FXML
    void onRetieveClicked() {
    	obsSeriesData.clear();
    	
    	String col = lstCollections.getSelectionModel().getSelectedItem();
    	String appId = lstAppId.getSelectionModel().getSelectedItem();
    	String sessionId = lstSessionId.getSelectionModel().getSelectedItem();
    	
    	BasicDBObject dbObj = new BasicDBObject(Retriever.KEY_APPID, appId).
    			append(Retriever.KEY_SESSION_ID, sessionId);
    	
    	try {
        	Retriever retriever = new Retriever();
			retriever.init();
			
			List<AbstractSensorData> ret = retriever.retrieve(col, dbObj, (AbstractSensorData)cls.newInstance());
			retriever.close();
			
			Field[] fields = cls.getDeclaredFields();
			HashMap<String, Series<String, Double>> map = new HashMap<String, XYChart.Series<String,Double>>();
			
			for(Field field : fields){
				if(AbstractSensorData.isBaseProperty(field.getName()))
					continue;
				
				Series<String, Double> a = new Series<String, Double>();
				a.setName(field.getName());
				map.put(field.getName(), a);
			}
	        
	        for(AbstractSensorData a : ret){
	        	for(Entry<String, Series<String, Double>> entry : map.entrySet()){
	        		Series<String, Double> v = entry.getValue();
	        		Field f =  cls.getDeclaredField(entry.getKey());
	        		
	        		v.getData().add(new XYChart.Data<String, Double>(a.timestamp, f.getDouble(cls.cast(a))));
	        	}
	        }
	        
	        obsSeriesData.addAll(map.values());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void onClearClicked() {
    	obsSeriesData.clear();
    }

    @FXML
    void onCollectionClicked() {
    	obsAppId.clear();
    	obsSessionId.clear();
    	obsTimestamp.clear();
    	
    	String item = lstCollections.getSelectionModel().getSelectedItem();
    	if(item == null)
    		return;
    	
    	System.out.println("Collection selected: "+item);
    	try {
        	Retriever retriever = new Retriever();
			retriever.init();
			
			String[] appIds = retriever.getAppIds(item);
			String[] sessIds = retriever.getSessionIds(item);
			//String[] Timestamps = retriever.getTimestamps(item);
			retriever.close();
	        
	        for(String s : appIds){
	        	obsAppId.add(s);
	        }
	        
	        for(String s : sessIds){
	        	obsSessionId.add(s);
	        }
	        
	        //for(String s : Timestamps){
	        	//obsTimestamp.add(s);
	        //}
	        
	        cls = Class.forName(AbstractSensorData.PACKAGE+"."+item);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }

}