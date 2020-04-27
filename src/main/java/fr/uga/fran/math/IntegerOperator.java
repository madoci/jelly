package fr.uga.fran.math;


public class IntegerOperator implements Operator {

	@Override
	public Object zero() {
		return 0;
	}

	@Override
	public Object add(Object a, Object b) {
		Number n1 = (Number) a;
		Number n2 = (Number) b;
		return (Number) (n1.intValue() + n2.intValue());
	}

	@Override
	public Object substract(Object a, Object b) {
		Number n1 = (Number) a;
		Number n2 = (Number) b;
		return (Number) (n1.intValue() - n2.intValue());
	}

	@Override
	public Object multiply(Object a, Object b) {
		Number n1 = (Number) a;
		Number n2 = (Number) b;
		return (Number) (n1.intValue() * n2.intValue());
	}

	@Override
	public Object divide(Object a, Object b) {
		Number n1 = (Number) a;
		Number n2 = (Number) b;
		double divide = ((double) n1.intValue() / (double) n2.intValue());
		return (Number) divide;
	}

	@Override
	public Object mean(Object a, Object b) {
		Number n1 = (Number) a;
		Number n2 = (Number) b;
		double mean = ((double) n1.intValue() + (double) n2.intValue()) / 2;
		return (Number) mean;
	}

	@Override
	public int compare(Object a, Object b) {
		Number n1 = (Number) a;
		Number n2 = (Number) b;
		if (n1.intValue() == n2.intValue()) {
			return 0;
		} else if (n1.intValue() > n2.intValue()) {
			return 1;
		} else {
			return -1;
		}
	}

}
