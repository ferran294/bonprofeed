package model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rometools.rome.feed.atom.Content;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

public class RomeOperations {
	
	public RomeOperations() {
		super();
	
	}
	
	/**
	 * function isFeed
	 * 
	 * Comprueba que la cadena introducida es una URL válida de una fuente de datos RSS.
	 * 
	 * @param str
	 * @return true, si se trata de una fuente de datos válida, false en caso contrario.
	 * 
	 */
	
	public boolean isFeed( String str ) {
		
		boolean ok = false;
		try {
			URL url = new URL( str );
			SyndFeedInput input = new SyndFeedInput();
			
			SyndFeed feed = input.build( new XmlReader( url ));
			
			ok = true;
			
		} catch ( FeedException e ) {
			System.out.println("ERROR: This is not a feed! ...");
		} catch ( MalformedURLException e ) {
			System.out.println("ERROR: This is not a valid URL! ...");
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		return ok;
	}
	
	public String getFeedName(String str){
		
		
		try {
			
			URL url = new URL( str );
			
			SyndFeedInput input = new SyndFeedInput();
			
			 
			try {
				
				SyndFeed feed = input.build( new XmlReader( url ));
				
				return feed.getTitle();
			
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			
			} catch (FeedException e) {
				e.printStackTrace();
			
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return null;		
		
	}
	
	public void pushArticlesIntoDatabase( String link ) {
		DatabaseHandler dbh = new DatabaseHandler();
		
		SyndFeedInput input = new SyndFeedInput();			
		SyndFeed feed;
		
		URL feedUrl;
		
		try {
			feedUrl = new URL ( link );
			feed = input.build( new XmlReader( feedUrl ));
		
			List<SyndEntry> entryList = feed.getEntries();
			
			for ( int i = 0; i < 15; i++ ) {
				String author = entryList.get(i).getAuthor();
				String content = entryList.get(i).getDescription().getValue();
				String title = entryList.get(i).getTitle();
				String url = entryList.get(i).getLink();
				Date date = entryList.get(i).getPublishedDate();
				
				dbh.insertArticle(title, content, author, link, feed.getTitle(), date );
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FeedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateArticles() {
		
		DatabaseHandler dbh = new DatabaseHandler();
		ArrayList<Feed> feeds = dbh.getAllFeeds();
	
		for ( int i = 0; i < feeds.size(); i++ ) {
			SyndFeedInput input = new SyndFeedInput();			
			SyndFeed feed;
			
			Feed f = feeds.get(i);
			
			ArrayList<Article> articles = dbh.getArticlesFromFeed( f.getName() );
			
			try {
				URL feedUrl = new URL ( f.getUrl() );
				feed = input.build( new XmlReader( feedUrl ));
				
				List<SyndEntry> entryList = feed.getEntries();
				
				for ( int j = 0; j < entryList.size(); j++ ) {
				
					String author = entryList.get(j).getAuthor();
					
					String content = entryList.get(j).getDescription().getValue();
					
					
					String title = entryList.get(j).getTitle();
					String link = entryList.get(j).getLink();
					Date date = entryList.get(j).getPublishedDate();
					
					if( date.compareTo(articles.get(0).getDate()) < 1 ) {
						break;
					}
					
					dbh.insertArticle(title, content, author, link, f.getName(), date );
				}
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (FeedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
