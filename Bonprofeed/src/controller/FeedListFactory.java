package controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.Article;
import model.DatabaseHandler;
import model.Feed;

public class FeedListFactory {

	private DatabaseHandler dbh;
	private ListView<Feed> listView;
	
	public FeedListFactory(DatabaseHandler dbh,ListView<Feed> listView){
		this.dbh = dbh;
		this.listView = listView;
	}
	
	public void generateFolderFeedsList(String folder){
		ObservableList<Feed> feeds = FXCollections.observableArrayList(dbh.getFeedsFromFolder(folder));
		
		
		if(feeds.size()>0){
				listView.setItems(feeds);
		
				listView.setCellFactory(new Callback<ListView<Feed>, ListCell<Feed>>(){
			 
	            @Override
	            public ListCell<Feed> call(ListView<Feed> p) {
	                 
	                ListCell<Feed> cell = new ListCell<Feed>(){
	 
	                    @Override
	                    protected void updateItem(Feed f, boolean bln) {
	                        super.updateItem(f, bln);
	                        if (f != null) {
	                            setText(f.getName());
	                        }
	                    }
	 
	                };
	                 
	                return cell;
	            }
	        });
		
		}
	}
	
	
	/*
	public void generateAllFeedsList(){
		ObservableList<Feed> feeds = FXCollections.observableArrayList(dbh.getAllFeeds());
		listView.setItems(feeds);
		
		 listView.setCellFactory(new Callback<ListView<Feed>, ListCell<Feed>>(){
			 
	            @Override
	            public ListCell<Feed> call(ListView<Feed> p) {
	                 
	                ListCell<Feed> cell = new ListCell<Feed>(){
	 
	                    @Override
	                    protected void updateItem(Feed f, boolean bln) {
	                        super.updateItem(f, bln);
	                        if (f != null) {
	                            setText(f.getName());
	                        }
	                    }
	 
	                };
	                 
	                return cell;
	            }
	        });
		
		 
	}
	*/

	public void generateTagFeedsList(String tag) {
		ObservableList<Feed> feeds = FXCollections.observableArrayList(dbh.getTaggedFeeds(tag));
		
		
		if(feeds.size()>0){
			listView.setItems(feeds);
			
			 listView.setCellFactory(new Callback<ListView<Feed>, ListCell<Feed>>(){
				 
		            @Override
		            public ListCell<Feed> call(ListView<Feed> p) {
		                 
		                ListCell<Feed> cell = new ListCell<Feed>(){
		 
		                    @Override
		                    protected void updateItem(Feed f, boolean bln) {
		                        super.updateItem(f, bln);
		                        if (f != null) {
		                            setText(f.getName());
		                        }
		                    }
		 
		                };
		                 
		                return cell;
		            }
		        });
		}
	        
	}
	
}
