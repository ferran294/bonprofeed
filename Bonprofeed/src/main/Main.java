package main;

import java.util.LinkedList;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.DatabaseHandler;
import model.RomeOperations;


public class Main extends Application {
	
	
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
		Scene scene = new Scene(root,1200,800);
		
		//Load Artciles from feed and put into database
		RomeOperations rome = new RomeOperations();
		rome.updateArticles();
		
		
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.setTitle("BonProfeed");
		primaryStage.show();
	}
	
	
 	

}
