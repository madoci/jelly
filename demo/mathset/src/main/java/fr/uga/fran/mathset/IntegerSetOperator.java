package fr.uga.fran.mathset;

import fr.uga.fran.math.Operator;

public class IntegerSetOperator implements Operator {

	@Override
	public Object zero() {
		return IntegerSet.empty();
	}

	@Override
	public Object one() {
		return IntegerSet.full();
	}

	@Override
	public Object add(Object a, Object b) {
		return value(a).union(value(b));
	}

	@Override
	public Object subtract(Object a, Object b) {
		return value(a).intersection(value(b).complement());
	}

	@Override
	public Object multiply(Object a, Object b) {
		return value(a).intersection(value(b));
	}

	@Override
	public Object divide(Object a, Object b) {
		return value(a).union(value(b).complement());
	}

	@Override
	public Object mean(Object a, Object b) {
		return add(a, b);
	}

	@Override
	public int compare(Object a, Object b) {
		return value(a).size() - value(b).size();
	}
	
	
	private IntegerSet value(Object o) {
		return (IntegerSet) o;
	}

}
