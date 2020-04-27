package fr.uga.fran.math;

/**
 * An implementation of the Operator interface for operating on doubles.
 *
 * @author ANDRE Stephen
 * @author FREBY Laura
 * @since 1.0.0
 */
public class DoubleOperator implements Operator {

	@Override
	public Object zero() {
		return 0.;
	}

	@Override
	public Object add(Object a, Object b) {
		return (Number) (value(a) + value(b));
	}

	@Override
	public Object subtract(Object a, Object b) {
		return (Number) (value(a) - value(b));
	}

	@Override
	public Object multiply(Object a, Object b) {
		return (Number) (value(a) * value(b));
	}

	@Override
	public Object divide(Object a, Object b) {
		return (Number) (value(a) / value(b));
	}

	@Override
	public Object mean(Object a, Object b) {
		return (Number) ((value(a) + value(b)) / 2.);
	}

	@Override
	public int compare(Object a, Object b) {
		if (value(a) == value(b)) {
			return 0;
		} else if (value(a) > value(b)) {
			return 1;
		} else {
			return -1;
		}
	}


	/*---------------------------------*/
	/*-----    Private methods    -----*/
	/*---------------------------------*/
	
	/*
	 * Returns the double value of the specified object.
	 */
	protected double value(Object o) {
		return ((Number) o).doubleValue();
	}

}
