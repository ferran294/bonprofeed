package unit_tests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import model.DatabaseHandler;

public class TestMarkAsRead {

	@Test
	public void mark_Article_As_Read() {
		DatabaseHandler dbh = new DatabaseHandler();
		dbh.clearDatabase();
	
		dbh.insertFeed("url", "feed");
		dbh.insertArticle("Pinocha", "Un finde to guapo en Marina D'Or", "El Serio", "https://www.youtube.com/watch?v=zUzcfIEgDrE", "feed", new Date());
		
		int res = dbh.markAsRead( "Pinocha" );
		
		assertEquals( 1, res );
	}

}
