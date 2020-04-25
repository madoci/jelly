package fr.uga.fran.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class IntegerOperatorsTest {
	private IntegerOperators operators;
	private Integer a, b, c;
	
	@Before
	public void setUp() {
		operators = new IntegerOperators();
		a = 5;
		b = 1;
		c = -2;
	}

	@Test
	public void testAdd() {
		Integer ab = (Integer) operators.add(a, b);
		assertEquals((int) (a+b), (int) ab);

		Integer ac = (Integer) operators.add(a, c);
		assertEquals((int) (a+c), (int) ac);

		Integer bc = (Integer) operators.add(b, c);
		assertEquals((int) (b+c), (int) bc);
	}

	@Test
	public void testSubstract() {
		Integer ab = (Integer) operators.substract(a, b);
		assertEquals((int) (a-b), (int) ab);

		Integer ac = (Integer) operators.substract(a, c);
		assertEquals((int) (a-c), (int) ac);

		Integer bc = (Integer) operators.substract(b, c);
		assertEquals((int) (b-c), (int) bc);
	}

	@Test
	public void testMultiply() {
		Integer ab = (Integer) operators.multiply(a, b);
		assertEquals((int) (a*b), (int) ab);

		Integer ac = (Integer) operators.multiply(a, c);
		assertEquals((int) (a*c), (int) ac);

		Integer bc = (Integer) operators.multiply(b, c);
		assertEquals((int) (b*c), (int) bc);
	}

	@Test
	public void testDivide() {
		Integer ab = (Integer) operators.divide(a, b);
		assertEquals((int) (a/b), (int) ab);

		Integer ac = (Integer) operators.divide(a, c);
		assertEquals((int) (a/c), (int) ac);

		Integer bc = (Integer) operators.divide(b, c);
		assertEquals((int) (b/c), (int) bc);
	}

	@Test
	public void testEquals() {
		assertFalse(operators.equals(a, b));
		assertFalse(operators.equals(a, c));
		assertFalse(operators.equals(b, c));
		
		b = 5;
		assertTrue(operators.equals(a, b));
	}

	@Test
	public void testLessThan() {
		assertFalse(operators.lessThan(a, b));
		assertFalse(operators.lessThan(a, c));
		assertFalse(operators.lessThan(b, c));
		assertTrue(operators.lessThan(b, a));
		assertTrue(operators.lessThan(c, a));
		assertTrue(operators.lessThan(c, b));
		
		b = 5;
		assertFalse(operators.lessThan(a, b));
		assertFalse(operators.lessThan(b, a));
	}

	@Test
	public void testLessEquals() {
		assertFalse(operators.lessEquals(a, b));
		assertFalse(operators.lessEquals(a, c));
		assertFalse(operators.lessEquals(b, c));
		assertTrue(operators.lessEquals(b, a));
		assertTrue(operators.lessEquals(c, a));
		assertTrue(operators.lessEquals(c, b));
		
		b = 5;
		assertTrue(operators.lessEquals(a, b));
		assertTrue(operators.lessEquals(b, a));
	}
	
	@Test
	public void testCompare() {
		assertTrue(operators.compare(a, b) > 0);
		assertTrue(operators.compare(a, c) > 0);
		assertTrue(operators.compare(b, c) > 0);
		assertTrue(operators.compare(b, a) < 0);
		assertTrue(operators.compare(c, a) < 0);
		assertTrue(operators.compare(c, b) < 0);
		
		b = 5;
		assertTrue(operators.compare(a, b) == 0);
		assertTrue(operators.compare(b, a) == 0);
	}

}
