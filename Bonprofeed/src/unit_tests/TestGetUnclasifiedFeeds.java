package unit_tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.Feed;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.DatabaseHandler;

public class TestGetUnclasifiedFeeds {

	public static DatabaseHandler dbh;
	
	@BeforeClass
	public static void initDBH() {
		dbh = new DatabaseHandler();
	}
	
	@Before
	public void clearDatabase() {
		dbh.clearDatabase();
	}
	
	@Test
	public void get_Unclasified_Feeds_All_Unclassified() {
		
		dbh.insertFeed("https://www.google.es", "name");
		dbh.insertFeed("urlFeed", "name2");
		
		ArrayList<Feed> feeds = dbh.getUnclassifiedFeeds();
		
		assertEquals( 2, feeds.size());
	}
	
	@Test
	public void get_Unclasified_Feeds_One_Classified() {
		
		dbh.insertFeed("url", "name");
		dbh.insertFeed("urlFeed", "name2");
		dbh.insertFeed("url3", "name3");
		dbh.createFolder("Folder");
		dbh.putFeedIntoFolder("name3", "Folder");
		
		ArrayList<Feed> feeds = dbh.getUnclassifiedFeeds();
		
		assertEquals( 2, feeds.size());
	}

}
