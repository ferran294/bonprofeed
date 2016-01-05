package unit_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.DatabaseHandler;

public class TestDeleteSubscription {

	@Test
	public void delete_Existing_Feed() {
		DatabaseHandler dbh = new DatabaseHandler();
		dbh.clearDatabase();
		
		String url = "http://feeds.feedburner.com/ElLadoDelMal";
		String name = "El lado del mal";
		
		dbh.insertFeed(url, name);
		
		int deleted = dbh.deleteFeed(name);
	
		assertEquals( 1, deleted );
	}

}
