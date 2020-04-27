package fr.uga.fran.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayStatistics {
	
	private static Map<Class<?>, Operator> operatorsMap;
	
	public static int argmin(Object[] array) throws IllegalArgumentException {
		// Retrieve the data type of the array and the operator associated
		Operator operator = getOperator(findDataType(array));
		
		// Search for the min
		int argmin = 0;
		for (int i=1; i<array.length; i++) {
			if (array[i] != null && operator.compare(array[i], array[argmin]) < 0) {
				argmin = i;
			}
		}
		
		return argmin;
	}
	
	public static int argmax(Object[] array) throws IllegalArgumentException {
		// Retrieve the data type of the array and the operator associated
		Operator operator = getOperator(findDataType(array));
		
		// Search for the max
		int argmax = 0;		
		for (int i=1; i<array.length; i++) {
			if (array[i] != null && operator.compare(array[i], array[argmax]) > 0) {
				argmax = i;
			}
		}
		
		return argmax;
	}
	
	public static Object min(Object[] array) throws IllegalArgumentException {
		return array[argmin(array)];
	}
	
	public static Object max(Object[] array) throws IllegalArgumentException {
		return array[argmax(array)];
	}
	
	public static Object sum(Object[] array) throws IllegalArgumentException {
		// Retrieve the data type of the array and the operator associated
		Operator operator = getOperator(findDataType(array));
		
		Object sum = operator.zero();		
		for (Object object : array) {
			if (object != null) {
				sum = operator.add(sum, object);
			}
		}
		
		return sum;
	}
	
	public static Object mean(Object[] array) throws IllegalArgumentException {
		// Retrieve the data type of the array and the operator associated
		Operator operator = getOperator(findDataType(array));
		
		int count = 0;
		Object sum = operator.zero();
		
		for (Object object : array) {
			if (object != null) {
				sum = operator.add(sum, object);
				count++;
			}
		}
		
		return operator.divide(sum, count);
	}
	
	public static Object median(Object[] array) throws IllegalArgumentException {
		// Retrieve the data type of the array and the operator associated
		Operator operator = getOperator(findDataType(array));
		
		Object[] sorted = sortArray(array, operator);
		
		int middle = sorted.length / 2;
		Object median = sorted[middle];
		
		if ((sorted.length % 2) == 0) {
			median = operator.mean(median, sorted[middle-1]);
		}
		
		return median;
	}
	
	public static void setOperator(Class<?> type, Operator operator) {
		operatorsMap.put(type, operator);
	}
	
	private static void initialize() {
		operatorsMap = new HashMap<>();
		setOperator(Integer.class, new IntegerStrictOperator());
		setOperator(Number.class, new DoubleOperator());
	}
	
	/*
	 * Returns the class of the first non-null object in the specified array.
	 * Throws an IllegalArgumentException if all objects are null.
	 */
	private static Class<?> findDataType(Object[] array) throws IllegalArgumentException {
		for (Object object : array) {
			if (object != null) {
				return object.getClass();
			}
		}

		throw new IllegalArgumentException("the array cannot contain only null references");
	}
	
	/*
	 * Returns an implementation of the NumberOperator interface associated to the specified type.
	 * If none is associated to the specified type, returns the NumberOperator associated to Double.
	 */
	private static Operator getOperator(Class<?> type) throws IllegalArgumentException {
		if (operatorsMap == null) {
			initialize();
		}
		
		if (operatorsMap.containsKey(type)) {
			return operatorsMap.get(type);
		} else if (Number.class.isAssignableFrom(type)) {
			return operatorsMap.get(Number.class);
		} else {
			throw new IllegalArgumentException("no operator found for "+type);
		}
	}
	
	private static Object[] sortArray(Object[] array, Operator operator) {
		List<Object> sorted = new ArrayList<>();
		
		for (Object object : array) {
			if (object != null) {
				sorted.add(object);
			}
		}
		
		Collections.sort(sorted, operator);
		
		return sorted.toArray();
	}
}
