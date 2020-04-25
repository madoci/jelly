package fr.uga.fran.math;

public class DoubleOperators implements NumberOperators {

	@Override
	public Number add(Number a, Number b) {
		Double result = a.doubleValue() + b.doubleValue();
		return result;
	}

	@Override
	public Number substract(Number a, Number b) {
		Double result = a.doubleValue() - b.doubleValue();
		return result;
	}

	@Override
	public Number multiply(Number a, Number b) {
		Double result = a.doubleValue() * b.doubleValue();
		return result;
	}

	@Override
	public Number divide(Number a, Number b) {
		Double result = a.doubleValue() / b.doubleValue();
		return result;
	}

	@Override
	public boolean equals(Number a, Number b) {
		return a.doubleValue() == b.doubleValue();
	}

	@Override
	public boolean lessThan(Number a, Number b) {
		return a.doubleValue() < b.doubleValue();
	}

	@Override
	public boolean lessEquals(Number a, Number b) {
		return a.doubleValue() <= b.doubleValue();
	}
	
	@Override
	public int compare(Number a, Number b) {
		return (int) (a.doubleValue() - b.doubleValue());
	}

}
