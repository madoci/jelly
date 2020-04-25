package fr.uga.fran.math;

import static org.junit.Assert.*;

import org.junit.Test;

public class ArrayStatisticsTest {

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
		Integer min1 = (Integer) ArrayStatistics.min(array1);
		assertEquals((int) array1[6], (int) min1);
		
		Integer array2[] = { 21, 0, 189, -226, 35, -2 };
		Integer min2 = (Integer) ArrayStatistics.min(array2);
		assertEquals((int) array2[3], (int) min2);
		
		Double array3[] = { 0.4, 10.7, 5.673, 0.399, 0.561 };
		Double min3 = (Double) ArrayStatistics.min(array3);
		assertEquals((double) array3[3], (double) min3, 0.0001);
		
		Double array4[] = { -32921.01, -32921., 5.673, -287.3, 0.561 };
		Double min4 = (Double) ArrayStatistics.min(array4);
		assertEquals((double) array4[0], (double) min4, 0.0001);
	}
	
	@Test
	public void testMax() {
		Integer array1[] = { 21, 7, null, 189, 1000000, 35, 1 };
		Integer max1 = (Integer) ArrayStatistics.max(array1);
		assertEquals((int) array1[4], (int) max1);
		
		Integer array2[] = { 21, 0, 189, -226, 35, -2 };
		Integer max2 = (Integer) ArrayStatistics.max(array2);
		assertEquals((int) array2[2], (int) max2);
		
		Double array3[] = { 0.4, 10.7, 5.673, 0.399, 0.561 };
		Double max3 = (Double) ArrayStatistics.max(array3);
		assertEquals((double) array3[1], (double) max3, 0.0001);
		
		Double array4[] = { -32921.01, -32921., 5.673, -287.3, 0.561 };
		Double max4 = (Double) ArrayStatistics.max(array4);
		assertEquals((double) array4[2], (double) max4, 0.0001);
	}
	
	@Test
	public void testSum() {
		Integer array1[] = { 12, 12, null, 12, 12, 12, 12 };
		Integer sum1 = (Integer) ArrayStatistics.sum(array1);
		assertEquals(72, (int) sum1);
		
		Integer array2[] = { 21, 0, 39, -25, 18, -2 };
		Integer sum2 = (Integer) ArrayStatistics.sum(array2);
		assertEquals(51, (int) sum2);
		
		Double array3[] = { 0.4, 10.7, 5.673, 0.399, 0.561 };
		Double sum3 = (Double) ArrayStatistics.sum(array3);
		assertEquals(17.733, (double) sum3, 0.0001);
		
		Double array4[] = { -32921.01, -32921., 5.673, -287.3, 0.561 };
		Double sum4 = (Double) ArrayStatistics.sum(array4);
		assertEquals(-66123.076, (double) sum4, 0.0001);
	}
	
	@Test
	public void testMean() {
		Integer array1[] = { 12, 12, null, 12, 12, 12, 12 };
		Integer mean1 = (Integer) ArrayStatistics.mean(array1);
		assertEquals(12, (int) mean1);
		
		Integer array2[] = { 21, 0, 39, -25, 18, -2 };
		Integer mean2 = (Integer) ArrayStatistics.mean(array2);
		assertEquals(8, (int) mean2);
		
		Double array3[] = { 0.4, 10.7, 5.673, 0.399, 0.561 };
		Double mean3 = (Double) ArrayStatistics.mean(array3);
		assertEquals(3.5466, (double) mean3, 0.00001);
		
		Double array4[] = { -32921.01, -32921., 5.673, -287.3, 0.561 };
		Double mean4 = (Double) ArrayStatistics.mean(array4);
		assertEquals(-13224.6152, (double) mean4, 0.00001);
	}
	
	@Test
	public void testMedian() {
		Integer array1[] = { 12, 12, null, 12, 12, 12, 12 };
		Integer median1 = (Integer) ArrayStatistics.median(array1);
		assertEquals(12, (int) median1);
		
		Integer array2[] = { 21, 0, 39, -25, 18, -2 };
		Integer median2 = (Integer) ArrayStatistics.median(array2);
		assertEquals(9, (int) median2);
		
		Double array3[] = { 0.4, 10.7, 5.673, 0.399, 0.561, 2.6 };
		Double median3 = (Double) ArrayStatistics.median(array3);
		assertEquals(1.5805, (double) median3, 0.00001);
		
		Double array4[] = { -32921.01, -32921., 5.673, -287.3, 0.561 };
		Double median4 = (Double) ArrayStatistics.median(array4);
		assertEquals(-287.3, (double) median4, 0.00001);
	}

}
