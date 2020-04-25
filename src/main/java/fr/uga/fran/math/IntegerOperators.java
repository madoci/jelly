package fr.uga.fran.math;

public class IntegerOperators implements NumberOperators {

	@Override
	public Number add(Number a, Number b) {
		Integer result = a.intValue() + b.intValue();
		return result;
	}

	@Override
	public Number substract(Number a, Number b) {
		Integer result = a.intValue() - b.intValue();
		return result;
	}

	@Override
	public Number multiply(Number a, Number b) {
		Integer result = a.intValue() * b.intValue();
		return result;
	}

	@Override
	public Number divide(Number a, Number b) {
		Integer result = a.intValue() / b.intValue();
		return result;
	}

	@Override
	public boolean equals(Number a, Number b) {
		return a.intValue() == b.intValue();
	}

	@Override
	public boolean lessThan(Number a, Number b) {
		return a.intValue() < b.intValue();
	}

	@Override
	public boolean lessEquals(Number a, Number b) {
		return a.intValue() <= b.intValue();
	}
	
	@Override
	public int compare(Number a, Number b) {
		return a.intValue() - b.intValue();
	}

}
