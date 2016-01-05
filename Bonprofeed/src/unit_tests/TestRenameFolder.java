package unit_tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.DatabaseHandler;

public class TestRenameFolder {

	public static DatabaseHandler dbh;
	
	@BeforeClass
	public static void init() {
		dbh = new DatabaseHandler();
		dbh.clearDatabase();
		dbh.createFolder("Folder 2");
	}
	
	@Before
	public void insertFolder() {
		dbh.createFolder("Folder");
	}
	
	@Test
	public void rename_Folder() {
		
		String newName = "Renamed";
		int success = dbh.renameFolder( "Folder", newName );
	
		assertEquals( 1, success );
	}
	
	@Test
	public void error_Name_Exist() {
		
		String newName = "Folder";
		
		int success = dbh.renameFolder( "Folder 2", newName );
		
		assertEquals( 0, success );
	}

}
