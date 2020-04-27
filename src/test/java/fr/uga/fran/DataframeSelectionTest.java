package fr.uga.fran;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class DataframeSelectionTest {
	private DataframeSelection selection;
	
	@Before
	public void setUp() throws Exception {
		Dataframe data = new Dataframe("src/test/resources/small.csv");
		selection = new DataframeSelection(data);
	}

	@Test
	public void testRow() {
		int[] i = {1, 2};
		Dataframe newData = selection.row(i);
		
		assertEquals(1999, (int) newData.get(0, 0));
		assertEquals("Chevy", (String) newData.get(0, 1));
		assertEquals("Venture \"Extended Edition\"", (String) newData.get(0, 2));
		assertNull(newData.get(0, 3));
		assertEquals(4900.00, (double) newData.get(0, 4), 0.005);

		assertEquals(1999, (int) newData.get(1, 0));
		assertEquals("Chevy", (String) newData.get(1, 1));
		assertEquals("Venture \"Extended Edition, Very Large\"", (String) newData.get(1, 2));
		assertNull(newData.get(0, 3));
		assertEquals(5000.00, (double) newData.get(1, 4), 0.005);
	}
	
	@Test
	public void testColumnIndex() throws Exception {
		int[] i = {1, 3};
		Dataframe newData = selection.column(i);
		
		assertEquals("Ford", (String) newData.get(0, 0));
		assertEquals("ac, abs, moon", (String) newData.get(0, 1));

		assertEquals("Chevy", (String) newData.get(1, 0));
		assertNull(newData.get(1, 1));

		assertEquals("Chevy", (String) newData.get(2, 0));
		assertNull(newData.get(2, 1));

		assertEquals("Jeep", (String) newData.get(3, 0));
		assertEquals("MUST SELL! air, moon roof, loaded", (String) newData.get(3, 1));
	}
	
	@Test
	public void testColumnLabel() throws Exception {
		String[] i = {"Constructeur", "Description"};
		Dataframe newData = selection.column(i);
		
		assertEquals("Ford", (String) newData.get(0, 0));
		assertEquals("ac, abs, moon", (String) newData.get(0, 1));

		assertEquals("Chevy", (String) newData.get(1, 0));
		assertNull(newData.get(1, 1));

		assertEquals("Chevy", (String) newData.get(2, 0));
		assertNull(newData.get(2, 1));

		assertEquals("Jeep", (String) newData.get(3, 0));
		assertEquals("MUST SELL! air, moon roof, loaded", (String) newData.get(3, 1));
	}

	@Test
	public void testCrossIndex() throws Exception {
		int[] i = {2, 3};
		int[] j = {1, 3};
		Dataframe newData = selection.cross(i, j);
		
		assertEquals("Chevy", (String) newData.get(0,0));
		assertNull(newData.get(0, 1));

		assertEquals("Jeep", (String) newData.get(1, 0));
		assertEquals("MUST SELL! air, moon roof, loaded", (String) newData.get(1, 1));
	}

	@Test
	public void testCrossLabel() throws Exception {
		int[] i = {2, 3};
		String[] j = {"Constructeur", "Description"};
		Dataframe newData = selection.cross(i, j);
		
		assertEquals("Chevy", (String) newData.get(0,0));
		assertNull(newData.get(0, 1));

		assertEquals("Jeep", (String) newData.get(1, 0));
		assertEquals("MUST SELL! air, moon roof, loaded", (String) newData.get(1, 1));
	}
	
	@Test
	public void testSelectionOrder() throws Exception {
		String[] labels = {"Constructeur", "Description", "Année", "Prix", "Modèle"};
		int[] rows = { 0 };
		Dataframe newData = selection.cross(rows, labels);
		
		assertEquals("Ford", newData.get(0, 0));
		assertEquals("ac, abs, moon", newData.get(0, 1));
		assertEquals(1997, (int) newData.get(0, 2));
		assertEquals(3000.0, (double) newData.get(0, 3), 0.0001);
		assertEquals("E350", newData.get(0, 4));
	}
	
	@Test
	public void testEqual() throws Exception {		
		Dataframe newData = selection.equal("Constructeur", "Chevy");
		
		assertEquals(1999, (int) newData.get(0, 0));
		assertEquals("Chevy", (String) newData.get(0, 1));
		assertEquals("Venture \"Extended Edition\"", (String) newData.get(0,  2));
		assertNull(newData.get(0, 3));
		assertEquals(4900.00, (double) newData.get(0, 4), 0.005);
		
		assertEquals(1999, (int) newData.get(1, 0));
		assertEquals("Chevy", (String) newData.get(1, 1));
		assertEquals("Venture \"Extended Edition, Very Large\"", (String) newData.get(1,  2));
		assertNull(newData.get(1, 3));
		assertEquals(5000.00, (double) newData.get(1, 4), 0.005);
	}
	
	@Test
	public void testNotEqual() throws Exception {
		Dataframe newData = selection.notEqual("Constructeur", "Chevy");
		
		assertEquals("Ford", (String) newData.get(0, 1));
		assertEquals("Jeep", (String) newData.get(1, 1));
	}
	
	@Test
	public void testLessThan() throws Exception {
		Dataframe newData = selection.lessThan("Constructeur", "Jeep");
		assertEquals("Ford", (String) newData.get(0,  1));
	}
	
	@Test
	public void testLessEqual() throws Exception {
		Dataframe newData = selection.lessEqual("Prix", 4799.00);
		
		assertEquals(3000.0, (double) newData.get(0, 4), 0.005);
		assertEquals(4799.0, (double) newData.get(1, 4), 0.005);
	}
	
	@Test
	public void testGreaterThan() throws Exception {
		Dataframe newData = selection.greaterThan("Prix", 3000.);
		
		assertEquals(4900.0, (double) newData.get(0, 4), 0.005);
		assertEquals(5000.0, (double) newData.get(1, 4), 0.005);
		assertEquals(4799.0, (double) newData.get(2, 4), 0.005);
	}
	
	@Test
	public void testGreaterEqual() throws Exception {
		Dataframe newData = selection.greaterEqual("Constructeur", "Jeep");
		
		assertEquals("Jeep", (String) newData.get(0, 1));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalLabel() throws Exception {
		String labels[] = { "Année", "Carburant" };
		
		selection.column(labels);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalCompare() throws Exception {
		String labels[] = { "Surname", "Name", "Age" };
		String col1[] = { "A", "B", "C" };
		String col2[] = { "Denise", "John Dorian" };
		Integer col3[] = { null, 28, 61 };
		Double col4[] = { 1.05, -2.7, 32.45 };
		
		Dataframe data = new Dataframe(labels, col1, col2, col3, col4);
		selection = new DataframeSelection(data);
		
		selection.lessThan("Age", 30.0);
	}

}
