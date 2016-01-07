package unit_tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.DatabaseHandler;
import model.Folder;

public class TestGetFolders {

	public static DatabaseHandler dbh;
	
	@BeforeClass
	public static void init() {
		dbh = new DatabaseHandler();
	}
	
	@Before
	public void clearDB() {
		dbh.clearDatabase();
	}
	
	@Test
	public void get_Folders() {
		
		dbh.createFolder("Blogs");
		dbh.createFolder("Podcats");
		
		ArrayList<Folder> folders = dbh.getFolders();
		
		assertEquals( 2, folders.size());
	}
	
	@Test
	public void get_Folders_No_Folders() {
		ArrayList<Folder> folders = dbh.getFolders();
		
		assertEquals( 0, folders.size());
	}
}
