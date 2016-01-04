package controller;

import model.DatabaseHandler;
import model.RomeOperations;

public class Controller {
	
	private DatabaseHandler dbh;
	private RomeOperations rome;
	
	public Controller() {
		dbh = new DatabaseHandler();
		rome = new RomeOperations();
	}
	public void addFeed(){
		String validUrl = "http://feeds.feedburner.com/ElLadoDelMal";
	
		boolean isFeed = rome.isFeed(validUrl);
		System.out.println( isFeed );
		
	}
}
