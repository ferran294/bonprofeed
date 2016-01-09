package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.DatabaseHandler;
import model.Feed;
import model.Folder;
import model.RomeOperations;

public class ControllerMain implements Initializable{
	
	private DatabaseHandler dbh = new DatabaseHandler();
	private RomeOperations rome;
	private static String actualView;
	private static Folder actualFolder;
	ArrayList<Folder> folders = dbh.getFolders();
	@FXML TreeView<String> folderTree;
	@FXML private AnchorPane panelArticles;
	//Folder view
	@FXML
	private Label labelFolderName;
	@FXML
	private TextField newName;
	@FXML
	private Button btnSetFolderName;
	@FXML
	private Label errorFolder;
	@FXML
	private Button btnDeleteFolder;
	
	public ControllerMain(){
		super();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		if(actualView == "main"){
			generateFolderTree();
		}else{
			labelFolderName.setText(actualFolder.getName());
		}
			
		
		
	}
	
	public void generateFolderTree(){
 		//TreeFolder
		dbh = new DatabaseHandler();
 		TreeItem<String> rootNode = new TreeItem<String>("Todos los feeds");
 		
		rootNode.setExpanded(true);
		
		
		for (Folder folder : folders) {
            TreeItem<String> foldLeaf = new TreeItem<String>(folder.getName());
            rootNode.getChildren().add(foldLeaf);
           
            LinkedList<Feed> feeds = folder.getFeeds();
            
            if(feeds.isEmpty()){
            	TreeItem<String> feedLeaf = new TreeItem<String>("No hay Feeds");
            	foldLeaf.getChildren().add(feedLeaf);
            }else{
            	for(Feed feed : feeds){
                	TreeItem<String> feedLeaf = new TreeItem<String>(feed.getName());
                	foldLeaf.getChildren().add(feedLeaf);
                }
            }
            
  
	    }
		ArrayList<Feed> listaFeeds = dbh.getUnclassifiedFeeds();
		
		for(Feed feed : listaFeeds){
			TreeItem<String> feedLeaf = new TreeItem<String>(feed.getName());
			rootNode.getChildren().add(feedLeaf);
		}
		
		
		folderTree.setRoot(rootNode);
		
		folderTree.setEditable(true);
		folderTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	    folderTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
	            
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            
            TreeItem treeItem = (TreeItem)newValue;
            
            if(!treeItem.isLeaf() && treeItem.getParent() != null){
            	
            	Folder folderSelected = getFolder(folders, treeItem.getValue().toString());
    			loadFolderWindow(folderSelected, treeItem);
            }
            
    		
    		               	                	                
            
        }
            
	    });
		
 	}
	
	public void newFeed(ActionEvent event) throws IOException{
		Parent parent = FXMLLoader.load(getClass().getResource("/view/NewFeed.fxml"));
		Stage stage = new Stage();
		Scene scene = new Scene(parent);
		
		stage.setResizable(false);
		stage.setScene(scene);
		stage.setTitle("Añade un nuevo Feed");
		stage.show();
	}
	
	private Folder getFolder(ArrayList<Folder> folderList,String folderName){
		
		for(Folder f : folderList){
	        if(f.getName() == folderName){
	        	return f;
	        }
	    }
		return null;
		
	}
	
	public static void setActualView(String newView){
		actualView = newView;
	}
	
	//Folder Window 
	
	
	
	public void setFolderName(){
		dbh = new DatabaseHandler();
		String newFolderName = newName.getText();
		String folderName = labelFolderName.getText();
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
		String folderName = labelFolderName.getText();
		dbh.deleteFolder(folderName);
		
		loadMain(btnDeleteFolder);
	}
	
	public void loadFolderWindow(Folder folder,TreeItem treeItem){
		Stage stage;
		Parent root;
		
		
		try {
			setActualView("folder");
			actualFolder = folder;
			stage = (Stage) this.folderTree.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/view/Folder.fxml"));
			Scene scene = new Scene(root);
			stage.setTitle(treeItem.getValue().toString());
		    stage.setScene(scene);
		    
		    stage.show();
		    
		    
	        System.out.println("Selected item is" + treeItem);	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		 
	}
	
	public void loadMain(Node element){
		Stage stage;
		Parent root;
		
		
		try {
			setActualView("main");
			stage = (Stage) element.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
			Scene scene = new Scene(root);
			stage.setTitle("BonProfeed");
		    stage.setScene(scene);
		    stage.show();
		    
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	
}
