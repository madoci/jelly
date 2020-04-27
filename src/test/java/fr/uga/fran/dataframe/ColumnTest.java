package fr.uga.fran.dataframe;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.uga.fran.dataframe.Column;

public class ColumnTest {
	
	@Test
	public void testAdd() throws Exception {
		Column column = new Column(Integer.class, "Test");
		
		column.add(0);
		column.add(123456);
		column.add(null);
		column.add(-2);
		
		Integer a = (Integer) column.get(0);
		Integer b = (Integer) column.get(1);
		Integer c = (Integer) column.get(2);
		Integer d = (Integer) column.get(3);
		
		assertEquals(0, (int) a);
		assertEquals(123456, (int) b);
		assertNull(c);
		assertEquals(-2, (int) d);
	}
	
	@Test
	public void testGetType() throws Exception {
		Column column = new Column(Double.class, "Test");
		assertEquals(Double.class, column.getType());
		
		column = new Column(String.class, "Test");
		assertEquals(String.class, column.getType());
		
		column = new Column(Column.class, "Test");
		assertEquals(Column.class, column.getType());
	}
	
	@Test
	public void testGetLabel() throws Exception {
		Column column = new Column(Integer.class, "Test");
		assertEquals("Test", column.getLabel());
		
		column = new Column(String.class, "");
		assertEquals("", column.getLabel());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalAdd() throws Exception {
		Column column = new Column(Integer.class, "Test");
		
		column.add("not allowed");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullType() throws Exception {
		new Column(null, "");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullLabel() throws Exception {
		new Column(Integer.class, null);
	}

}
