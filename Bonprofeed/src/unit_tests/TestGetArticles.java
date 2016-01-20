package unit_tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Article;
import model.DatabaseHandler;
import model.Feed;

public class TestGetArticles {
	
	private static DatabaseHandler dbh;
	
	@BeforeClass
	public static void init() {
		dbh = new DatabaseHandler();
		dbh.clearDatabase();
		
		dbh.insertFeed("https://www.google.es", "name");
		dbh.insertFeed("urlFeed", "name2");
		
		
		dbh.insertArticle("art1", "content1", "autor1", "http://url.com", "name", new Date() );
		dbh.insertArticle("art2", "content2", "autor2", "http://url2.com", "name2", new GregorianCalendar(2016, 1, 2, 10, 30).getTime() );
	
	}
	
	@Test
	public void get_All_Articles() {
		
		ArrayList<Article> articles = dbh.getAllArticles();
		
		assertEquals( 2, articles.size());
	}
	
	@Test
	public void check_Articles_Order() {
		ArrayList<Article> articles = dbh.getAllArticles();
		Article art1 = articles.get(0);
		Article art2 = articles.get(1);
		
		assertTrue( art1.getDate().compareTo(art2.getDate() ) == 1 );
	}

	@Test
	public void get_Articles_From_Feed() {
		ArrayList<Article> articles = dbh.getArticlesFromFeed( "name" );
		
		assertEquals( 1, articles.size() );
	}
}
