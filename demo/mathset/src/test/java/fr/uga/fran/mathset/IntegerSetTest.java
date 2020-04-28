package fr.uga.fran.mathset;

import static org.junit.Assert.*;

import org.junit.Test;

public class IntegerSetTest {

	@Test
	public void testNotInfinite() {
		Integer i[] = { 1, 2, 6, 0, -3, 7 };
		Integer j[] = { 4, 1, 7, 3 };
		IntegerSet a = new IntegerSet(i);
		IntegerSet b = new IntegerSet(j);
		
		IntegerSet union = a.union(b);
		
		assertTrue(union.contains(1));
		assertTrue(union.contains(2));
		assertTrue(union.contains(3));
		assertTrue(union.contains(4));
		assertTrue(union.contains(6));
		assertTrue(union.contains(7));
		assertTrue(union.contains(-3));
		assertFalse(union.contains(5));
		assertFalse(union.contains(8));
		
		IntegerSet inter = a.intersection(b);
		
		assertTrue(inter.contains(1));
		assertTrue(inter.contains(7));
		assertFalse(inter.contains(2));
		assertFalse(inter.contains(3));
		assertFalse(inter.contains(4));
		assertFalse(inter.contains(6));
		assertFalse(inter.contains(-3));
		assertFalse(inter.contains(5));
		assertFalse(inter.contains(8));
	}

}
