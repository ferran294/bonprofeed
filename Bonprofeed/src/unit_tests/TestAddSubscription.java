package unit_tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import model.DatabaseHandler;
import model.RomeOperations;

public class TestAddSubscription {
	
	private static DatabaseHandler dbh;
	private static RomeOperations ro;
	
	@BeforeClass
	public static void insertSubscription() {
		String url = "http://feeds.feedburner.com/ElLadoDelMal";
		String name = "Un informático en el lado del mal";
		
		dbh.insertFeed(url, name);
	}
	
	@BeforeClass
	public static void clearTableFeeds() {	
		dbh = new DatabaseHandler();
		dbh.clearDatabase();
	}
	
	@BeforeClass
	public static void createRO() {
		ro = new RomeOperations();
	}
	
	@Test
	public void add_New_Subscription_to_DB() {
		// Arrange
		String url = "http://feeds.feedburner.com/FluProject";
		String name = "Flu Project";
		
		int inserted = dbh.insertFeed( url, name );
		
		assertEquals( 1, inserted );
	
	}
	
	@Test
	public void add_Same_Subscription_to_DB() {
		// Arrange
		String url = "http://feeds.feedburner.com/ElLadoDelMal";
		String name = "Un informático en el lado del mal";
		
		// Act
		int inserted = dbh.insertFeed(url, name);
		
		// Assert		
		assertEquals( 0, inserted );
	}
	
	
	@Test
	public void accept_Good_Links() {
		String url = "http://feeds.feedburner.com/ElLadoDelMal";
		assertTrue( ro.isFeed(url) );
	}
	
	@Test
	public void reject_Bad_Links() {
		String url = "nothing";
		assertFalse( ro.isFeed(url) );
	}
	
}
