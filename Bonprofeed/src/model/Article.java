package model;

import java.net.URL;
import java.util.Date;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Article {
	private StringProperty title;
	private StringProperty author;
	private String content;
	private URL url;
	private ObjectProperty<Integer> readen;
	private ObjectProperty<Date> date;
	
	public Article( String title, String author, String content, URL url, int readen, Date date ) {
		this.title = new SimpleStringProperty(title);
		this.author = new SimpleStringProperty(author);
		this.content = content;
		this.url = url;
		this.readen = new SimpleObjectProperty<Integer>(readen);
		this.date = new SimpleObjectProperty<Date>(date);
		
	}

	public String getTitle() {
		return title.get();
	}

	public String getAuthor() {
		return author.get();
	}
	
	public StringProperty getTitleProperty(){
		return title;
	}
	
	public StringProperty getAuthorProperty(){
		return author;
	}

	public String getContent() {
		return content;
	}

	public URL getUrl() {
		return url;
	}
	
	public int getReaden() {
		return readen.get();
	}
	
	public ObjectProperty<Integer> getReadenProperty(){
		return readen;
	}
	
	public Date getDate() {
		return date.get();
	}
	
	public ObjectProperty<Date> getDateProperty(){
		return date;
	}
}
