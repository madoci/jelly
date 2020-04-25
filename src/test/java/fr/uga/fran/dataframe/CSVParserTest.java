package fr.uga.fran.dataframe;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import fr.uga.fran.dataframe.CSVParser;
import fr.uga.fran.dataframe.InvalidCSVFormatException;

public class CSVParserTest {

	@Test(expected = FileNotFoundException.class)
	public void testFileNotFound() throws Exception {
		new CSVParser("notanexistingfile.csv");
	}
	
	@Test(expected = InvalidCSVFormatException.class)
	public void testMissingQuote() throws Exception {
		CSVParser parser = new CSVParser("src/test/resources/missingquote.csv");
		while (parser.readLine() != null);
	}
	
	@Test
	public void testCorrectCSVFile() throws Exception {
		CSVParser parser = new CSVParser("src/test/resources/small.csv");
		
		Object[] line;
		
		// Labels
		line = parser.readLine();
		assertEquals("Année", (String) line[0]);
		assertEquals("Constructeur", (String) line[1]);
		assertEquals("Modèle", (String) line[2]);
		assertEquals("Description", (String) line[3]);
		assertEquals("Prix", (String) line[4]);
		
		// Ligne 1
		line = parser.readLine();
		assertEquals(1997, (int) line[0]);
		assertEquals("Ford", (String) line[1]);
		assertEquals("E350", (String) line[2]);
		assertEquals("ac, abs, moon", (String) line[3]);
		assertEquals(3000., (double) line[4], 0.005);
		
		// Ligne 2
		line = parser.readLine();
		assertEquals(1999, (int) line[0]);
		assertEquals("Chevy", (String) line[1]);
		assertEquals("Venture \"Extended Edition\"", (String) line[2]);
		assertNull(line[3]);
		assertEquals(4900., (double) line[4], 0.005);
		
		// Ligne 3
		line = parser.readLine();
		assertEquals(1999, (int) line[0]);
		assertEquals("Chevy", (String) line[1]);
		assertEquals("Venture \"Extended Edition, Very Large\"", (String) line[2]);
		assertNull(line[3]);
		assertEquals(5000., (double) line[4], 0.005);
		
		// Ligne 4
		line = parser.readLine();
		assertEquals(1996, (int) line[0]);
		assertEquals("Jeep", (String) line[1]);
		assertEquals("Grand Cherokee", (String) line[2]);
		assertEquals("MUST SELL! air, moon roof, loaded", (String) line[3]);
		assertEquals(4799., (double) line[4], 0.005);
	}

}
