package model;

import java.util.ArrayList;

public class Tag {

	private String name;
	private ArrayList<Feed> feeds;
	private DatabaseHandler dbh;
	
	public Tag( String name ) {
		this.name = name;
		this.dbh = new DatabaseHandler();
		this.feeds = dbh.getTaggedFeeds(name);
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<Feed> getFeeds() {
		return this.feeds;
	}
	
}
