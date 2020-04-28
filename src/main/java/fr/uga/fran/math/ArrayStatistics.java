package fr.uga.fran.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains static methods to perform statistics on array of objects.
 * The class of objects in an array must have an operator associated to it in order
 * to be able to perform statistics. The static method setOperator allow for users 
 * to add their own implementation of Operator for a specific class.
 * By default, IntegerStrictOperator is used for integers, and DoubleStrictOperator
 * is used for doubles and any other class extending Number.
 *
 * @author ANDRE Stephen
 * @author FREBY Laura
 * @since 1.0.0
 * @see fr.uga.fran.math.Operator
 * @see fr.uga.fran.math.IntegerStrictOperator
 * @see fr.uga.fran.math.DoubleOperator
 */
public class ArrayStatistics {
	
	private static Map<Class<?>, Operator> operatorsMap;
	
	/**
	 * Returns the index of the lowest value in the specified array.
	 * 
	 * @param array the array to get the min from
	 * @return the index of the lowest value in the specified array
	 * @throws java.lang.IllegalArgumentException if the objects in the array are not associated to any operator
	 */
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

	/**
	 * Returns the index of the greatest value in the specified array.
	 * 
	 * @param array the array to get the max from
	 * @return the index of the greatest value in the specified array
	 * @throws java.lang.IllegalArgumentException if the objects in the array are not associated to any operator
	 */
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

	/**
	 * Returns the lowest value in the specified array.
	 * 
	 * @param array the array to get the min from
	 * @return the lowest value in the specified array
	 * @throws java.lang.IllegalArgumentException if the objects in the array are not associated to any operator
	 */
	public static Object min(Object[] array) throws IllegalArgumentException {
		return array[argmin(array)];
	}

	/**
	 * Returns the greatest value in the specified array.
	 * 
	 * @param array the array to get the max from
	 * @return the greatest value in the specified array
	 * @throws IllegalArgumentException if the objects in the array are not associated to any operator
	 */
	public static Object max(Object[] array) throws IllegalArgumentException {
		return array[argmax(array)];
	}

	/**
	 * Returns the sum of all elements in the specified array.
	 * 
	 * @param array the array to get the sum from
	 * @return the sum of all elements in the specified array
	 * @throws java.lang.IllegalArgumentException if the objects in the array are not associated to any operator
	 */
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

	/**
	 * Returns the mean of all elements in the specified array.
	 * 
	 * @param array the array to get the mean from
	 * @return the mean of all elements in the specified array
	 * @throws java.lang.IllegalArgumentException if the objects in the array are not associated to any operator
	 */
	public static Object mean(Object[] array) throws IllegalArgumentException {
		// Retrieve the data type of the array and the operator associated
		Operator operator = getOperator(findDataType(array));
		
		Object count = operator.zero();
		Object sum = operator.zero();
		
		for (Object object : array) {
			if (object != null) {
				sum = operator.add(sum, object);
				count = operator.add(count, operator.one());
			}
		}
		
		return operator.divide(sum, count);
	}

	/**
	 * Returns the median of all elements in the specified array.
	 * 
	 * @param array the array to get the median from
	 * @return the median of all elements in the specified array
	 * @throws java.lang.IllegalArgumentException if the objects in the array are not associated to any operator
	 */
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
	
	/**
	 * Set an operator to use for the specified type.
	 * 
	 * @param type the type to associate the operator to
	 * @param operator the operator to use
	 */
	public static void setOperator(Class<?> type, Operator operator) {
		if (operatorsMap == null) {
			initialize();
		}
		
		operatorsMap.put(type, operator);
	}


	/*---------------------------------*/
	/*-----    Private methods    -----*/
	/*---------------------------------*/
	
	/*
	 * Initialize the static field and fill it with the default operators. 
	 */
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
	
	/*
	 * Returns an array corresponding to the specified array sorted according to
	 * the operator.
	 */
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
