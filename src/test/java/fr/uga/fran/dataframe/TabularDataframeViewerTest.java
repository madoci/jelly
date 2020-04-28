package fr.uga.fran.dataframe;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.uga.fran.dataframe.Dataframe;
import fr.uga.fran.dataframe.TabularDataframeViewer;

public class TabularDataframeViewerTest {
	private Dataframe data;
	private TabularDataframeViewer viewer;
	
	@Before
	public void setUp() throws Exception {
		data = new Dataframe("src/test/resources/small.csv");
		viewer = new TabularDataframeViewer();
	}

	@Test
	public void testView() {
		String expected = 	" Année Constructeur Modèle                                 Description                       Prix   " + System.lineSeparator() +
							"  1997 Ford         E350                                   ac, abs, moon                     3000.0 " + System.lineSeparator() +
							"  1999 Chevy        Venture \"Extended Edition\"                                               4900.0 " + System.lineSeparator() +
							"  1999 Chevy        Venture \"Extended Edition, Very Large\"                                   5000.0 " + System.lineSeparator() +
							"  1996 Jeep         Grand Cherokee                         MUST SELL! air, moon roof, loaded 4799.0 " + System.lineSeparator();
		
		assertEquals(expected, viewer.view(data));
	}

	@Test
	public void testHead() {
		String expected = 	" Année Constructeur Modèle                                 Description                       Prix   " + System.lineSeparator() +
							"  1997 Ford         E350                                   ac, abs, moon                     3000.0 " + System.lineSeparator() +
							"  1999 Chevy        Venture \"Extended Edition\"                                               4900.0 " + System.lineSeparator() +
							"  1999 Chevy        Venture \"Extended Edition, Very Large\"                                   5000.0 " + System.lineSeparator() +
							"  1996 Jeep         Grand Cherokee                         MUST SELL! air, moon roof, loaded 4799.0 " + System.lineSeparator();
		
		assertEquals(expected, viewer.head(data));
	}

	@Test
	public void testHeadNum() {
		String expected = 	" Année Constructeur Modèle                                 Description   Prix   " + System.lineSeparator() +
							"  1997 Ford         E350                                   ac, abs, moon 3000.0 " + System.lineSeparator() +
							"  1999 Chevy        Venture \"Extended Edition\"                           4900.0 " + System.lineSeparator() +
							"  1999 Chevy        Venture \"Extended Edition, Very Large\"               5000.0 " + System.lineSeparator();
		
		assertEquals(expected, viewer.head(data, 3));
	}

	@Test
	public void testTail() {
		String expected = 	" Année Constructeur Modèle                                 Description                       Prix   " + System.lineSeparator() +
							"  1997 Ford         E350                                   ac, abs, moon                     3000.0 " + System.lineSeparator() +
							"  1999 Chevy        Venture \"Extended Edition\"                                               4900.0 " + System.lineSeparator() +
							"  1999 Chevy        Venture \"Extended Edition, Very Large\"                                   5000.0 " + System.lineSeparator() +
							"  1996 Jeep         Grand Cherokee                         MUST SELL! air, moon roof, loaded 4799.0 " + System.lineSeparator();
		
		assertEquals(expected, viewer.tail(data));
	}

	@Test
	public void testTailNum() {
		String expected = 	" Année Constructeur Modèle         Description                       Prix   " + System.lineSeparator() +
							"  1996 Jeep         Grand Cherokee MUST SELL! air, moon roof, loaded 4799.0 " + System.lineSeparator();
		
		assertEquals(expected, viewer.tail(data, 1));
	}

	@Test
	public void testSetSampleSize() {
		viewer.setSampleSize(1);
		
		String expectedHead = 	" Année Constructeur Modèle Description   Prix   " + System.lineSeparator() +
								"  1997 Ford         E350   ac, abs, moon 3000.0 " + System.lineSeparator();
		
		assertEquals(expectedHead, viewer.head(data));
		
		String expectedTail = 	" Année Constructeur Modèle         Description                       Prix   " + System.lineSeparator() +
								"  1996 Jeep         Grand Cherokee MUST SELL! air, moon roof, loaded 4799.0 " + System.lineSeparator();
		
		assertEquals(expectedTail, viewer.tail(data));
	}

	@Test
	public void testSetSeparator() {
		viewer.setSeparator(" | ");
		
		String expected = 	" | Année | Constructeur | Modèle                                 | Description                       | Prix   | " + System.lineSeparator() +
							" |  1997 | Ford         | E350                                   | ac, abs, moon                     | 3000.0 | " + System.lineSeparator() +
							" |  1999 | Chevy        | Venture \"Extended Edition\"             |                                   | 4900.0 | " + System.lineSeparator() +
							" |  1999 | Chevy        | Venture \"Extended Edition, Very Large\" |                                   | 5000.0 | " + System.lineSeparator() +
							" |  1996 | Jeep         | Grand Cherokee                         | MUST SELL! air, moon roof, loaded | 4799.0 | " + System.lineSeparator();

		assertEquals(expected, viewer.view(data));
	}

}
