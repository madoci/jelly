package fr.uga.fran.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class IntegerOperatorTest {

	private Operator op;
	private final double epsilon = 0.00001;
	
	@Before
	public void setUp() {
		op = new IntegerOperator();
	}

	@Test
	public void testZero() {
		int z = (int) op.zero();
		assertEquals(0, z);
	}

	@Test
	public void testOne() {
		int o = (int) op.one();
		assertEquals(1, o);
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
		double d = (double) op.divide(25, 20);
		assertEquals(1.25, d, epsilon);
		
		d = (double) op.divide(12, -1);
		assertEquals(-12., d, epsilon);
	}

	@Test
	public void testMean() {
		double d = (double) op.mean(25, 20);
		assertEquals(22.5, d, epsilon);
		
		d = (double) op.mean(12, -12);
		assertEquals(0.0, d, epsilon);
	}

	@Test
	public void testCompare() {
		assertTrue(op.compare(20, 20) == 0);
		assertTrue(op.compare(-1, 1) < 0);
		assertTrue(op.compare(1, -1) > 0);
		assertTrue(op.compare(2, 0) > 0);
	}

}
