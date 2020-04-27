package fr.uga.fran.math;


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
