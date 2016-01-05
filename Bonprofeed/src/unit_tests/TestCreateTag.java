package unit_tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import model.DatabaseHandler;

public class TestCreateTag {

	public static DatabaseHandler dbh;
	
	@BeforeClass
	public static void init() {
		dbh = new DatabaseHandler();
		dbh.clearDatabase();
		dbh.createTag("Tag 2");
	}
	
	@Test
	public void add_New_Tag() {

		String name = "Tag";
		
		int success = dbh.createTag(name);
		
		assertEquals( 1, success );
	}
	
	@Test
	public void not_Add_Repeated_Tag() {
		
		String name = "Tag 2";
		
		int success = dbh.createTag( name );
		
		assertEquals( 0, success );
	}

}
