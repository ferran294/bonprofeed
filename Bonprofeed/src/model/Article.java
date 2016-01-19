package model;

import java.net.URL;
import java.util.Date;

public class Article {
	private String title;
	private String author;
	private String content;
	private URL url;
	private int readen;
	private Date date;
	
	public Article( String title, String author, String content, URL url, int readen, Date date ) {
		this.title = title;
		this.author = author;
		this.content = content;
		this.url = url;
		this.readen = readen;
		this.date = date;
		
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getContent() {
		return content;
	}

	public URL getUrl() {
		return url;
	}
	
	public int getReaden() {
		return readen;
	}
	
	public Date getDate() {
		return date;
	}
}
