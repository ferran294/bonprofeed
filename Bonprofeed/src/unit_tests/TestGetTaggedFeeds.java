package unit_tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.DatabaseHandler;
import model.Feed;

public class TestGetTaggedFeeds {

	@Test
	public void get_Tagged_Feeds() {
		DatabaseHandler dbh = new DatabaseHandler();
		dbh.clearDatabase();
		
		dbh.insertFeed( "http://krebsonsecurity.com/feed/", "Krebs On Security" );
		dbh.insertFeed( "http://krebsonsecurity.com/", "Security" );
		dbh.createTag( "Favoritos" );
		dbh.asignTag("Krebs On Security", "Favoritos");
		dbh.asignTag("Security", "Favoritos");
		
		ArrayList<Feed> feeds = dbh.getTaggedFeeds( "Favoritos" );
		
		assertEquals( 2, feeds.size() );
	}

}
