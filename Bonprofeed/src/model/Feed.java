package model;

public class Feed {

	private String name;
	private String url;
	
	public Feed(String name,String url){
		this.name = name;
		this.url = url;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getUrl(){
		return this.url;
	}
	
}
