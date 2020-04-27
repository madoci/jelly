package fr.uga.fran.math;

public class DoubleOperator implements Operator {

	@Override
	public Object zero() {
		return 0.;
	}

	@Override
	public Object add(Object a, Object b) {
		Number n1 = (Number) a;
		Number n2 = (Number) b;
		return (Number) (n1.doubleValue() + n2.doubleValue());
	}

	@Override
	public Object substract(Object a, Object b) {
		Number n1 = (Number) a;
		Number n2 = (Number) b;
		return (Number) (n1.doubleValue() - n2.doubleValue());
	}

	@Override
	public Object multiply(Object a, Object b) {
		Number n1 = (Number) a;
		Number n2 = (Number) b;
		return (Number) (n1.doubleValue() * n2.doubleValue());
	}

	@Override
	public Object divide(Object a, Object b) {
		Number n1 = (Number) a;
		Number n2 = (Number) b;
		return (Number) (n1.doubleValue() / n2.doubleValue());
	}

	@Override
	public Object mean(Object a, Object b) {
		Number n1 = (Number) a;
		Number n2 = (Number) b;
		return (Number) ((n1.doubleValue() + n2.doubleValue()) / 2.);
	}

	@Override
	public int compare(Object a, Object b) {
		Number n1 = (Number) a;
		Number n2 = (Number) b;
		if (n1.doubleValue() == n2.doubleValue()) {
			return 0;
		} else if (n1.doubleValue() > n2.doubleValue()) {
			return 1;
		} else {
			return -1;
		}
	}

}
