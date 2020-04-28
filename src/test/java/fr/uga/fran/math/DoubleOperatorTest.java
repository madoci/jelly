package fr.uga.fran.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DoubleOperatorTest {
	
	private Operator op;
	private final double epsilon = 0.00001;
	
	@Before
	public void setUp() {
		op = new DoubleOperator();
	}

	@Test
	public void testZero() {
		double z = (double) op.zero();
		assertEquals(0.0, z, epsilon);
	}

	@Test
	public void testAdd() {
		double d = (double) op.add(12., 3.56);
		assertEquals(15.56, d, epsilon);
		
		d = (double) op.add(-12.5, 3);
		assertEquals(-9.5, d, epsilon);
	}

	@Test
	public void testSubtract() {
		double d = (double) op.subtract(12.5, 3);
		assertEquals(9.5, d, epsilon);
		
		d = (double) op.subtract(12., -3.56);
		assertEquals(15.56, d, epsilon);
	}

	@Test
	public void testMultiply() {
		double d = (double) op.multiply(4, 8.5);
		assertEquals(34., d, epsilon);
		
		d = (double) op.multiply(12., -0.5);
		assertEquals(-6., d, epsilon);
	}

	@Test
	public void testDivide() {
		double d = (double) op.divide(25, 20);
		assertEquals(1.25, d, epsilon);
		
		d = (double) op.divide(12., -0.5);
		assertEquals(-24., d, epsilon);
	}

	@Test
	public void testMean() {
		double d = (double) op.mean(25, 20);
		assertEquals(22.5, d, epsilon);
		
		d = (double) op.mean(12., -12.);
		assertEquals(0.0, d, epsilon);
	}

	@Test
	public void testCompare() {
		assertTrue(op.compare(20, 20.) == 0);
		assertTrue(op.compare(-1.67, 1.67) < 0);
		assertTrue(op.compare(1.67, -1.67) > 0);
		assertTrue(op.compare(0.4, 0.39) > 0);
	}

}
