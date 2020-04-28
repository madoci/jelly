package fr.uga.fran.math;

import org.junit.Test;

public class ArrayStatisticsSetOperatorTest {

	/*
	 * A simple test to ensure no NullPointerException is raised when calling setOperator
	 * before any other methods of ArrayStatistics.
	 */
	@Test
	public void test() {
		ArrayStatistics.setOperator(Integer.class, new IntegerOperator());
	}

}
