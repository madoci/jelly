package fr.uga.fran.mathset;

import java.util.HashSet;

public class IntegerSet extends HashSet<Integer> {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Constructs an empty set
	 */
	public IntegerSet() {
		super();
	}
	
	/*
	 * Constructs a set with an array
	 */
	public IntegerSet(Integer[] array) {
		super();
		
		for (Integer i : array) {
			add(i);
		}
	}
	
	public IntegerSet intersection(IntegerSet set) {
		IntegerSet newSet = new IntegerSet();
		
		for (Integer i : set) {
			if (contains(i)) {
				newSet.add(i);
			}
		}
		
		return newSet;
	}
	
	public IntegerSet union(IntegerSet set) {
		IntegerSet newSet = new IntegerSet();
		
		for (Integer i : this) {
			newSet.add(i);
		}
		
		for (Integer i : set) {
			newSet.add(i);
		}
		
		return newSet;
	}
	
	public IntegerSet minus(IntegerSet set) {
		IntegerSet newSet = new IntegerSet();
		
		for (Integer i : this) {
			if (!set.contains(i)) {
				newSet.add(i);
			}
		}
		
		return newSet;
	}
}
