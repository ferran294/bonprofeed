package unit_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.DatabaseHandler;

public class TestAddSubscription {
	
	@Test
	public void add_New_Subscription_to_DB() {
		
		// Arrange
		String url = "http://feeds.feedburner.com/FluProject";
		String name = "Flu Project";
		
		DatabaseHandler dbh = new DatabaseHandler();
		
		int inserted = dbh.insertFeed( url, name );
		
		assertEquals( inserted, 1 );
	
	}
	
	@Test
	public void add_Same_Subscription_to_DB() {
		
	}
	
}
