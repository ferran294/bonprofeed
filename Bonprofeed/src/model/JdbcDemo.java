package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conexion = DriverManager.getConnection( "jdbc:mysql://localhost/bonprofeed", "root", "" );
			System.out.println( conexion.getSchema() );
		} catch ( Exception e ) {
			System.out.println("Caca");
		}
		
	}

}
