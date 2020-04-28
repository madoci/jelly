package fr.uga.fran.mathset;

import fr.uga.fran.math.Operator;

public class IntegerSetOperator implements Operator {

	@Override
	public Object zero() {
		return new IntegerSet();
	}

	@Override
	public Object add(Object a, Object b) {
		return value(a).union(value(b));
	}

	@Override
	public Object subtract(Object a, Object b) {
		return value(a).minus(value(b));
	}

	@Override
	public Object multiply(Object a, Object b) {
		return value(a).intersection(value(b));
	}

	@Override
	public Object divide(Object a, Object b) {
		return value(a).union(value(b)).minus(value(a).intersection(value(b)));
	}

	@Override
	public Object mean(Object a, Object b) {
		
	}

	@Override
	public int compare(Object a, Object b) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	private IntegerSet value(Object o) {
		return (IntegerSet) o;
	}

}
