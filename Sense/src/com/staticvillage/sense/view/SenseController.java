package com.staticvillage.sense.view;

import java.net.UnknownHostException;
import java.util.List;

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
	private ObservableList<String> obsCollection;
	private ObservableList<String> obsAppId;
	private ObservableList<String> obsSessionId;
	private ObservableList<String> obsTimestamp;
	
	private ObservableList<XYChart.Series<String, Double>> obsAccels;
	
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
    	obsAccels = FXCollections.observableArrayList();

        try {
        	Retriever retriever = new Retriever();
			retriever.init();
			
			String[] colls = retriever.getCollections();
			retriever.close();
	        
	        for(String c :colls){
	        	obsCollection.add(c);
	        }
	        
	        lstCollections.setItems(obsCollection);
	        lstAppId.setItems(obsAppId);
	        lstSessionId.setItems(obsSessionId);
	        lstTimestamp.setItems(obsTimestamp);
	        
	        lineChart.setData(obsAccels);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
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
    	obsAccels.clear();
    	
    	String col = lstCollections.getSelectionModel().getSelectedItem();
    	String appId = lstAppId.getSelectionModel().getSelectedItem();
    	String sessionId = lstSessionId.getSelectionModel().getSelectedItem();
    	
    	BasicDBObject dbObj = new BasicDBObject(Retriever.KEY_APPID, appId).
    			append(Retriever.KEY_SESSION_ID, sessionId);
    	
    	try {
        	Retriever retriever = new Retriever();
			retriever.init();
			
			List<AbstractSensorData> ret = retriever.retrieve(col, dbObj, new AccelerometerData());
			retriever.close();
			
			Series<String, Double> x = new Series<String, Double>();
		    Series<String, Double> y = new Series<String, Double>();
		    Series<String, Double> z = new Series<String, Double>();
		    x.setName("X");
		    y.setName("Y");
		    z.setName("Z");
	        
	        for(AbstractSensorData a : ret){
	        	AccelerometerData ac = (AccelerometerData)a;
	        	x.getData().add(new XYChart.Data<String, Double>(ac.timestamp, ac.x));
	        	y.getData().add(new XYChart.Data<String, Double>(ac.timestamp, ac.y));
	        	z.getData().add(new XYChart.Data<String, Double>(ac.timestamp, ac.z));
	        }
	        
	        obsAccels.addAll(x, y, z);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void onClearClicked() {
    	obsAccels.clear();
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
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
    }

}