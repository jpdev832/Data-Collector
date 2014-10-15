package com.staticvillage.sense;
	
import java.net.URL;

import com.staticvillage.sense.view.SenseController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	private Stage stage;
	private TitledPane rootLayout;
	
	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		
		initLayout();
	}
	
	public void initLayout(){
		try {
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/sense.fxml"));
            rootLayout = (TitledPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
			stage.setScene(scene);
			stage.show();
			
			//SenseController controller = loader.getController();
			//controller.setMainApp(this);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
