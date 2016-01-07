package unit_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.DatabaseHandler;

public class TestDeallocateTag {

	@Test
	public void deallocate_Tag() {
		DatabaseHandler dbh = new DatabaseHandler();
		dbh.clearDatabase();
		dbh.createTag("Favoritos");
		dbh.insertFeed( "http://krebsonsecurity.com/feed/", "Krebs On Security" );
		dbh.asignTag("Krebs On Security", "Favoritos" );
		int res = dbh.deallocateTag( "Krebs On Security", "Favoritos" );
		
		assertEquals( 1, res );
	}

}
