package unit_tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.DatabaseHandler;
import model.Tag;

public class TestGetTags {

	@Test
	public void get_All_Tags() {
		DatabaseHandler dbh = new DatabaseHandler();
		dbh.clearDatabase();
		
		dbh.createTag("Favoritos");
		dbh.createTag("Revisión diaria");
		ArrayList<Tag> tags = dbh.getTags();
		
		assertEquals( 2, tags.size());
	}

}
