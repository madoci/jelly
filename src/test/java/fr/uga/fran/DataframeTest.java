package fr.uga.fran;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;


public class DataframeTest {

	@Test
	public void testArrayConstructor() {
		String labels[] = { "Surname", "Name", "Age" };
		String col1[] = { "A", "B", "C" };
		String col2[] = { "Denise", "John Dorian" };
		Integer col3[] = { 46, 28, 61 };
		Double col4[] = { 1.05, -2.7, 32.45 };

		Dataframe data = new Dataframe(labels, col1, col2, col3, col4);

		// Check types
		assertEquals(String.class, data.getType(0));
		assertEquals(String.class, data.getType(1));
		assertEquals(Integer.class, data.getType(2));
		assertEquals(Double.class, data.getType(3));

		// Check labels
		for (int i=0; i<3; i++) {
			assertEquals(labels[i], data.getLabel(i));
		}
		assertEquals("", data.getLabel(3));

		// Check each element of each row
		for (int i=0; i<3; i++) {
			String surname = (String) data.get(i, 0);
			String name = (String) data.get(i, 1);
			Integer age = (Integer) data.get(i, 2);
			Double unknown = (Double) data.get(i, 3);

			if (i < col1.length) {
				assertEquals(col1[i], surname);
			} else {
				assertNull(surname);
			}

			if (i < col2.length) {
				assertEquals(col2[i], name);
			} else {
				assertNull(name);
			}

			if (i < col3.length) {
				assertEquals(col3[i], age);
			} else {
				assertNull(age);
			}

			if (i < col4.length) {
				assertEquals(col4[i], unknown);
			} else {
				assertNull(unknown);
			}
		}
	}

	@Test
	public void testCSVConstructor() throws Exception {
		Dataframe data = new Dataframe("src/test/resources/small.csv");

		// Check types
		assertEquals(Integer.class, data.getType(0));
		assertEquals(String.class, data.getType(1));
		assertEquals(String.class, data.getType(2));
		assertEquals(String.class, data.getType(3));
		assertEquals(Double.class, data.getType(4));

		// Check labels
		assertEquals("Année", (String) data.getLabel(0));
		assertEquals("Constructeur", (String) data.getLabel(1));
		assertEquals("Modèle", (String) data.getLabel(2));
		assertEquals("Description", (String) data.getLabel(3));
		assertEquals("Prix", (String) data.getLabel(4));

		// Check row 1
		assertEquals(1997, (int) data.get(0, 0));
		assertEquals("Ford", (String) data.get(0, 1));
		assertEquals("E350", (String) data.get(0, 2));
		assertEquals("ac, abs, moon", (String) data.get(0, 3));
		assertEquals(3000., (double) data.get(0, 4), 0.005);

		// Check row 2
		assertEquals(1999, (int) data.get(1, 0));
		assertEquals("Chevy", (String) data.get(1, 1));
		assertEquals("Venture \"Extended Edition\"", (String) data.get(1, 2));
		assertNull(data.get(1, 3));
		assertEquals(4900., (double) data.get(1, 4), 0.005);

		// Check row 3
		assertEquals(1999, (int) data.get(2, 0));
		assertEquals("Chevy", (String) data.get(2, 1));
		assertEquals("Venture \"Extended Edition, Very Large\"", (String) data.get(2, 2));
		assertNull(data.get(2, 3));
		assertEquals(5000., (double) data.get(2, 4), 0.005);

		// Check row 4
		assertEquals(1996, (int) data.get(3, 0));
		assertEquals("Jeep", (String) data.get(3, 1));
		assertEquals("Grand Cherokee", (String) data.get(3, 2));
		assertEquals("MUST SELL! air, moon roof, loaded", (String) data.get(3, 3));
		assertEquals(4799., (double) data.get(3, 4), 0.005);
	}

	@Test
	public void testAddRow() throws Exception {
		Dataframe data = new Dataframe("src/test/resources/small.csv");

		int year = 2020;
		String make = "Peugeot";
		String model = "205";

		Object[] row = { year, make, model };

		data.addRow(row);

		assertEquals(year, (int) data.get(4, 0));
		assertEquals(make, (String) data.get(4, 1));
		assertEquals(model, (String) data.get(4, 2));
		assertNull(data.get(4, 3));
		assertNull(data.get(4, 4));
	}

	@Test(expected = InvalidCSVFormatException.class)
	public void testEmptyFile() throws Exception {
		new Dataframe("src/test/resources/empty.csv");
	}

	@Test(expected = InvalidCSVFormatException.class)
	public void testInvalidNumFields() throws Exception {
		new Dataframe("src/test/resources/missingfields.csv");
	}

	@Test(expected = InvalidCSVFormatException.class)
	public void testEmptyColumn() throws Exception {
		new Dataframe("src/test/resources/emptycolumn.csv");
	}

	@Test
	public void testSelectLines() throws Exception {
		Dataframe data = new Dataframe("src/test/resources/small.csv");

		int[] i = {1, 2};
		Dataframe newData = data.selectRows(i);
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
	public void testSelectColumns() throws Exception {
		Dataframe data = new Dataframe("src/test/resources/small.csv");

		String[] i = {"Constructeur", "Description"};
		Dataframe newData = data.selectColumns(i);
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
	public void testCrossSelect() throws Exception {
		Dataframe data = new Dataframe("src/test/resources/small.csv");

		int[] i = {2, 3};
		String[] j = {"Constructeur", "Description"};
		Dataframe newData = data.crossSelect(i, j);
		assertEquals("Chevy", (String) newData.get(0,0));
		assertNull(newData.get(0, 1));

		assertEquals("Jeep", (String) newData.get(1, 0));
		assertEquals("MUST SELL! air, moon roof, loaded", (String) newData.get(1, 1));
	}

	@Test
	public void testSelectEquals() throws Exception {
		Dataframe data = new Dataframe("src/test/resources/small.csv");
		
		data = data.selectEquals("Constructeur", (String) "Chevy");
		assertEquals(1999, (int) data.get(0, 0));
		assertEquals("Chevy", (String) data.get(0, 1));
		assertEquals("Venture \"Extended Edition\"", (String) data.get(0,  2));
		assertNull(data.get(0, 3));
		assertEquals(4900.00, (double) data.get(0, 4), 0.005);
		
		assertEquals(1999, (int) data.get(1, 0));
		assertEquals("Chevy", (String) data.get(1, 1));
		assertEquals("Venture \"Extended Edition, Very Large\"", (String) data.get(1,  2));
		assertNull(data.get(1, 3));
		assertEquals(5000.00, (double) data.get(1, 4), 0.005);
	}
	
	@Test
	public void testSelectNotEquals() throws Exception {
		Dataframe data = new Dataframe("src/test/resources/small.csv");
		
		data = data.selectNotEquals("Constructeur", (String) "Chevy");
		assertEquals("Ford", (String) data.get(0, 1));
		assertEquals("Jeep", (String) data.get(1, 1));
	}
	
	@Test
	public void testSelectGreaterThan() throws Exception {
		Dataframe data = new Dataframe("src/test/resources/small.csv");
		
		data = data.selectGreaterThan("Prix", Double.valueOf(3000.00), true);
		assertEquals(4900.0, (double) data.get(0, 4), 0.005);
		assertEquals(5000.0, (double) data.get(1, 4), 0.005);
		assertEquals(4799.0, (double) data.get(2, 4), 0.005);
		
		data = data.selectGreaterThan("Constructeur", "Ci", false);
		assertEquals("Jeep", (String) data.get(0, 1));		
	}
	
	@Test
	public void testSelectLessThan() throws Exception {
		Dataframe data = new Dataframe("src/test/resources/small.csv");
		
		data = data.selectLessThan("Prix", Double.valueOf(4799.00), false);
		assertEquals(3000.0, (double) data.get(0, 4), 0.005);
		assertEquals(4799.0, (double) data.get(1, 4), 0.005);
		
		data = data.selectGreaterThan("Constructeur", "Ford", true);
		assertEquals("Jeep", (String) data.get(0,  1));
	}
	
	@Test
	public void testAccessByLabel() throws Exception {
		Dataframe data = new Dataframe("src/test/resources/small.csv");

		assertEquals("Ford", data.get(0, "Constructeur"));
		assertEquals(1996, data.get(3, "Année"));

		assertEquals(Integer.class, data.getType("Année"));
		assertEquals(Double.class, data.getType("Prix"));
		assertEquals(String.class, data.getType("Modèle"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAccessByWrongLabel() throws Exception {
		Dataframe data = new Dataframe("src/test/resources/small.csv");

		data.get(0, "Wrong Label");
	}

	@Test
	public void testCount() throws Exception {
		Dataframe data = new Dataframe("src/test/resources/small.csv");

		assertEquals(4, data.rowCount());
		assertEquals(5, data.columnCount());
	}

	@Test
	public void testDefaultViewer() throws Exception {
		Dataframe data = new Dataframe("src/test/resources/small.csv");
		TabularDataframeViewer viewer = new TabularDataframeViewer();

		assertEquals(viewer.view(data), data.view());
		assertEquals(viewer.head(data), data.head());
		assertEquals(viewer.tail(data), data.tail());
		assertEquals(viewer.head(data, 0), data.head(0));
		assertEquals(viewer.tail(data, 2), data.tail(2));
	}

	@Test
	public void testSetViewer() throws Exception {
		Dataframe data = new Dataframe("src/test/resources/small.csv");
		TabularDataframeViewer viewer = new TabularDataframeViewer();
		viewer.setSeparator(" | ");
		viewer.setSampleSize(3);
		data.setViewer(viewer);

		assertEquals(viewer.view(data), data.view());
		assertEquals(viewer.head(data), data.head());
		assertEquals(viewer.tail(data), data.tail());
		assertEquals(viewer.head(data, 0), data.head(0));
		assertEquals(viewer.tail(data, 2), data.tail(2));
		assertEquals(viewer.view(data), data.toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArrayConstructor() throws Exception {
		String labels[] = { "Surname", "Name", "Age" };
		String col1[] = { "A", "B", "C" };
		String col2[] = { "Denise", "John Dorian" };

		int a = 46;
		int b = 28;
		double c = 61.5;
		Object col3[] = { a, b, c };

		new Dataframe(labels, col1, col2, col3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalAddRow() throws Exception {
		String labels[] = { "Surname", "Name", "Age" };
		String col1[] = { "A", "B", "C" };
		String col2[] = { "Denise", "John Dorian" };
		Integer col3[] = { 46, 28, 61 };
		Double col4[] = { 1.05, -2.7, 32.45 };

		Dataframe data = new Dataframe(labels, col1, col2, col3, col4);

		String a = "D";
		int b = 2;
		int c = 40;
		double d = 12.4;

		Object row[] = { a, b, c, d };

		data.addRow(row);
	}

}
