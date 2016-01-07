package unit_tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.DatabaseHandler;

public class TestAsignTag {
	
	public static DatabaseHandler dbh;
	
	@BeforeClass
	public static void init() {
		dbh = new DatabaseHandler();
		dbh.clearDatabase();
		dbh.insertFeed( "http://krebsonsecurity.com/feed/", "Krebs On Security" );
		dbh.createTag( "Favoritos" );
		dbh.createTag( "Importante" );
		dbh.asignTag( "Krebs On Security", "Importante" );
	}
	
	@Test
	public void assign_New_Tag() {
		
		int success = dbh.asignTag( "Krebs On Security", "Favoritos" );
		
		assertEquals( 1, success );
	}
	
	@Test
	public void not_Assing_Repeaded_Tag() {
	
		int success = dbh.asignTag( "Krebs On Security", "Importante" );
		
		assertEquals( 0, success );
	}

}
