package fr.uga.fran.math;

/**
 * An implementation of the Operator interface for operating on integers on a non-strict manner.
 * This means divisions and means returns double instead of integers.
 *
 * @author ANDRE Stephen
 * @author FREBY Laura
 * @since 1.0.0
 */
public class IntegerOperator extends IntegerStrictOperator {

	@Override
	public Object divide(Object a, Object b) {
		return (Number) ((double) value(a) / (double) value(b));
	}

	@Override
	public Object mean(Object a, Object b) {
		return (Number) (((double) value(a) + (double) value(b)) / 2);
	}

}
