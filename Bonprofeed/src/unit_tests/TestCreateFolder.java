package unit_tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.DatabaseHandler;

public class TestCreateFolder {
	
	public static DatabaseHandler dbh;
	
	@BeforeClass
	public static void init() {
		dbh = new DatabaseHandler();
		dbh.clearDatabase();
		dbh.createFolder("Folder 2");
	}
	
	@Test
	public void add_New_Folder() {

		String name = "Folder";
		
		int success = dbh.createFolder(name);
		
		assertEquals( 1, success );
	}
	
	@Test
	public void not_Add_Same_Folder() {
		
		String name = "Folder 2";
		
		int success = dbh.createFolder( name );
		
		assertEquals( 0, success );
	}

}
