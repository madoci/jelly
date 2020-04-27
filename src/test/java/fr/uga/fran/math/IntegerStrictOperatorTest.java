package fr.uga.fran.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class IntegerStrictOperatorTest {

	private Operator op;
	
	@Before
	public void setUp() {
		op = new IntegerStrictOperator();
	}

	@Test
	public void testZero() {
		int z = (int) op.zero();
		assertEquals(0, z);
	}

	@Test
	public void testAdd() {
		int i = (int) op.add(12, 4);
		assertEquals(16, i);
		
		i = (int) op.add(-12, 3);
		assertEquals(-9, i);
	}

	@Test
	public void testSubtract() {
		int i = (int) op.subtract(12, 3);
		assertEquals(9, i);
		
		i = (int) op.subtract(12, -4);
		assertEquals(16, i);
	}

	@Test
	public void testMultiply() {
		int i = (int) op.multiply(4, 8);
		assertEquals(32, i);
		
		i = (int) op.multiply(12., -1);
		assertEquals(-12, i);
	}

	@Test
	public void testDivide() {
		int i = (int) op.divide(25, 20);
		assertEquals(1, i);
		
		i = (int) op.divide(12, -1);
		assertEquals(-12, i);
	}

	@Test
	public void testMean() {
		int i = (int) op.mean(25, 20);
		assertEquals(22, i);
		
		i = (int) op.mean(12, -12);
		assertEquals(0, i);
	}

	@Test
	public void testCompare() {
		assertTrue(op.compare(20, 20) == 0);
		assertTrue(op.compare(-1, 1) < 0);
		assertTrue(op.compare(1, -1) > 0);
		assertTrue(op.compare(2, 0) > 0);
	}

}
