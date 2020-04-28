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
	
	@Test
	public void testInfinite() {
		IntegerSet a = IntegerSet.full();
		a.remove(0);
		Integer j[] = { 4, 1, 7, 3 ,0 };
		IntegerSet b = new IntegerSet(j);
		b = b.complement();
		
		IntegerSet union = a.union(b);
		
		assertFalse(union.contains(0));
		assertTrue(union.contains(1));
		assertTrue(union.contains(5));
		assertTrue(union.contains(10));
		assertTrue(union.contains(100));
		assertTrue(union.contains(-1));
		
		IntegerSet inter = a.intersection(b);
		
		assertFalse(inter.contains(0));
		assertFalse(inter.contains(1));
		assertTrue(inter.contains(2));
		assertFalse(inter.contains(3));
		assertFalse(inter.contains(4));
		assertTrue(inter.contains(5));
		assertTrue(inter.contains(6));
		assertFalse(inter.contains(7));
		assertTrue(inter.contains(8));
		assertTrue(inter.contains(9));
		assertTrue(inter.contains(10));
		
	}

	@Test
	public void testFiniteInfinite() {
		IntegerSet a = IntegerSet.full();
		a.remove(0);
		Integer j[] = { 4, 1, 7, 3 };
		IntegerSet b = new IntegerSet(j);
		
		IntegerSet union = a.union(b);
		
		assertFalse(union.contains(0));
		assertTrue(union.contains(1));
		assertTrue(union.contains(5));
		assertTrue(union.contains(10));
		assertTrue(union.contains(100));
		assertTrue(union.contains(-1));
		
		IntegerSet inter = a.intersection(b);
		
		assertFalse(inter.contains(0));
		assertTrue(inter.contains(1));
		assertFalse(inter.contains(2));
		assertTrue(inter.contains(3));
		assertTrue(inter.contains(4));
		assertFalse(inter.contains(5));
		assertFalse(inter.contains(6));
		assertTrue(inter.contains(7));
		assertFalse(inter.contains(8));
		assertFalse(inter.contains(9));
		assertFalse(inter.contains(10));
	}
}
