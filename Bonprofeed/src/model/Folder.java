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

}
