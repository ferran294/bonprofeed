package unit_tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import model.Article;
import model.DatabaseHandler;

public class TestGetArticlesFromFolder {

	@Test
	public void get_Articles_From_Folder() {
		DatabaseHandler dbh = new DatabaseHandler();
		dbh.clearDatabase();
		
		dbh.insertFeed("http://www.google.es", "Google");
		dbh.createFolder("Cosas");
		dbh.putFeedIntoFolder("Google", "Cosas");		
		
		dbh.insertArticle("title", "content", "author", "http://www.link.com", "Google", new Date());
		dbh.insertArticle("title2", "content2", "author2", "http://www.link.com", "Google", new Date());
		
		ArrayList<Article> articles = dbh.getArticlesFromFolder("Cosas");
		
		assertEquals( articles.size(), 2);
		
	}

}
