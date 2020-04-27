package fr.uga.fran.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ArrayStatisticsTest {

	private final double epsilon = 0.00001;

	@Test
	public void testArgmin() {
		Integer array1[] = { 21, 7, null, 189, 1000000, 35, 1 };
		assertEquals(6, ArrayStatistics.argmin(array1));
		
		Integer array2[] = { 21, 0, 189, -126, 35, -2 };
		assertEquals(3, ArrayStatistics.argmin(array2));
		
		Double array3[] = { 0.4, 10.7, 5.673, 0.399, 0.561 };
		assertEquals(3, ArrayStatistics.argmin(array3));
		
		Double array4[] = { -32921.01, -32921., 5.673, -287.3, 0.561 };
		assertEquals(0, ArrayStatistics.argmin(array4));
	}
	
	@Test
	public void testArgmax() {
		Integer array1[] = { 21, 7, null, 189, 1000000, 35, 1 };
		assertEquals(4, ArrayStatistics.argmax(array1));
		
		Integer array2[] = { 21, 0, 189, -226, 35, -2 };
		assertEquals(2, ArrayStatistics.argmax(array2));
		
		Double array3[] = { 0.4, 10.7, 5.673, 0.399, 0.561 };
		assertEquals(1, ArrayStatistics.argmax(array3));
		
		Double array4[] = { -32921.01, -32921., 5.673, -287.3, 0.561 };
		assertEquals(2, ArrayStatistics.argmax(array4));
	}
	
	@Test
	public void testMin() {
		Integer array1[] = { 21, 7, null, 189, 1000000, 35, 1 };
		int min1 = (int) ArrayStatistics.min(array1);
		assertEquals(1, min1);
		
		Integer array2[] = { 21, 0, 189, -226, 35, -2 };
		int min2 = (int) ArrayStatistics.min(array2);
		assertEquals(-226, min2);
		
		Double array3[] = { 0.4, 10.7, 5.673, 0.399, 0.561 };
		double min3 = (double) ArrayStatistics.min(array3);
		assertEquals(0.399, min3, epsilon);
		
		Double array4[] = { -32921.01, -32921., 5.673, -287.3, 0.561 };
		double min4 = (double) ArrayStatistics.min(array4);
		assertEquals(-32921.01, min4, epsilon);
	}
	
	@Test
	public void testMax() {
		Integer array1[] = { 21, 7, null, 189, 1000000, 35, 1 };
		int max1 = (int) ArrayStatistics.max(array1);
		assertEquals(1000000, max1);
		
		Integer array2[] = { 21, 0, 189, -226, 35, -2 };
		int max2 = (int) ArrayStatistics.max(array2);
		assertEquals(189, max2);
		
		Double array3[] = { 0.4, 10.7, 5.673, 0.399, 0.561 };
		double max3 = (double) ArrayStatistics.max(array3);
		assertEquals(10.7, max3, epsilon);
		
		Double array4[] = { -32921.01, -32921., 5.673, -287.3, 0.561 };
		double max4 = (double) ArrayStatistics.max(array4);
		assertEquals(5.673, max4, epsilon);
	}
	
	@Test
	public void testSum() {
		Integer array1[] = { 12, 12, null, 12, 12, 12, 12 };
		int sum1 = (int) ArrayStatistics.sum(array1);
		assertEquals(72, sum1);
		
		Integer array2[] = { 21, 0, 39, -25, 18, -2 };
		int sum2 = (int) ArrayStatistics.sum(array2);
		assertEquals(51, sum2);
		
		Double array3[] = { 0.4, 10.7, 5.673, 0.399, 0.561 };
		double sum3 = (double) ArrayStatistics.sum(array3);
		assertEquals(17.733, sum3, epsilon);
		
		Double array4[] = { -32921.01, -32921., 5.673, -287.3, 0.561 };
		double sum4 = (double) ArrayStatistics.sum(array4);
		assertEquals(-66123.076, sum4, epsilon);
	}
	
	@Test
	public void testMean() {
		Integer array1[] = { 12, 12, null, 12, 12, 12, 12 };
		int mean1 = (int) ArrayStatistics.mean(array1);
		assertEquals(12, mean1);
		
		Integer array2[] = { 21, 0, 39, -25, 18, -2 };
		int mean2 = (int) ArrayStatistics.mean(array2);
		assertEquals(8, mean2);
		
		Double array3[] = { 0.4, 10.7, 5.673, 0.399, 0.561 };
		double mean3 = (double) ArrayStatistics.mean(array3);
		assertEquals(3.5466, mean3, epsilon);
		
		Double array4[] = { -32921.01, -32921., 5.673, -287.3, 0.561 };
		double mean4 = (double) ArrayStatistics.mean(array4);
		assertEquals(-13224.6152, mean4, epsilon);
	}
	
	@Test
	public void testMedian() {
		Integer array1[] = { 12, 12, null, 12, 12, 12, 12 };
		int median1 = (int) ArrayStatistics.median(array1);
		assertEquals(12, median1);
		
		Integer array2[] = { 21, 0, 39, -25, 18, -2 };
		int median2 = (int) ArrayStatistics.median(array2);
		assertEquals(9, median2);
		
		Double array3[] = { 0.4, 10.7, 5.673, 0.399, 0.561, 2.6 };
		double median3 = (double) ArrayStatistics.median(array3);
		assertEquals(1.5805, median3, epsilon);
		
		Double array4[] = { -32921.01, -32921., 5.673, -287.3, 0.561 };
		double median4 = (double) ArrayStatistics.median(array4);
		assertEquals(-287.3, median4, epsilon);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyArray() {
		Integer array[] = new Integer[0];
		ArrayStatistics.min(array);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArray() {
		String array[] = { "A", "B", "C" };
		ArrayStatistics.max(array);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullArray() {
		Integer array[] = { null, null, null };
		ArrayStatistics.max(array);
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
		double sum = (double) ArrayStatistics.sum(array);
		assertEquals(2.0, sum, epsilon);
	}
	
	@Test
	public void testSetOperators() {
		ArrayStatistics.setOperator(Integer.class, new IntegerOperator());
		
		Integer array[] = { 21, 0, 39, -25, 19, -3 };
		
		double mean = (double) ArrayStatistics.mean(array);
		assertEquals(8.5, mean, epsilon);
		
		double median = (double) ArrayStatistics.median(array);
		assertEquals(9.5, median, epsilon);
		
		ArrayStatistics.setOperator(Integer.class, new IntegerStrictOperator());
	}
	
	@Test
	public void testInstance() {
		new ArrayStatistics();
	}

}
