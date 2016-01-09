package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import model.DatabaseHandler;
import model.Folder;

public class ControllerFolder implements Initializable {

	private DatabaseHandler dbh;
	private String folderName;
	private ControllerMain controllerMain;
	@FXML
	Label labelFolderName;
	@FXML
	TextField newName;
	@FXML
	Button btnSetFolderName;
	@FXML
	Label errorFolder;
	@FXML
	Button btnDeleteFolder;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void initData(Folder folderSelected) {
	    labelFolderName.setText(folderSelected.getName());
	    this.folderName = folderSelected.getName();
	}
	
	public void setFolderName(){
		dbh = new DatabaseHandler();
		String newFolderName = newName.getText();
		System.out.println(newFolderName);
		System.out.println(folderName);
		
		int done = dbh.renameFolder(folderName, newFolderName);
		
		if(done == 0){
			errorFolder.setText("El nombre de carpeta ya existe");
		}else{
			labelFolderName.setText(newFolderName);
			errorFolder.setText("Hecho!");
		}
	}
	
	public void deleteFolder(){
		dbh = new DatabaseHandler();
		dbh.deleteFolder(folderName);
		
		
		Stage stage = (Stage) btnDeleteFolder.getScene().getWindow();
		stage.close();
	}

}
