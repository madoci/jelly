package fr.uga.fran.math;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class NumberArrayStatisticsTest {
	
	private NumberArrayStatistics stats;
	private final double epsilon = 0.00001;
	
	@Before
	public void setUp() {
		stats = new NumberArrayStatistics();
	}

	@Test
	public void testArgmin() {
		Integer array1[] = { 21, 7, null, 189, 1000000, 35, 1 };
		assertEquals(6, stats.argmin(array1));
		
		Integer array2[] = { 21, 0, 189, -126, 35, -2 };
		assertEquals(3, stats.argmin(array2));
		
		Double array3[] = { 0.4, 10.7, 5.673, 0.399, 0.561 };
		assertEquals(3, stats.argmin(array3));
		
		Double array4[] = { -32921.01, -32921., 5.673, -287.3, 0.561 };
		assertEquals(0, stats.argmin(array4));
	}
	
	@Test
	public void testArgmax() {
		Integer array1[] = { 21, 7, null, 189, 1000000, 35, 1 };
		assertEquals(4, stats.argmax(array1));
		
		Integer array2[] = { 21, 0, 189, -226, 35, -2 };
		assertEquals(2, stats.argmax(array2));
		
		Double array3[] = { 0.4, 10.7, 5.673, 0.399, 0.561 };
		assertEquals(1, stats.argmax(array3));
		
		Double array4[] = { -32921.01, -32921., 5.673, -287.3, 0.561 };
		assertEquals(2, stats.argmax(array4));
	}
	
	@Test
	public void testMin() {
		Integer array1[] = { 21, 7, null, 189, 1000000, 35, 1 };
		Integer min1 = (Integer) stats.min(array1);
		assertEquals((int) array1[6], (int) min1);
		
		Integer array2[] = { 21, 0, 189, -226, 35, -2 };
		Integer min2 = (Integer) stats.min(array2);
		assertEquals((int) array2[3], (int) min2);
		
		Double array3[] = { 0.4, 10.7, 5.673, 0.399, 0.561 };
		Double min3 = (Double) stats.min(array3);
		assertEquals((double) array3[3], (double) min3, epsilon);
		
		Double array4[] = { -32921.01, -32921., 5.673, -287.3, 0.561 };
		Double min4 = (Double) stats.min(array4);
		assertEquals((double) array4[0], (double) min4, epsilon);
	}
	
	@Test
	public void testMax() {
		Integer array1[] = { 21, 7, null, 189, 1000000, 35, 1 };
		Integer max1 = (Integer) stats.max(array1);
		assertEquals((int) array1[4], (int) max1);
		
		Integer array2[] = { 21, 0, 189, -226, 35, -2 };
		Integer max2 = (Integer) stats.max(array2);
		assertEquals((int) array2[2], (int) max2);
		
		Double array3[] = { 0.4, 10.7, 5.673, 0.399, 0.561 };
		Double max3 = (Double) stats.max(array3);
		assertEquals((double) array3[1], (double) max3, epsilon);
		
		Double array4[] = { -32921.01, -32921., 5.673, -287.3, 0.561 };
		Double max4 = (Double) stats.max(array4);
		assertEquals((double) array4[2], (double) max4, epsilon);
	}
	
	@Test
	public void testSum() {
		Integer array1[] = { 12, 12, null, 12, 12, 12, 12 };
		Integer sum1 = (Integer) stats.sum(array1);
		assertEquals(72, (int) sum1);
		
		Integer array2[] = { 21, 0, 39, -25, 18, -2 };
		Integer sum2 = (Integer) stats.sum(array2);
		assertEquals(51, (int) sum2);
		
		Double array3[] = { 0.4, 10.7, 5.673, 0.399, 0.561 };
		Double sum3 = (Double) stats.sum(array3);
		assertEquals(17.733, (double) sum3, epsilon);
		
		Double array4[] = { -32921.01, -32921., 5.673, -287.3, 0.561 };
		Double sum4 = (Double) stats.sum(array4);
		assertEquals(-66123.076, (double) sum4, epsilon);
	}
	
	@Test
	public void testMean() {
		Integer array1[] = { 12, 12, null, 12, 12, 12, 12 };
		Integer mean1 = (Integer) stats.mean(array1);
		assertEquals(12, (int) mean1);
		
		Integer array2[] = { 21, 0, 39, -25, 18, -2 };
		Integer mean2 = (Integer) stats.mean(array2);
		assertEquals(8, (int) mean2, epsilon);
		
		Double array3[] = { 0.4, 10.7, 5.673, 0.399, 0.561 };
		Double mean3 = (Double) stats.mean(array3);
		assertEquals(3.5466, (double) mean3, epsilon);
		
		Double array4[] = { -32921.01, -32921., 5.673, -287.3, 0.561 };
		Double mean4 = (Double) stats.mean(array4);
		assertEquals(-13224.6152, (double) mean4, epsilon);
	}
	
	@Test
	public void testMedian() {
		Integer array1[] = { 12, 12, null, 12, 12, 12, 12 };
		Integer median1 = (Integer) stats.median(array1);
		assertEquals(12, (int) median1);
		
		Integer array2[] = { 21, 0, 39, -25, 18, -2 };
		Integer median2 = (Integer) stats.median(array2);
		assertEquals(9, (int) median2);
		
		Double array3[] = { 0.4, 10.7, 5.673, 0.399, 0.561, 2.6 };
		Double median3 = (Double) stats.median(array3);
		assertEquals(1.5805, (double) median3, epsilon);
		
		Double array4[] = { -32921.01, -32921., 5.673, -287.3, 0.561 };
		Double median4 = (Double) stats.median(array4);
		assertEquals(-287.3, (double) median4, epsilon);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyArray() {
		Integer array[] = new Integer[0];
		stats.min(array);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArray() {
		String array[] = { "A", "B", "C" };
		stats.max(array);
	}
	
	@Test
	public void testMyNumber() {
		@SuppressWarnings("serial")
		final class MyNumber extends Number {
			@Override
			public double doubleValue() { return 1; }
			@Override
			public float floatValue() { return 0; }
			@Override
			public int intValue() { return 0; }
			@Override
			public long longValue() { return 0; }
		}
		
		MyNumber array[] = { new MyNumber(), new MyNumber() };
		Double sum = (Double) stats.sum(array);
		assertEquals(2.0, (double) sum, epsilon);
	}
	
	@Test
	public void testSetOperators() {
		stats.setOperators(Double.class, new IntegerOperators());
		
		Double array[] = { 0.4, 10.7, 5.673, 0.399, 0.561, 2.6 };
		
		Integer sum = (Integer) stats.sum(array);
		assertEquals(17, (int) sum);
	}

}
