package unit_tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import model.DatabaseHandler;

public class TestViewFolders {

	public static DatabaseHandler dbh;
	
	@BeforeClass
	public static void init() {
		dbh = new DatabaseHandler();
		dbh.clearDatabase();
		dbh.createFolder("Folder 2");
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
