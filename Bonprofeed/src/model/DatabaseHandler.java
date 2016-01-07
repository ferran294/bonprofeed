package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.exceptions.jdbc4.MySQLDataException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class DatabaseHandler {
	
	public DatabaseHandler() {
		super();
	}
	
	public int createTag( String name ) {
		Connection conn = getConnection();
		Statement statement = null;
		int ret = 0;
		
		String sql = String.format("INSERT INTO tags ( name ) VALUES ( '%s' );", name );
		
		try {
			statement = conn.createStatement();
			ret = statement.executeUpdate(sql);
		
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
		Statement statement = null;
		
		String sql = String.format( "DELETE FROM folders WHERE name = '%s';", name);
		
		try {
			statement = con.createStatement();
			ret = statement.executeUpdate(sql);
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
		Statement sta = null;
		int ret = 0;
		
		String sql = String.format( "UPDATE folders SET name = '%s' WHERE name = '%s';", newName, oldName );
		
		try {
			sta = con.createStatement();
			ret = sta.executeUpdate( sql );
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
		Statement statement = null;
		int ret = 0;
		
		String sql = String.format("INSERT INTO folders ( name ) VALUES ( '%s' );", name );
		
		try {
			statement = conn.createStatement();
			ret = statement.executeUpdate(sql);
		
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
	
	//Set nombre de carpeta (Update y devuelve true si el nombre no existe,si existe devuelve false)
	public boolean setFolderName(int id,String name){
		
		Connection con = getConnection();
		Statement statement = null;
		ResultSet rs = null;
		
		String queryFolderSql = String.format("SELECT id FROM folders WHERE name = %s;",name);
		
		try {
			statement = con.createStatement();
			rs = statement.executeQuery(queryFolderSql);
	
			if (!rs.isBeforeFirst() ) {    //No existe
				String updateFolderSql = String.format("UPDATE folders SET name = %s WHERE id = %d;",name,id);
				statement.executeUpdate(updateFolderSql);
				return true;
			}
		} catch ( SQLException e ) {
			
			e.printStackTrace();
		
		} finally {
			
		    try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		return false;
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
		Statement statement = null;
		int ret = 0;
		
		String sql = String.format("INSERT INTO feeds ( name, url ) VALUES ( '%s', '%s' );", name, url );
		
		try {
			statement = conn.createStatement();
			ret = statement.executeUpdate(sql);
		
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
		Statement statement = null;
		int ret = 0;
		
		String sql = String.format( "DELETE FROM feeds WHERE name = '%s';", name);
		
		try {
			statement = con.createStatement();
			ret = statement.executeUpdate(sql);
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
		Statement statement = null;
		ResultSet rs = null;
		int ret = 0;
		
		String sqlIdFeed = String.format( "SELECT id FROM feeds WHERE name = '%s';", feed);
		String sqlIdTag = String.format( "SELECT id FROM tags WHERE name = '%s';", tag );
		
		try {
			statement = con.createStatement();
			
			rs = statement.executeQuery(sqlIdFeed);
			rs.next();
			int idFeed = rs.getInt("id");
			try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			
			rs = statement.executeQuery(sqlIdTag);
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
		Statement statement = null;
		
		String sql = String.format( "DELETE FROM tags WHERE name = '%s';", name);
		
		try {
			statement = con.createStatement();
			ret = statement.executeUpdate(sql);
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
		Statement statement = null;
		ResultSet rs = null;
		int ret = 0;
		
		String sqlIdFeed = String.format( "SELECT id FROM feeds WHERE name = '%s';", feed);
		String sqlIdTag = String.format( "SELECT id FROM tags WHERE name = '%s';", tag );
		
		try {
			statement = con.createStatement();
			
			rs = statement.executeQuery(sqlIdFeed);
			rs.next();
			int idFeed = rs.getInt("id");
			try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			
			rs = statement.executeQuery(sqlIdTag);
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
		Statement statement = null;
		ResultSet rs = null;
		int ret = 0;
		
		String sqlIdFeed = String.format( "SELECT id FROM feeds WHERE name = '%s';", feed);
		String sqlIdFolder = String.format( "SELECT id FROM folders WHERE name = '%s';", folder );
		
		try {
			statement = con.createStatement();
			
			rs = statement.executeQuery(sqlIdFeed);
			rs.next();
			int idFeed = rs.getInt("id");
			try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			
			rs = statement.executeQuery(sqlIdFolder);
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
	
}
