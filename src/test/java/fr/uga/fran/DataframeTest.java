package fr.uga.fran;

import static org.junit.Assert.*;

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
		
		// Vérifie les labels
		for (int i=0; i<3; i++) {
			assertEquals(labels[i], data.getLabel(i));
		}
		assertEquals("", data.getLabel(3));
		
		// Vérifie chaque élément de chaque ligne
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
	
}
