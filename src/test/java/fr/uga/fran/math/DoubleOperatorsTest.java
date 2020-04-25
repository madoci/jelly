package fr.uga.fran.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DoubleOperatorsTest {
	private DoubleOperators operators;
	private Double a, b, c;
	
	@Before
	public void setUp() {
		operators = new DoubleOperators();
		a = 5.31;
		b = 1.9;
		c = -2.66;
	}

	@Test
	public void testAdd() {
		Double ab = (Double) operators.add(a, b);
		assertEquals((double) (a+b), (double) ab, 0.00001);

		Double ac = (Double) operators.add(a, c);
		assertEquals((double) (a+c), (double) ac, 0.00001);

		Double bc = (Double) operators.add(b, c);
		assertEquals((double) (b+c), (double) bc, 0.00001);
	}

	@Test
	public void testSubstract() {
		Double ab = (Double) operators.substract(a, b);
		assertEquals((double) (a-b), (double) ab, 0.00001);

		Double ac = (Double) operators.substract(a, c);
		assertEquals((double) (a-c), (double) ac, 0.00001);

		Double bc = (Double) operators.substract(b, c);
		assertEquals((double) (b-c), (double) bc, 0.00001);
	}

	@Test
	public void testMultiply() {
		Double ab = (Double) operators.multiply(a, b);
		assertEquals((double) (a*b), (double) ab, 0.00001);

		Double ac = (Double) operators.multiply(a, c);
		assertEquals((double) (a*c), (double) ac, 0.00001);

		Double bc = (Double) operators.multiply(b, c);
		assertEquals((double) (b*c), (double) bc, 0.00001);
	}

	@Test
	public void testDivide() {
		Double ab = (Double) operators.divide(a, b);
		assertEquals((double) (a/b), (double) ab, 0.00001);

		Double ac = (Double) operators.divide(a, c);
		assertEquals((double) (a/c), (double) ac, 0.00001);

		Double bc = (Double) operators.divide(b, c);
		assertEquals((double) (b/c), (double) bc, 0.00001);
	}

	@Test
	public void testEquals() {
		assertFalse(operators.equals(a, b));
		assertFalse(operators.equals(a, c));
		assertFalse(operators.equals(b, c));
		
		b = 5.31;
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
		
		b = 5.31;
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
		
		b = 5.31;
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
		
		b = 5.31;
		assertTrue(operators.compare(a, b) == 0);
		assertTrue(operators.compare(b, a) == 0);
	}

}
