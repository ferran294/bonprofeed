package unit_tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.DatabaseHandler;
import model.Folder;

public class TestGetFolders {

	@Test
	public void get_Folders() {
		DatabaseHandler dbh = new DatabaseHandler();
		dbh.clearDatabase();
		
		dbh.createFolder("Blogs");
		dbh.createFolder("Podcats");
		
		ArrayList<Folder> folders = dbh.getFolders();
		
		assertEquals( 2, folders.size());
	}

}
