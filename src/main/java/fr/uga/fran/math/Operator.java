package fr.uga.fran.math;

import java.util.Comparator;

public interface Operator extends Comparator<Object> {
	public Object zero();
	
	public Object add(Object a, Object b);
	public Object substract(Object a, Object b);
	public Object multiply(Object a, Object b);
	public Object divide(Object a, Object b);
	public Object mean(Object a, Object b);
}
