package unit_tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.Article;
import model.DatabaseHandler;
import model.Feed;

public class TestGetArticles {

	@Test
	public void get_All_Articles() {
		DatabaseHandler dbh = new DatabaseHandler();
		dbh.clearDatabase();
		
		dbh.insertFeed("https://www.google.es", "name");
		dbh.insertFeed("urlFeed", "name2");
		
		dbh.insertArticle("art1", "content1", "autor1", "http://url.com", "name");
		dbh.insertArticle("art2", "content2", "autor2", "http://url2.com", "name2");
	
		ArrayList<Article> articles= dbh.getAllArticles();
		
		assertEquals( 2, articles.size());
	}
	
	@Test
	public void check_Articles_Order() {
		
	}

}
