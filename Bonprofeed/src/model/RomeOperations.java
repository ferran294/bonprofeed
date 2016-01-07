package model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
}
