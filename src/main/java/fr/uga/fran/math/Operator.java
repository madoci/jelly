package fr.uga.fran.math;

import java.util.Comparator;

/**
 * An interface for operating on objects.
 * It extends the Comparator<Object> interface in order to also be able
 * to perform comparisons between the objects.
 *
 * @author ANDRE Stephen
 * @author FREBY Laura
 * @since 1.0.0
 */
public interface Operator extends Comparator<Object> {
	/**
	 * Returns the "zero" object of this operator.
	 * 
	 * @return the "zero" object of this operator
	 */
	public Object zero();
	
	/**
	 * Returns the "one" object of this operator.
	 * 
	 * @return the "one" object of this operator
	 */
	public Object one();
	
	/**
	 * Returns the result of the second specified object added to the first.
	 * 
	 * @param a the base object to add to
	 * @param b the object to add
	 * @return the result of the second specified object added to the first
	 */
	public Object add(Object a, Object b);
	
	/**
	 * Returns the result of the second specified object subtracted to the first.
	 * 
	 * @param a the base object to subtract from
	 * @param b the object to subtract
	 * @return the result of the second specified object subtracted to the first
	 */
	public Object subtract(Object a, Object b);
	
	/**
	 * Returns the result of the first specified object multiplied by the second.
	 * 
	 * @param a the base object to multiply
	 * @param b the multiplier object
	 * @return the result of the first specified object multiplied by the second
	 */
	public Object multiply(Object a, Object b);
	
	/**
	 * Returns the result of the first specified object divided by the second.
	 * 
	 * @param a the base object to divide
	 * @param b the divisor object
	 * @return the result of the first specified object divided by the second
	 */
	public Object divide(Object a, Object b);
	
	/**
	 * Returns the result of the mean between the two specified object.
	 * 
	 * @param a the first object
	 * @param b the second object
	 * @return the result of the mean between the two specified object
	 */
	public Object mean(Object a, Object b);
}
