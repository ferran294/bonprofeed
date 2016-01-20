package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import model.Article;
import model.DatabaseHandler;
import model.Feed;
import model.Folder;
import model.RomeOperations;
import model.Tag;

public class ControllerMain implements Initializable{
	
	private DatabaseHandler dbh = new DatabaseHandler();
	private RomeOperations rome;
	private WindowLoader windowLoader = new WindowLoader();
	private static Folder actualFolder;
	private static Tag actualTag;
	ArrayList<Folder> folders = dbh.getFolders();
	@FXML TreeView<String> folderTree;
	@FXML TreeView<String> tagTree;
	@FXML
	private TableView<Article> articlesList;
	@FXML
	private TableColumn<Article, String> columnTitle;
	@FXML
	private TableColumn<Article, String> columnAuthor;
	@FXML
	private TableColumn<Article, String> columnDate;
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
	@FXML
	private Button btnBack;
	//New Folder view
	@FXML
	private TextField textNewFolder;
	@FXML
	private Button btnNewFolder;
	@FXML
	private Label errorNewFolder;
	//New Tag
	@FXML
	private Button btnNewTag;
	@FXML
	private TextField textNewTag;
	@FXML
	private Label textErrorTag;
	//Tag
	@FXML
	private Label labelTagName;
	
	public ControllerMain(){
		super();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String actualView = windowLoader.getActualView();
		
		if(actualView == "folder"){
			labelFolderName.setText(actualFolder.getName());
		}else if (actualView == "newFolder"){
			
		}else if(actualView == "newTag"){
			
		}else if(actualView == "tag"){
			labelTagName.setText(actualTag.getName());
		}else{
			generateFolderTree();
			generateTagTree();
		}
			
		
		
	}
	
	public void generateArticleList(){
		dbh = new DatabaseHandler();
		ObservableList<Article> articles = FXCollections.observableArrayList(dbh.getAllArticles());
		
		// Initialize the columns.
		columnTitle.setCellValueFactory(cellData -> cellData.getValue().getTitle());
		columnAuthor.setCellValueFactory(cellData -> cellData.getValue().getAuthor());
		columnDate.setCellValueFactory(cellData -> cellData.getValue().getDate());
		
		
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
	
	public void generateTagTree(){
 		//TreeFolder
		dbh = new DatabaseHandler();
 		TreeItem<String> rootNode = new TreeItem<String>("Todas las etiquetas");
 		ArrayList<Tag> tags = dbh.getTags();
 		
		rootNode.setExpanded(true);
		
		
		for (Tag tag : tags) {
            TreeItem<String> foldLeaf = new TreeItem<String>(tag.getName());
            rootNode.getChildren().add(foldLeaf);
           
            ArrayList<Feed> feeds = tag.getFeeds();
            
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
		
		
		
		tagTree.setRoot(rootNode);
		
		tagTree.setEditable(true);
		tagTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	    tagTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
	            
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            
            TreeItem treeItem = (TreeItem)newValue;
            
            if(!treeItem.isLeaf() && treeItem.getParent() != null){
            	
            	Tag tagSelected = getTag(tags, treeItem.getValue().toString());
    			loadTagWindow(tagSelected, treeItem);
            }
            
    		
    		               	                	                
            
        }

		private Tag getTag(ArrayList<Tag> tags, String tagName) {
			for(Tag t : tags){
		        if(t.getName() == tagName){
		        	return t;
		        }
		    }
			return null;
			
		}
            
	    });
		
 	}
	
	public void loadTagWindow(Tag tag,TreeItem treeItem){
		actualTag = tag;
		windowLoader.loadTagWindow(tag, treeItem,tagTree);
		 
	}
	
	public void loadNewFeedWindow(ActionEvent event) throws IOException{
		
		windowLoader.loadNewFeedWindow(event, this.folderTree);
		
		 
	}
	
	private Folder getFolder(ArrayList<Folder> folderList,String folderName){
		
		for(Folder f : folderList){
	        if(f.getName() == folderName){
	        	return f;
	        }
	    }
		return null;
		
	}
	
	
	
	//Folder Window -------------------------------
	
	
	
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
		windowLoader.loadMain(btnDeleteFolder);
	}
	
	public void backFromFolder(){
		windowLoader.loadMain(btnBack);
	}
	
	
	public void loadFolderWindow(Folder folder,TreeItem treeItem){
		actualFolder = folder;
		windowLoader.loadFolderWindow(folder, treeItem,folderTree);
		 
	}
	
	public void loadMain(Node element){
		windowLoader.loadMain(element);
	}
	
	// ---------------------------------------------------
	
	
	//------- New Folder Controller -----------
	
	public void loadNewFolder(){
		windowLoader.loadNewFolder(btnNewFolder);
	}
	
	public void createFolder(){
		dbh = new DatabaseHandler();
		String newFolderName = textNewFolder.getText();
		
		
		int done = dbh.createFolder(newFolderName);
		
		if(done == 0){
			
			errorNewFolder.setText("El nombre de carpeta ya existe");
		}else{
			
			
			loadMain(textNewFolder);
		}
	}
	
	// --------- New Tag Controller ----------------
	
	public void loadNewTagWindow(){
		windowLoader.loadNewTag(btnNewTag);
	}
	
	public void addTag(){
		dbh = new DatabaseHandler();
		String newTagName = textNewTag.getText();
		
		int done = dbh.createTag(newTagName);
		
		if(done == 0){
			textErrorTag.setText("El nombre de etiqueta ya existe");
		}else{
			loadMain(textNewTag);
		}
	}
	
	// ----------------- Tag Controller ---------
	public void setTagName(){
		/*
		dbh = new DatabaseHandler();
		String newTagName = textNewTag.getText();
		String tagName = labelTagName.getText();
		System.out.println(newTagName);
		System.out.println(tagName);
		
		int done = dbh.renameTag(tagName, newTagName);
		
		if(done == 0){
			errorFolder.setText("El nombre de etiqueta ya existe");
		}else{
			labelTagName.setText(newTagName);
			errorFolder.setText("Hecho!");
		}
		*/
	}
	
	public void deleteTag(){
		dbh = new DatabaseHandler();
		String tagName = labelTagName.getText();
		dbh.deleteTag(tagName);
		windowLoader.loadMain(labelTagName);
	}
}
