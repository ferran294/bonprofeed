package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Statement;

public class DatabaseHandler {

	public void initialize() {
		
		boolean connected = false;
		Connection connection = null;
		Statement statement = null;
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection( "jdbc:mysql://localhost/bonprofeed", "root", "" );
			
			connected = true;
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		
		} catch ( SQLException e ) {
			connected = false;
		}
		
		if ( !connected ) {
			this.createDatabase();
		}
	}
	
	public Connection getConnection() {
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
	
	
	private void createDatabase() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection( "jdbc:mysql://localhost/", "root", "" );
			Statement statement = connection.createStatement();
			String sql = "CREATE DATABASE bonprofeed";
			
			statement.executeUpdate( sql );
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch ( SQLException e ) {
			e.printStackTrace();
		}	
	}
}
