package main;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NewFeedView extends Application {

	public static void main(String[] args ){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("/view/NewFeed.fxml"));
		Scene scene = new Scene(root,600,400);
		
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Añade un nuevo Feed");
		primaryStage.show();
	}
}
