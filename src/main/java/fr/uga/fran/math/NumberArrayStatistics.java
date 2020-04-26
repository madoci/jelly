package fr.uga.fran.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NumberArrayStatistics implements ArrayStatistics {
	
	private Map<Class<?>, NumberOperators> operatorsMap;
	
	public NumberArrayStatistics() {
		operatorsMap = new HashMap<>();
		operatorsMap.put(Integer.class, new IntegerOperators());
		operatorsMap.put(Double.class, new DoubleOperators());
	}
	
	public void setOperators(Class<?> type, NumberOperators operators) {
		operatorsMap.put(type, operators);
	}
	
	@Override
	public int argmin(Object[] array) throws IllegalArgumentException {
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
	
	@Override
	public int argmax(Object[] array) throws IllegalArgumentException {
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
	
	@Override
	public Number min(Object[] array) throws IllegalArgumentException {
		return (Number) array[argmin(array)];
	}
	
	@Override
	public Number max(Object[] array) throws IllegalArgumentException {
		return (Number) array[argmax(array)];
	}
	
	@Override
	public Number sum(Object[] array) throws IllegalArgumentException {
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
	
	@Override
	public Number mean(Object[] array) throws IllegalArgumentException {
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
	
	@Override
	public Number median(Object[] array) throws IllegalArgumentException {
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
	
	/*
	 * Throws an IllegalArgumentException if the specified array is empty or if its elements
	 * are not numbers.
	 */
	private void checkArray(Object[] array) throws IllegalArgumentException {
		if (array.length == 0) {
			throw new IllegalArgumentException("cannot perform operations on an empty array");
		} else if (!Number.class.isInstance(array[0])) {
			throw new IllegalArgumentException(
					"array elements are of "+array[0].getClass()+" which doesn't extends "+Number.class);
		}
	}
	
	/*
	 * Returns an implementation of the NumberOperators interface associated to the specified type.
	 * If none is associated to the specified type, returns the NumberOperators associated to Double.
	 */
	private NumberOperators getOperators(Class<?> type) throws IllegalArgumentException {
		if (operatorsMap.containsKey(type)) {
			return operatorsMap.get(type);
		} else {
			return operatorsMap.get(Double.class);
		}
	}
	
	private Object[] sortArray(Object[] array) {
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
