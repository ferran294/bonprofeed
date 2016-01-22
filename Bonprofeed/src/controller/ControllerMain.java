package controller;

import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
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
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Article;
import model.ArticleTable;
import model.DatabaseHandler;
import model.Feed;
import model.Folder;
import model.RomeOperations;
import model.Tag;

public class ControllerMain implements Initializable{
	
	private DatabaseHandler dbh = new DatabaseHandler();
	private WindowLoader windowLoader = new WindowLoader();
	private static Folder actualFolder;
	private Article actualArticle;
	private static Tag actualTag;
	ArrayList<Folder> folders = dbh.getFolders();
	ArrayList<Feed> feeds = dbh.getAllFeeds();
	@FXML TreeView<String> folderTree;
	@FXML TreeView<String> tagTree;
	//Article table from Main
	@FXML
	private TableView<Article> articlesList;
	@FXML
	private TableColumn<Article, Integer> columnVisto;
	@FXML
	private TableColumn<Article, String> columnTitle;
	@FXML
	private TableColumn<Article, String> columnAuthor;
	@FXML
	private TableColumn<Article, Date> columnDate;
	@FXML private AnchorPane panelArticles;
	//Article table from feed
	@FXML
	private TableView<Article> articlesListFeed;
	@FXML
	private TableColumn<Article, Integer> columnVistoFeed;
	@FXML
	private TableColumn<Article, String> columnTitleFeed;
	@FXML
	private TableColumn<Article, String> columnAuthorFeed;
	@FXML
	private TableColumn<Article, Date> columnDateFeed;
	@FXML
	private Label textFeedTitle;
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
	@FXML
	private ListView listFeeds;
	@FXML
	private TextField feedNameDis;
	@FXML
	private TableView<Article> articlesListFolder;
	@FXML
	private TableColumn<Article, Integer> columnVistoFolder;
	@FXML
	private TableColumn<Article, String> columnTitleFolder;
	@FXML
	private TableColumn<Article, String> columnAuthorFolder;
	@FXML
	private TableColumn<Article, Date> columnDateFolder;
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
	@FXML
	private TextField feedDisTag;
	@FXML
	private Label errorDisTag;
	@FXML
	private ListView listFeedsTag;
	@FXML
	private TableView<Article> articlesListTag;
	@FXML
	private TableColumn<Article, Integer> columnVistoTag;
	@FXML
	private TableColumn<Article, String> columnTitleTag;
	@FXML
	private TableColumn<Article, String> columnAuthorTag;
	@FXML
	private TableColumn<Article, Date> columnDateTag;
	//Article
	@FXML
	private Label textTitle;
	@FXML
	private Label textAuthor;
	@FXML
	private Label textDate;
	@FXML private WebView htmlContainer = new WebView();
	//Assign
	@FXML
	private Label errorTagAssign;
	@FXML
	private Label errorFolderAssign;
	@FXML
	private TextField textFeedTag;
	@FXML
	private TextField textTagAssigned;
	@FXML
	private TextField textFeedFolder;
	@FXML
	private TextField textFolderAssigned;
	//Delete
	@FXML
	private TextField feedName;
	@FXML
	private Label errorDeleteTag;
	
	public ControllerMain(){
		super();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String actualView = windowLoader.getActualView();
	
		
		if(actualView == "folder"){
			
			labelFolderName.setText(actualFolder.getName());
			FeedListFactory listGenerator = new FeedListFactory(dbh, listFeeds);
			listGenerator.generateFolderFeedsList(actualFolder.getName());
			
			ObservableList<Article> articles = FXCollections.observableArrayList(dbh.getArticlesFromFolder(actualFolder.getName()));
			ArticleTable articleTable = new ArticleTable(articlesListFolder, columnVistoFolder, columnTitleFolder, columnAuthorFolder, columnDateFolder);
			ArticleTableLoader tableLoader = new ArticleTableLoader(articleTable, articles, windowLoader);
			tableLoader.generateArticleTable();
			
		}else if (actualView == "newFolder"){
			
		}else if(actualView == "newTag"){
			
		}else if(actualView == "tag"){
			
			labelTagName.setText(actualTag.getName());
			FeedListFactory listGenerator = new FeedListFactory(dbh, listFeedsTag);
			listGenerator.generateTagFeedsList(actualTag.getName());
			
			ObservableList<Article> articles = FXCollections.observableArrayList(dbh.getArticlesFromTag(actualTag.getName()));
			ArticleTable articleTable = new ArticleTable(articlesListTag, columnVistoTag, columnTitleTag, columnAuthorTag, columnDateTag);
			ArticleTableLoader tableLoader = new ArticleTableLoader(articleTable, articles, windowLoader);
			tableLoader.generateArticleTable();
			
		}else if(actualView == "article"){
			
			

		}else if(actualView == "assign"){
			
		}else if(actualView == "deleteFeed"){
			
		}else if(actualView == "feed"){
			
		}else{
		
			generateFolderTree();
			generateTagTree();
			generateArticleList();
		
		}
			
		
		
	}
	
	public void generateArticleList(){
		ObservableList<Article> articles = FXCollections.observableArrayList(dbh.getAllArticles());
		ArticleTable articleTable = new ArticleTable(articlesList, columnVisto, columnTitle, columnAuthor, columnDate);
		ArticleTableLoader tableLoader = new ArticleTableLoader(articleTable, articles, windowLoader);
		tableLoader.generateArticleTable();
	}
	
	public void generateFolderTree(){
 		//TreeFolder
		
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
            
            if(treeItem.isLeaf()){
            	Feed feedSelected = getFeed(feeds,treeItem.getValue().toString());
            	
            	windowLoader.loadFeedWindow(feedSelected,treeItem,folderTree);
            }
            
    		
    		               	                	                
            
        }
            
	    });
		
 	}
	
	public void generateTagTree(){
 		//TreeFolder
		
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
	
	public static void setActualFolder(Folder folder){
		actualFolder=folder;
	}
	
	public void setFolderName(){
		
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
	
	public void disassignFeedFromFolder(){
		String folderName = labelFolderName.getText();
		String feedName = feedNameDis.getText();
		
		int done = dbh.disassignFromFolder(feedName);
		
		if(done == 0){
			errorFolder.setText("El feed no existe");
		}else{
			loadMain(feedNameDis);
		}
		
	}
	
	public void deleteFolder(){
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
		
		String newTagName = textNewTag.getText();
		String tagName = labelTagName.getText();
		System.out.println(newTagName);
		System.out.println(tagName);
		
		int done = dbh.renameTag(tagName, newTagName);
		
		if(done == 0){
			textErrorTag.setText("El nombre de etiqueta ya existe");
		}else{
			labelTagName.setText(newTagName);
			textErrorTag.setText("Hecho!");
		}
		
	}
	
	public void deleteTag(){
		String tagName = labelTagName.getText();
		dbh.deleteTag(tagName);
		windowLoader.loadMain(labelTagName);
		
	}
	
	public void disassignFeedFromTag(){
		String tagName = labelTagName.getText();
		String feedName = feedDisTag.getText();
		
		int done = dbh.deallocateTag(feedName, tagName);
		
		if(done == 0){
			errorDisTag.setText("El Feed no existe");
		}else{
			loadMain(feedDisTag);
		}
		
	}
	
	//------ Assign controller --------
	
	public void loadAssign(){
		windowLoader.loadAssign(btnNewFolder);
	}
	
	
	public void assignTag(){
		String feedName = textFeedTag.getText();
		String tagName = textTagAssigned.getText();
		
		int done = dbh.asignTag(feedName, tagName);
		
		if(done == 0){
			errorTagAssign.setText("El nombre de etiqueta o feed es incorrecto");
		}else{
			loadMain(textFeedTag);
		}
	}
	
	public void assignFolder(){
		String feedName = textFeedFolder.getText();
		String folderName = textFolderAssigned.getText();
		
		int done = dbh.putFeedIntoFolder(feedName, folderName);
		
		if(done == 0){
			errorFolderAssign.setText("El nombre de carpeta o feed es incorrecto/ El feed se encuentra ya en otra carpeta");
		}else{
			loadMain(textFeedTag);
		}
		
	}
	
	//----- Delete Controller -----
	
	public void loadDeleteFeed(){
		windowLoader.loadDeleteFeed(btnNewFolder);
	}
	
	public void deleteFeed(){
		String feed = feedName.getText();
		
		int done = dbh.deleteFeed(feed);
		
		if(done == 0){
			errorDeleteTag.setText("El nombre de  feed es incorrecto");
		}else{
			loadMain(errorDeleteTag);
		}
	}
	
	//------- Article controller ------
	
	public void setActualArticle(Article article){
		this.actualArticle=article;
		textTitle.setText(actualArticle.getTitle());
		textAuthor.setText(actualArticle.getAuthor());
		textDate.setText(actualArticle.getDate().toString());
		dbh.markAsRead(actualArticle.getTitle());
		final WebEngine webEngine = htmlContainer.getEngine();
		webEngine.loadContent(actualArticle.getContent());
		
		
	}
	
	
	//------ Feed controller ----
	
	public void setActualFeed(Feed feed){
		
		textFeedTitle.setText(feed.getName());
		ObservableList<Article> articles = FXCollections.observableArrayList(dbh.getArticlesFromFeed(feed.getName()));
		ArticleTable articleTable = new ArticleTable(articlesListFeed, columnVistoFeed, columnTitleFeed, columnAuthorFeed, columnDateFeed);
		ArticleTableLoader tableLoader = new ArticleTableLoader(articleTable, articles, windowLoader);
		tableLoader.generateArticleTable();
	}
	
	public Feed getFeed(ArrayList<Feed> feeds,String feedName){
		for(Feed f : feeds){
	        if(f.getName().equals(feedName)){
	        	return f;
	        }
	    }
		return null;
	}
}
