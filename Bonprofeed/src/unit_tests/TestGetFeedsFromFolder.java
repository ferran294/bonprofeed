package unit_tests;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

import model.DatabaseHandler;
import model.Feed;

public class TestGetFeedsFromFolder {

	@Test
	public void get_Feeds_From_Folder() {
		DatabaseHandler dbh = new DatabaseHandler();
		dbh.clearDatabase();
		
		dbh.insertFeed( "http://krebsonsecurity.com/feed/", "Krebs On Security" );
		dbh.insertFeed( "http://krebsonsecurity.com/", "Security" );
		dbh.createFolder( "Blogs" );
		dbh.putFeedIntoFolder("Krebs On Security", "Blogs");
		dbh.putFeedIntoFolder("Security", "Blogs");
		
		LinkedList<Feed> feeds = dbh.getFeedsFromFolder( "Blogs" );
		
		assertEquals( 2, feeds.size() );
		
	}

}
