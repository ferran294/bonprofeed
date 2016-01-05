package unit_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.DatabaseHandler;

public class TestDeleteFolder {

	@Test
	public void delete_Existing_Folder() {
		DatabaseHandler dbh = new DatabaseHandler();
		dbh.clearDatabase();
		dbh.createFolder("Folder");
		
		int deleted = dbh.deleteFolder( "Folder" );
	
		assertEquals( 1, deleted );
	}

}
