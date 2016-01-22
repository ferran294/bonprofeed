package unit_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.DatabaseHandler;

public class TestRenameTags {

	public static DatabaseHandler dbh;
	
	@BeforeClass
	public static void init() {
		dbh = new DatabaseHandler();
		dbh.clearDatabase();
		dbh.createTag("Tag");
		dbh.createTag("Tag 2");
	}
	
	@Test
	public void rename_Tag() {
		
		String newName = "Renamed";
		int success = dbh.renameTag( "Tag 2", newName );
	
		assertEquals( 1, success );
	}
	
	@Test
	public void error_Name_Exist() {
		
		String newName = "Tag";
		
		int success = dbh.renameTag( "Tag 2", newName );
		
		assertEquals( 0, success );
	}

}
