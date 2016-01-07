package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import model.DatabaseHandler;
import model.Feed;
import model.Folder;
import model.RomeOperations;

public class ControllerMain implements Initializable{
	
	private DatabaseHandler dbh;
	private RomeOperations rome;
	@FXML private TreeView<String> folderTree;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		generateFolderTree();
		
	}
	
	private void generateFolderTree(){
 		//TreeFolder
		dbh = new DatabaseHandler();
 		TreeItem<String> rootNode = new TreeItem<String>("Todos los feeds");
 		ArrayList<Folder> folders = dbh.getFolders();
		rootNode.setExpanded(true);
		
		
		
		for (Folder folder : folders) {
            TreeItem<String> foldLeaf = new TreeItem<String>(folder.getName());
            rootNode.getChildren().add(foldLeaf);
            
            
            for(Feed feed : folder.getFeeds()){
            	TreeItem<String> feedLeaf = new TreeItem<String>(feed.getName());
            	foldLeaf.getChildren().add(feedLeaf);
            }
  
	    }
		
		folderTree.setRoot(rootNode);
		
 	}
	
	
	
	
	

	
}
