package model;

import java.util.LinkedList;

public class Folder {

	private String name;
	private LinkedList<Feed> feedList;
	private DatabaseHandler dbh;
	
	public Folder(String folderName) {
		dbh = new DatabaseHandler();
		this.name = folderName;
		this.feedList = dbh.getFeedsFromFolder(folderName);
	}
	
	public LinkedList<Feed> getFeeds(){
		return this.feedList;
	}
	
	public String getName(){
		return this.name;
	}
	
	public boolean setName(String newName){
		int done = dbh.renameFolder(this.name, newName); 
		
		if(done == 0){
			return false;
		}else{
			this.name = newName;
			return true;
		}
		
	}

}
