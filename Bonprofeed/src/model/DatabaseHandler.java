package model;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import model.Feed;

public class DatabaseHandler {
	
	public DatabaseHandler() {
		super();
	}
	
	public int createTag( String name ) {
		Connection conn = getConnection();
		PreparedStatement statement = null;
		int ret = 0;
		
		String sql = "INSERT INTO tags ( name ) VALUES ( ? );";
		
		try {
			statement = conn.prepareStatement( sql );
			statement.setString(1, name);
			ret = statement.executeUpdate();
		
		} catch ( MySQLIntegrityConstraintViolationException e ) {
			
			System.out.println( "ERROR: La etiqueta ya existe." );
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
		    try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		return ret;
		
	}
	
	public int deleteFolder( String name ) {
		int ret = 0;
		Connection con = getConnection();
		PreparedStatement statement = null;
		
		String sql = "DELETE FROM folders WHERE name = ?;";
		
		try {
			statement = con.prepareStatement( sql );
			statement.setString(1, name);
			ret = statement.executeUpdate();
		
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
		    try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		return ret;
	}
	
	public int renameFolder( String oldName, String newName ) {
		
		Connection con = getConnection();
		PreparedStatement sta = null;
		int ret = 0;
		
		String sql = "UPDATE folders SET name = ? WHERE name = ?;";
		
		try {
			sta = con.prepareStatement( sql );
			sta.setString(1, newName);
			sta.setString(2, oldName);
			ret = sta.executeUpdate( );
		} catch( MySQLIntegrityConstraintViolationException e ) {
			System.out.println("ERROR: Ya existe una carpeta con el mismo nombre.");
		} catch ( SQLException e) {
			e.printStackTrace();
		
		} finally {
			try { sta.close(); } catch (SQLException e) { e.printStackTrace(); }
		    try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		return ret;
	}
	
public int renameTag( String oldName, String newName ) {
		
		Connection con = getConnection();
		PreparedStatement sta = null;
		int ret = 0;
		
		String sql = "UPDATE tags SET name = ? WHERE name = ?;";
		
		try {
			sta = con.prepareStatement( sql );
			sta.setString(1, newName);
			sta.setString(2, oldName);
			ret = sta.executeUpdate( );
		} catch( MySQLIntegrityConstraintViolationException e ) {
			System.out.println("ERROR: Ya existe una carpeta con el mismo nombre.");
		} catch ( SQLException e) {
			e.printStackTrace();
		
		} finally {
			try { sta.close(); } catch (SQLException e) { e.printStackTrace(); }
		    try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		return ret;
	}
	
	public int createFolder(String name){
		Connection conn = getConnection();
		PreparedStatement statement = null;
		int ret = 0;
		
		String sql = "INSERT INTO folders ( name ) VALUES ( ? );";
		
		try {
			
			statement = conn.prepareStatement( sql );
			statement.setString(1, name);
			ret = statement.executeUpdate();
		
		} catch ( MySQLIntegrityConstraintViolationException e ) {
			
			System.out.println( "ERROR: La carpeta ya existe." );
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
		    try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		return ret;
	}
	
	public void initialize() {
		
		boolean connected = false;
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println( "Connecting to database..." );
			connection = DriverManager.getConnection( "jdbc:mysql://localhost/bonprofeed", "root", "" );
			
			connected = true;
			System.out.println( "Connecting done!..." );
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		
		} catch ( SQLException e ) {
			System.out.println("Database doesn't exist...");
			connected = false;
		}
		
		if ( !connected ) {
			this.createDatabase();
		} else {
		
			try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
	}
	
	private Connection getConnection() {
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection( "jdbc:mysql://localhost/bonprofeed", "root", "" );
		} catch (ClassNotFoundException e ){
			e.printStackTrace();
		} catch (SQLException e ) {
			e.printStackTrace();
		}
		
		return connection;
	}
	
	
	/**
	 * function createDatabase()
	 * 
	 * Crea la base de datos.
	 * 
	 */
	private void createDatabase() {
		
		Connection connection = null ;
		Statement statement = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection( "jdbc:mysql://localhost/", "root", "" );
			statement = connection.createStatement();
			String sql = "CREATE DATABASE bonprofeed";
			
			statement.executeUpdate( sql );
			System.out.println("Creating database...");
			this.createDatabaseTables();
			
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		
		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
		    try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
	}
	
	/**
	 * function createDatabaseTables()
	 * 
	 * Crea las tablas de la base de datos
	 * 
	 */
	private void createDatabaseTables() {
		//Realizando conexion a base de datos recien creada
		Connection connection=this.getConnection();
		Statement statement=null;
		
		String tablaArticles = "CREATE TABLE IF NOT EXISTS `articles` ("
				  +"`id` int(11) NOT NULL,"
				  +"`title` varchar(255) NOT NULL,"
				  +"`author` varchar(255) NOT NULL,"
				  +"`link` varchar(255) NOT NULL,"
				  +"`readen` int(1) NOT NULL,"
				  +"`content` text NOT NULL,"
				  +"`date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"
				  +"`source` int(11) NOT NULL"
				  +") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		
		String tablaFeeds = "CREATE TABLE IF NOT EXISTS `feeds` ("
				+"`id` int(11) NOT NULL,"
				  +"`name` varchar(50) UNIQUE NOT NULL,"
				  +"`url` varchar(255) UNIQUE NOT NULL"
				+") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		
		String tablaFeeds_folders = "CREATE TABLE IF NOT EXISTS `feeds_folders` ("
				  +"`id_feed` int(11) NOT NULL,"
				  +"`id_folder` int(11) NOT NULL,"
				  +"`date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"
				+") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		
		String tablaFeeds_tags = "CREATE TABLE IF NOT EXISTS `feeds_tags` ("
				  +"`id_feed` int(11) NOT NULL,"
				  +"`id_tag` int(11) NOT NULL,"
				  +"`date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"
				+") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		
		String tablaFolders = "CREATE TABLE IF NOT EXISTS `folders` ("
				+"`id` int(11) NOT NULL,"
				  +"`name` varchar(50) UNIQUE NOT NULL,"
				 +"`date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"
				+") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		
		String tablaTags = "CREATE TABLE IF NOT EXISTS `tags` ("
				+"`id` int(11) NOT NULL,"
				  +"`name` varchar(50) UNIQUE NOT NULL"
				+") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		
		//Indices

		String indicesArticles = "ALTER TABLE `articles`"
		+" ADD PRIMARY KEY (`id`), ADD KEY `articles_source` (`source`);";
		
		String indicesFeeds = "ALTER TABLE `feeds`"
				+" ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `id` (`id`);";
		
		String indicesFeeds_folders = "ALTER TABLE `feeds_folders`"
				+" ADD PRIMARY KEY (`id_feed`), ADD KEY `id_folder` (`id_folder`);";
		
		String indicesFeeds_tags = "ALTER TABLE `feeds_tags`"
				+" ADD PRIMARY KEY (`id_feed`,`id_tag`), ADD KEY `fk_feeds_tags_tags` (`id_tag`);";
		
		String indicesFolders = "ALTER TABLE `folders`"
				+" ADD PRIMARY KEY (`id`);";
		
		String indicesTags = "ALTER TABLE `tags`"
				+" ADD PRIMARY KEY (`id`);";
		
		//Autoincrement

		String incArticles = "ALTER TABLE `articles`"
		+"MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;";
		
		String incFeeds = "ALTER TABLE `feeds`"
				+"MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;";
		
		String incFolders = "ALTER TABLE `folders`"
				+"MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;";
		
		String incTags = "ALTER TABLE `tags`"
				+"MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;";
		
		//Relaciones
		String relArticles = "ALTER TABLE `articles`"
				+"ADD CONSTRAINT `fk_articles_feeds` FOREIGN KEY (`source`) REFERENCES `feeds` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;";

		String relFeed_folders = "ALTER TABLE `feeds_folders`"
				+"ADD CONSTRAINT `fk_feeds_folders_feeds` FOREIGN KEY (`id_feed`) REFERENCES `feeds` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,"
				+"ADD CONSTRAINT `fk_feeds_folders_folders` FOREIGN KEY (`id_folder`) REFERENCES `folders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;";

		try {
			statement = connection.createStatement();
			
			statement.executeUpdate(tablaFeeds);
			System.out.println("Created table Feeds...");
			
			statement.execute(tablaArticles);
			System.out.println("Created table Articles...");
			
			statement.executeUpdate(tablaFeeds_folders);
			System.out.println("Created table Feeds_folders...");
			
			statement.executeUpdate(tablaFeeds_tags);
			System.out.println("Created table Feeds_tags...");
			
			statement.executeUpdate(tablaFolders);
			System.out.println("Created table Folders...");
			
			statement.executeUpdate(tablaTags);
			System.out.println("Created table Tags...");
			
			statement.executeUpdate(indicesArticles);

			statement.executeUpdate(indicesFeeds);

			statement.executeUpdate(indicesFeeds_folders);

			statement.executeUpdate(indicesFeeds_tags);

			statement.executeUpdate(indicesFolders);

			statement.executeUpdate(indicesTags);

			System.out.println("Created table index...");
			
			statement.executeUpdate(incArticles);

			statement.executeUpdate(incFeeds);

			statement.executeUpdate(incFolders);

			statement.executeUpdate(incTags);

			System.out.println("Created autoincrements...");

			statement.executeUpdate(relArticles);

			statement.executeUpdate(relFeed_folders);
			
			System.out.println("Created relations...");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			try {
				statement.close();
				connection.close();
			
			} catch ( SQLException e ) {
				e.printStackTrace();
			}
		
		}
		
	}

	public int insertFeed(String url, String name) {
		
		Connection conn = getConnection();
		PreparedStatement statement = null;
		int ret = 0;
		
		String sql =  "INSERT INTO feeds ( name, url ) VALUES ( ?, ? );";
		
		try {
			
			statement = conn.prepareStatement( sql );
			statement.setString(1, name);
			statement.setString(2, url);
			ret = statement.executeUpdate();
		
		} catch ( MySQLIntegrityConstraintViolationException e ) {
			
			System.out.println( "La entrada ya existe en la base de datos." );
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
		    try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		return ret;
	}
	
	public int deleteFeed(String name) {
		Connection con = getConnection();
		PreparedStatement statement = null;
		int ret = 0;
		
		String sql = "DELETE FROM feeds WHERE name = ?;";
		
		try {
			
			statement = con.prepareStatement(sql);
			statement.setString(1, name);
			ret = statement.executeUpdate();
		
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
		    try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		return ret;
	}
	
	public void clearDatabase() {
		
		Connection con = getConnection();
		Statement sta = null;
		
		try {
			
			sta = con.createStatement();
			
			sta.executeUpdate( "DELETE FROM feeds;");
			sta.executeUpdate( "DELETE FROM articles;");
			sta.executeUpdate( "DELETE FROM folders;");
			sta.executeUpdate( "DELETE FROM tags;");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try { sta.close(); } catch (SQLException e) { e.printStackTrace(); }
		    try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
	}

	public int asignTag(String feed, String tag) {
		
		Connection con = getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;
		int ret = 0;
		
		String sqlIdFeed = "SELECT id FROM feeds WHERE name = ?;";
		String sqlIdTag = "SELECT id FROM tags WHERE name = ?;";
		
		try {
			statement = con.prepareStatement( sqlIdFeed );
			statement.setString(1, feed);
			rs = statement.executeQuery();
			rs.next();
			int idFeed = rs.getInt("id");
			
			try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
			
			statement = con.prepareStatement(sqlIdTag);
			statement.setString(1, tag);
			
			rs = statement.executeQuery();
			rs.next();
			int idTag = rs.getInt("id");
			try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			
			String assignTag = String.format("INSERT INTO feeds_tags (id_feed, id_tag) VALUES ( %d, %d );", idFeed, idTag );
			ret = statement.executeUpdate( assignTag );
		
		} catch ( MySQLIntegrityConstraintViolationException e ) {
			System.out.println( "La etiqueta ya está asignada a esta fuente de datos.");
		
		} catch(SQLException e) {
			e.printStackTrace();
		
		} finally {
			try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
		    try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		return ret;
	}

	public int deleteTag(String name) {
		int ret = 0;
		Connection con = getConnection();
		PreparedStatement statement = null;
		
		String sql = "DELETE FROM tags WHERE name = ?;";
		
		try {
			statement = con.prepareStatement( sql );
			statement.setString(1, name);
			ret = statement.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
		    try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		return ret;
	}

	public int deallocateTag(String feed, String tag) {
		Connection con = getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;
		int ret = 0;
		
		String sqlIdFeed = "SELECT id FROM feeds WHERE name = ?;";
		String sqlIdTag = "SELECT id FROM tags WHERE name = ?;";
		
		try {
			statement = con.prepareStatement( sqlIdFeed );
			statement.setString(1, feed);
			rs = statement.executeQuery();
			rs.next();
			int idFeed = rs.getInt("id");
			
			try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
			
			statement = con.prepareStatement(sqlIdTag);
			statement.setString(1, tag);	
			rs = statement.executeQuery();
			rs.next();
			
			int idTag = rs.getInt("id");
			try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			
			String deallocateTagSQL = String.format("DELETE FROM feeds_tags WHERE id_feed = %d AND id_tag = %d;", idFeed, idTag );
			ret = statement.executeUpdate( deallocateTagSQL );
		
		} catch(SQLException e) {
			e.printStackTrace();
		
		} finally {
			try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
		    try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		return ret;
	}

	public int putFeedIntoFolder(String feed, String folder) {
		Connection con = getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;
		int ret = 0;
		
		
		String sqlIdFeed = "SELECT id FROM feeds WHERE name = ?;";
		String sqlIdFolder = "SELECT id FROM folders WHERE name = ?;";
		
		try {
			statement = con.prepareStatement(sqlIdFeed);
			statement.setString(1, feed);
			rs = statement.executeQuery();
			rs.next();
			int idFeed = rs.getInt("id");
			
			try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
			
			statement = con.prepareStatement(sqlIdFolder);
			statement.setString(1, folder);
			
			rs = statement.executeQuery();
			rs.next();
			int idFolder = rs.getInt("id");
			try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			
			String putInFolderSQL = String.format("INSERT INTO feeds_folders (id_folder, id_feed) VALUES ( %d, %d);", idFolder, idFeed );
			ret = statement.executeUpdate( putInFolderSQL );
		
		} catch(SQLException e) {
			e.printStackTrace();
		
		} finally {
			try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
		    try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		return ret;
	}
	
	public LinkedList<Feed> getFeedsFromFolder( String folder ) {
		
		Connection con = getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		ArrayList<Integer> feedsIds = new ArrayList<Integer>();
		LinkedList<Feed> feedsList = new LinkedList<Feed>();
		
		try {
			
			String getFolderIdSQL = "SELECT id FROM folders WHERE name = ?;";
			statement = con.prepareStatement( getFolderIdSQL );
			statement.setString(1, folder );
			rs = statement.executeQuery();
			rs.next();
			
			int idFolder = rs.getInt("id");
			
			String getFeedsFromFolderSQL = String.format("SELECT id_feed FROM feeds_folders WHERE id_folder = %d;", idFolder);
			
			try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			
			rs = statement.executeQuery(getFeedsFromFolderSQL);
			
			// Itera sobre todos ids de los feeds de una carpeta.
			while( rs.next() ) {			
				int idFeed = rs.getInt("id_feed");
				feedsIds.add(idFeed);
			}
			
			for( int i = 0; i < feedsIds.size(); i++ ) {
				String getFeedSQL = String.format("SELECT name, url FROM feeds WHERE id = %d;" , feedsIds.get(i) );
				rs = statement.executeQuery(getFeedSQL);
				
				if( rs.next() ) {
					Feed feed = new Feed( rs.getString("name"), rs.getString("url") );
					
					feedsList.add(feed);
				}
			}
		
		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			
			try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
		    try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		return feedsList;
	}

	public ArrayList<Folder> getFolders() {
		Connection con = getConnection();
		Statement statement = null;
		ResultSet rs = null;
		ArrayList<Folder> folders = new ArrayList<Folder>();
		
		try {
			statement = con.createStatement();
			String getFoldersSQL = "SELECT id, name FROM folders;";
			
			rs = statement.executeQuery( getFoldersSQL );
			
			while( rs.next() ) {
				String name = rs.getString("name");
				Folder folder = new Folder( name );
				folders.add(folder);
			}
			
			try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			
			
			try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
		    try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
			
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
		
		return folders;
	}

	public ArrayList<Feed> getUnclassifiedFeeds() {
		ArrayList<Feed> feeds = new ArrayList<Feed>();
		Connection con = getConnection();
		Statement statement = null;
		ResultSet rs = null;
		
		try {
			statement = con.createStatement();
			String sql = "SELECT name, url FROM feeds WHERE id NOT IN ( SELECT id_feed FROM feeds_folders );";
			rs = statement.executeQuery(sql);
		
			while( rs.next() ) {
				Feed f = new Feed( rs.getString("name"), rs.getString("url") );
				feeds.add(f);
			}
			
			try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
		    try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return feeds;
	}
	
	public ArrayList<Feed> getTaggedFeeds( String tag ) {
		Connection con = getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		ArrayList<Integer> feedsIds = new ArrayList<Integer>();
		ArrayList<Feed> feedsList = new ArrayList<Feed>();
		
		try {
			
			String getTagIdSQL = "SELECT id FROM tags WHERE name = ?;";
			statement = con.prepareStatement(getTagIdSQL);
			statement.setString(1, tag);
		
			rs = statement.executeQuery();
			rs.next();
			int idTag = rs.getInt("id");
			
			String getFeedsFromTagSQL = String.format("SELECT id_feed FROM feeds_tags WHERE id_tag = %d;", idTag);
			rs = statement.executeQuery(getFeedsFromTagSQL);
			
			// Itera sobre todos ids de los feeds de una etiquetas.
			while( rs.next() ) {			
				int idFeed = rs.getInt("id_feed");
				feedsIds.add(idFeed);
			}
			
			for( int i = 0; i < feedsIds.size(); i++ ) {
				String getFeedSQL = String.format("SELECT name, url FROM feeds WHERE id = %d;" , feedsIds.get(i) );
				rs = statement.executeQuery(getFeedSQL);
				
				if( rs.next() ) {
					Feed feed = new Feed( rs.getString("name"), rs.getString("url") );
					
					feedsList.add(feed);
				}
			}
		
		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			
			try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
		    try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		
		return feedsList;
		
	}
	
	public ArrayList<Tag> getTags() {
		ArrayList<Tag> tags = new ArrayList<Tag>();
		
		Connection con = getConnection();
		Statement statement = null;
		ResultSet rs = null;
		
		try {
			statement = con.createStatement();
			String getTagsSQL = "SELECT id, name FROM tags;";
			
			rs = statement.executeQuery( getTagsSQL );
			
			while( rs.next() ) {
				String name = rs.getString("name");
				Tag tag = new Tag( name );
				tags.add(tag);
			}
			
			try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
		    try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
			
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
		
		return tags;
	}

	public int insertArticle(String title, String content, String author, String link, String feed, Date date) {
		int res = 0;
		
		Connection con = getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try {
			statement = con.prepareStatement("SELECT id FROM feeds WHERE name = ?;");
			statement.setString( 1, feed);
			
			rs = statement.executeQuery();
			rs.next();
			int source = rs.getInt("id");
			
			statement = con.prepareStatement( "INSERT INTO articles ( title, content, author, readen, link, source, date ) VALUES (?, ?, ?, 0, ?, ?, ?)");
			statement.setString(1, title);
			statement.setString(2, content);
			statement.setString(3, author);
			statement.setString(4, link);
			statement.setInt(5, source);
			statement.setTimestamp(6, new Timestamp(date.getTime()));
			
			res = statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return res;
	}

	public int markAsRead( String title ) {
		int res = 0;
		Connection con = getConnection();
		PreparedStatement sta = null;
		
		try {
			String sql = "UPDATE articles SET readen = 1 WHERE title = ?;";
			sta = con.prepareStatement( sql );
			sta.setString(1, title );
			res = sta.executeUpdate();
			
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
		
		return res;
	}

	public ArrayList<Article> getAllArticles() {
		ArrayList<Article> articles = new ArrayList<Article>();
		Connection con = getConnection();
		PreparedStatement sta = null;
		ResultSet rs = null;
		
		
		try {
			String sql = "SELECT * FROM articles ORDER BY date DESC;";
			sta = con.prepareStatement(sql);
			rs = sta.executeQuery();
			
			while( rs.next() ) {
				String title = rs.getString("title");
				String author = rs.getString("author");
				String content = rs.getString("content");
				Date date = rs.getDate("date");
				int readen = rs.getInt("readen");
				URL url = new URL( rs.getString("link") );
				
				articles.add( new Article( title, author, content, url, readen, date ) );
			}
			
			try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { sta.close(); } catch (SQLException e) { e.printStackTrace(); }
		    try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
			
		} catch (SQLException e ){
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return articles;
	}

	public ArrayList<Article> getArticlesFromFeed(String string) {
		ArrayList<Article> articles = new ArrayList<Article>();
		
		Connection con = getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try {
			statement = con.prepareStatement("SELECT id FROM feeds WHERE name = ?;");
			statement.setString( 1, string );
			
			rs = statement.executeQuery();
			rs.next();
			int source = rs.getInt("id");
			
			statement = con.prepareStatement( "SELECT * FROM articles WHERE source = ?" );
			statement.setInt(1, source );
			
			rs = statement.executeQuery();
			
			while( rs.next() ) {
				String title = rs.getString("title");
				String author = rs.getString("author");
				String content = rs.getString("content");
				Date date = rs.getDate("date");
				int readen = rs.getInt("readen");
				URL url = new URL( rs.getString("link") );
				
				articles.add( new Article( title, author, content, url, readen, date ) );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		
		return articles;
	}
	
	public ArrayList<Feed> getAllFeeds() {
		ArrayList<Feed> feeds = new ArrayList<Feed>();
		
		Connection con = getConnection();
		PreparedStatement sta = null;
		ResultSet rs = null;
		
		try {
			sta = con.prepareStatement( "SELECT name, url FROM feeds;");
			rs = sta.executeQuery();
			
			while( rs.next() ) {
				String name = rs.getString( "name" );
				String link = rs.getString( "url" );
				
				Feed feed = new Feed( name, link );
			
				feeds.add( feed );
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return feeds;
	}
	
	public int disassignFromFolder( String name ) {
		Connection con = getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;
		int ret = 0;
		
		String sqlIdFeed = "SELECT id FROM feeds WHERE name = ?;";
		
		try {
			
			//Sacamos el ID del Feed.
			statement = con.prepareStatement( sqlIdFeed );
			statement.setString(1, name);
			rs = statement.executeQuery();
			rs.next();
			int idFeed = rs.getInt("id");
			
			statement = con.prepareStatement( "DELETE FROM feeds_folders WHERE id_feed = ?" );
			statement.setInt( 1, idFeed );
			
			ret = statement.executeUpdate();
		
		
		} catch(SQLException e) {
			e.printStackTrace();
		
		} 
		
		return ret;
	}
	
	public ArrayList<Article> getArticlesFromFolder( String folder ) {
		
		ArrayList<Article> articles = new ArrayList<Article>();
		
		Connection con = getConnection();
		PreparedStatement sta = null;
		ResultSet rs = null;
		
		String sql = "SELECT a.title, a.author, a.content, a.link, a.readen, a.date "
				+ "FROM folders AS fo "
				+ "INNER JOIN feeds_folders AS ff ON (fo.id = ff.id_folder) "
				+ "INNER JOIN feeds AS f ON (ff.id_feed = f.id) "
				+ "INNER JOIN articles AS a ON (f.id = a.source) "
				+ "WHERE fo.name = ?;";
		
		try {
			sta = con.prepareStatement(sql);
			sta.setString(1, folder);
			rs = sta.executeQuery();
			
			while( rs.next() ) {
				String title = rs.getString("title");
				String author = rs.getString("author");
				String content = rs.getString("content");
				Date date = rs.getDate("date");
				int readen = rs.getInt("readen");
				URL url = new URL( rs.getString("link") );
				
				articles.add( new Article( title, author, content, url, readen, date ) );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		
		return articles;
	}
	
public ArrayList<Article> getArticlesFromTag( String tag ) {
		
		ArrayList<Article> articles = new ArrayList<Article>();
		
		Connection con = getConnection();
		PreparedStatement sta = null;
		ResultSet rs = null;
		
		String sql = "SELECT a.title, a.author, a.content, a.link, a.readen, a.date "
				+ "FROM tags AS t "
				+ "INNER JOIN feeds_tags AS ft ON (t.id = ft.id_tag) "
				+ "INNER JOIN feeds AS f ON (ft.id_feed = f.id) "
				+ "INNER JOIN articles AS a ON (f.id = a.source) "
				+ "WHERE t.name = ?;";
		
		try {
			sta = con.prepareStatement(sql);
			sta.setString(1, tag);
			rs = sta.executeQuery();
			
			while( rs.next() ) {
				String title = rs.getString("title");
				String author = rs.getString("author");
				String content = rs.getString("content");
				Date date = rs.getDate("date");
				int readen = rs.getInt("readen");
				URL url = new URL( rs.getString("link") );
				
				articles.add( new Article( title, author, content, url, readen, date ) );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		
		return articles;
	}
}
