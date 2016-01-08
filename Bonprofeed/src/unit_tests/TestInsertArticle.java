package unit_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.DatabaseHandler;

public class TestInsertArticle {

	@Test
	public void add_Article() {
		DatabaseHandler dbh = new DatabaseHandler();
		dbh.clearDatabase();
		dbh.insertFeed( "url", "name");
		
		String name = "Programar a estas horas no es sano.";
		String contenido = "Lo dise mi yaya";
		String autor = "DDDD";
		String link = "boladas.es";
		String feed = "name";
		
		int res = dbh.insertArticle( name, contenido, autor, link, feed );
		
		assertEquals( 1, res );	
		
	}

}
