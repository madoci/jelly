package fr.uga.fran.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ArrayStatistics {
	
	public static int argmin(Object[] array) throws IllegalArgumentException {
		checkArray(array);
		
		int argmin = 0;
		NumberOperators operators = getOperators(array[0].getClass());
		
		for (int i=1; i<array.length; i++) {
			if (array[i] != null && operators.lessThan((Number) array[i], (Number) array[argmin])) {
				argmin = i;
			}
		}
		
		return argmin;
	}
	
	public static int argmax(Object[] array) throws IllegalArgumentException {
		checkArray(array);
		
		int argmax = 0;
		NumberOperators operators = getOperators(array[0].getClass());
		
		for (int i=1; i<array.length; i++) {
			if (array[i] != null && operators.lessThan((Number) array[argmax], (Number) array[i])) {
				argmax = i;
			}
		}
		
		return argmax;
	}
	
	public static Number min(Object[] array) throws IllegalArgumentException {
		return (Number) array[argmin(array)];
	}
	
	public static Number max(Object[] array) throws IllegalArgumentException {
		return (Number) array[argmax(array)];
	}
	
	public static Number sum(Object[] array) throws IllegalArgumentException {
		checkArray(array);
		
		Number sum = 0;
		NumberOperators operators = getOperators(array[0].getClass());
		
		for (Object object : array) {
			if (object != null) {
				sum = operators.add(sum, (Number) object);
			}
		}
		
		return sum;
	}
	
	public static Number mean(Object[] array) throws IllegalArgumentException {
		checkArray(array);
		
		int count = 0;
		Number sum = 0;
		NumberOperators operators = getOperators(array[0].getClass());
		
		for (Object object : array) {
			if (object != null) {
				sum = operators.add(sum, (Number) object);
				count++;
			}
		}
		
		return operators.divide(sum, count);
	}
	
	public static Number median(Object[] array) throws IllegalArgumentException {
		checkArray(array);
		
		Object[] sortedArray = sortArray(array);
		
		int middle = array.length / 2;
		Number median = (Number) sortedArray[middle];
		
		if ((array.length % 2) == 0) {
			NumberOperators operators = getOperators(array[0].getClass());
			median = operators.add(median, (Number) sortedArray[middle-1]);
			median = operators.divide(median, 2);
		}
		
		return median;
	}
	
	
	/*---------------------------------*/
	/*-----    Private methods    -----*/
	/*---------------------------------*/
	
	private static void checkArray(Object[] array) throws IllegalArgumentException {
		if (array.length == 0) {
			throw new IllegalArgumentException("cannot perform operations on an empty array");
		} else if (!Number.class.isInstance(array[0])) {
			throw new IllegalArgumentException(
					"array elements are of "+array[0].getClass()+" which doesn't extends "+Number.class);
		}
	}
	
	private static NumberOperators getOperators(Class<?> type) throws IllegalArgumentException {
		if (Integer.class.isAssignableFrom(type)) {
			return new IntegerOperators();
		} else if (Double.class.isAssignableFrom(type)) {
			return new DoubleOperators();
		} else {
			throw new IllegalArgumentException("cannot perform operations on element of"+type);
		}
	}
	
	private static Object[] sortArray(Object[] array) {
		List<Number> sorted = new ArrayList<>();
		
		NumberOperators operators = getOperators(array[0].getClass());
		
		for (Object object : array) {
			if (object != null) {
				sorted.add((Number) object);
			}
		}
		
		Collections.sort(sorted, operators);
		
		return sorted.toArray();
	}
}
