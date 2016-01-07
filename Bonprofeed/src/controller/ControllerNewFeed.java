package controller;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.DatabaseHandler;
import model.RomeOperations;

public class ControllerNewFeed implements Initializable{
	//Declare variables
	private DatabaseHandler dbh;
	private RomeOperations rome;
	
	@FXML
	private TextField textUrl;
	
	@FXML
	private Label errorFeedLabel;
	
	@FXML
	private Button btnAceptar;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void addFeed(ActionEvent event){
		dbh = new DatabaseHandler();
		rome = new RomeOperations();
		
		String url = textUrl.getText();
		boolean isFeed = rome.isFeed(url);
		
		if(isFeed){
			String feedName = rome.getFeedName(url);
			dbh.insertFeed(url, feedName);
			errorFeedLabel.setText("Correcto");
			
		}else{
			errorFeedLabel.setText("Feed incorrecto");
		}
	
	}
	

}
