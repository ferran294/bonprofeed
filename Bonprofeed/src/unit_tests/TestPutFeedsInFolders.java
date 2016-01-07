package unit_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.DatabaseHandler;

public class TestPutFeedsInFolders {

	@Test
	public void put_Feed_Into_Folder() {
		DatabaseHandler dbh = new DatabaseHandler();
		dbh.clearDatabase();
		
		dbh.createFolder("Blogs");
		dbh.insertFeed( "http://krebsonsecurity.com/feed/", "Krebs On Security" );
		
		int res = dbh.putFeedIntoFolder("Krebs On Security", "Blogs");
		
		assertEquals( 1, res );
	}

}
