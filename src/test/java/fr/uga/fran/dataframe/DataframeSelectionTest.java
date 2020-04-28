package fr.uga.fran.dataframe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import fr.uga.fran.dataframe.DataframeSelection;

public class DataframeSelectionTest {
	private Dataframe data;
	private DataframeSelection selection;
	
	@Before
	public void setUp() throws Exception {
		data = new Dataframe("src/test/resources/small.csv");
		selection = new DataframeSelection(data);
	}

	@Test
	public void testRow() {
		int[] rows = {1, 2};
		Dataframe newData = selection.row(rows);
		
		assertEquals(1999, (int) newData.get(0, 0));
		assertEquals("Chevy", (String) newData.get(0, 1));
		assertEquals("Venture \"Extended Edition\"", (String) newData.get(0, 2));
		assertEquals("", newData.get(0, 3));
		assertEquals(4900.00, (double) newData.get(0, 4), 0.005);

		assertEquals(1999, (int) newData.get(1, 0));
		assertEquals("Chevy", (String) newData.get(1, 1));
		assertEquals("Venture \"Extended Edition, Very Large\"", (String) newData.get(1, 2));
		assertNull(newData.get(1, 3));
		assertEquals(5000.00, (double) newData.get(1, 4), 0.005);
	}
	
	@Test
	public void testColumnIndex() throws Exception {
		int[] columns = {1, 3};
		Dataframe newData = selection.column(columns);
		
		assertEquals("Ford", (String) newData.get(0, 0));
		assertEquals("ac, abs, moon", (String) newData.get(0, 1));

		assertEquals("Chevy", (String) newData.get(1, 0));
		assertEquals("", newData.get(1, 1));

		assertEquals("Chevy", (String) newData.get(2, 0));
		assertNull(newData.get(2, 1));

		assertEquals("Jeep", (String) newData.get(3, 0));
		assertEquals("MUST SELL! air, moon roof, loaded", (String) newData.get(3, 1));
	}
	
	@Test
	public void testColumnLabel() throws Exception {
		String[] labels = {"Constructeur", "Description"};
		Dataframe newData = selection.column(labels);
		
		assertEquals("Ford", (String) newData.get(0, 0));
		assertEquals("ac, abs, moon", (String) newData.get(0, 1));

		assertEquals("Chevy", (String) newData.get(1, 0));
		assertEquals("", newData.get(1, 1));

		assertEquals("Chevy", (String) newData.get(2, 0));
		assertNull(newData.get(2, 1));

		assertEquals("Jeep", (String) newData.get(3, 0));
		assertEquals("MUST SELL! air, moon roof, loaded", (String) newData.get(3, 1));
	}

	@Test
	public void testCrossIndex() throws Exception {
		int[] rows = {2, 3};
		int[] columns = {1, 3};
		Dataframe newData = selection.cross(rows, columns);
		
		assertEquals("Chevy", (String) newData.get(0,0));
		assertNull(newData.get(0, 1));

		assertEquals("Jeep", (String) newData.get(1, 0));
		assertEquals("MUST SELL! air, moon roof, loaded", (String) newData.get(1, 1));
	}

	@Test
	public void testCrossLabel() throws Exception {
		int[] rows = {2, 3};
		String[] labels = {"Constructeur", "Description"};
		Dataframe newData = selection.cross(rows, labels);
		
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
		assertEquals("", newData.get(0, 3));
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
	
	@Test
	public void testViewer() throws Exception {
		TabularDataframeViewer viewer = new TabularDataframeViewer();
		viewer.setSeparator("|");
		data.setViewer(viewer);
		
		int rows[] = { 0 };
		int columns[] = { 0, 1 };
		Dataframe newData = selection.cross(rows, columns);
		
		String expected = 	"|Année|Constructeur|" + System.lineSeparator() +
							"| 1997|Ford        |" + System.lineSeparator();
		
		assertEquals(expected, newData.toString());
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
