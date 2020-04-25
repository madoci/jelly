package fr.uga.fran.math;

import java.util.Comparator;

public interface NumberOperators extends Comparator<Number> {
	public Number add(Number a, Number b);
	public Number substract(Number a, Number b);
	public Number multiply(Number a, Number b);
	public Number divide(Number a, Number b);
	
	public boolean equals(Number a, Number b);
	public boolean lessThan(Number a, Number b);
	public boolean lessEquals(Number a, Number b);
}
