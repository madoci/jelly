package fr.uga.fran.math;


public class IntegerStrictOperator implements Operator {

	@Override
	public Object zero() {
		return 0;
	}

	@Override
	public Object add(Object a, Object b) {
		return (Number) (value(a) + value(b));
	}

	@Override
	public Object substract(Object a, Object b) {
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
		return (Number) ((value(a) + value(b)) / 2);
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
	
	protected int value(Object o) {
		return ((Number) o).intValue();
	}

}
