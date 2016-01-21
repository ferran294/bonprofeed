package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import model.Article;
import model.Folder;
import model.Tag;

public class WindowLoader {

	private static String actualView;
	
	public WindowLoader(){
		super();
		
		
	}
	
	//Load new Feed View
	
	public void loadNewFeedWindow(ActionEvent event,Node element) throws IOException{
		Stage stage;
		Parent root;
		
		
		try {
			setActualView("newFeed");
			
			stage = (Stage) element.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/view/NewFeed.fxml"));
			Scene scene = new Scene(root);
			stage.setTitle("Añade un nuevo Feed");
			stage.setResizable(false);
		    stage.setScene(scene);
		    
		    stage.show();
		    
		   
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		 
	}
	
	//Load the main view
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
	
	//Load the new Folder view
		public void loadNewFolder(Node element){
			Stage stage;
			Parent root;
			
			
			try {
				setActualView("newFolder");
				stage = (Stage) element.getScene().getWindow();
				root = FXMLLoader.load(getClass().getResource("/view/NewFolder.fxml"));
				Scene scene = new Scene(root);
				stage.setTitle("Nueva Carpeta");
			    stage.setScene(scene);
			    stage.show();
			    
			    
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	//Load the folder view
	
	public void loadFolderWindow(Folder folder,TreeItem treeItem,Node element){
		Stage stage;
		Parent root;
		
		
		try {
			setActualView("folder");
			
			stage = (Stage) element.getScene().getWindow();
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
	
	public void loadTagWindow(Tag tag,TreeItem treeItem,Node element){
		Stage stage;
		Parent root;
		
		
		try {
			setActualView("tag");
			
			stage = (Stage) element.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/view/Tag.fxml"));
			Scene scene = new Scene(root);
			stage.setTitle(treeItem.getValue().toString());
		    stage.setScene(scene);
		    
		    stage.show();
		    
		    
	        System.out.println("Selected item is" + treeItem);	
		} catch (IOException e) {
			e.printStackTrace();
		}	
		 
	}
	
	public static void setActualView(String newView){
		actualView = newView;
	}
	
	public static String getActualView(){
		return actualView;
	}

	public void loadNewTag(Node element) {
		
		Stage stage;
		Parent root;
		
		
		try {
			setActualView("newTag");
			stage = (Stage) element.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/view/NewTag.fxml"));
			Scene scene = new Scene(root);
			stage.setTitle("Nueva Etiqueta");
		    stage.setScene(scene);
		    stage.show();
		    
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadArticle(Article article,Node element) {
		Stage stage;
		Parent root;
		
		
		try {
			setActualView("article");
			stage = (Stage) element.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/view/Article.fxml"));
			Scene scene = new Scene(root);
			stage.setTitle(article.getTitle());
		    stage.setScene(scene);
		    stage.show();
		    
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadAssign(Node element){
		Stage stage;
		Parent root;
		
		
		try {
			setActualView("assign");
			stage = (Stage) element.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/view/Assign.fxml"));
			Scene scene = new Scene(root);
			stage.setTitle("Asigna un feed a una carpeta o etiqueta");
		    stage.setScene(scene);
		    stage.show();
		    
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadDeleteFeed(Node element) {
		Stage stage;
		Parent root;
		
		
		try {
			setActualView("deleteFeed");
			stage = (Stage) element.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/view/DeleteFeed.fxml"));
			Scene scene = new Scene(root);
			stage.setTitle("Elimina un Feed");
		    stage.setScene(scene);
		    stage.show();
		    
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
