package unit_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.DatabaseHandler;

public class TestDeleteTag {

	@Test
	public void delete_Existing_Folder() {
		DatabaseHandler dbh = new DatabaseHandler();
		dbh.clearDatabase();
		dbh.createTag("Favoritos");
		
		int deleted = dbh.deleteTag( "Favoritos" );
	
		assertEquals( 1, deleted );
	}

}
