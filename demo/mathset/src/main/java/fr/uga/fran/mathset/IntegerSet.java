package fr.uga.fran.mathset;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class IntegerSet implements Iterable<Integer> {
	
	private Set<Integer> set;
	private boolean infinite;
	
	/*
	 * Constructs an empty set
	 */
	public IntegerSet() {
		set = new HashSet<>();
		infinite = false;
	}
	
	/*
	 * Constructs a set with an array
	 */
	public IntegerSet(Integer[] array) {
		this();
		
		for (Integer i : array) {
			add(i);
		}
	}
	
	/*
	 * Constructs an empty set
	 */
	public static IntegerSet empty() {
		return new IntegerSet();
	}
	
	/*
	 * Constructs a full set
	 */
	public static IntegerSet full() {
		IntegerSet set = new IntegerSet();
		set.infinite = true;
		
		return set;
	}
	
	public IntegerSet complement() {
		IntegerSet newSet;
		
		if (infinite) {
			newSet = empty();
			
			for (Integer i : this) {
				newSet.add(i);
			}
		} else {
			newSet = full();
			
			for (Integer i : this) {
				newSet.remove(i);
			}
		}
		
		return newSet;
	}
	
	public int size() {
		return set.size();
	}
	
	public boolean isInfinite() {
		return infinite;
	}
	
	public void add(Integer i) {
		if (!infinite) {
			set.add(i);
		} else {
			set.remove(i);
		}
	}
	
	public void remove(Integer i) {
		if (!infinite) {
			set.remove(i);
		} else {
			set.add(i);
		}
	}
	
	public boolean contains(Integer o) {
		if (infinite) {
			return !set.contains(o);
		}
		
		return set.contains(o);
	}
	
	public IntegerSet intersection(IntegerSet set) {
		IntegerSet newSet;
		
		if (infinite && set.infinite) {
			newSet = full();
			
			for (Integer i : this) {
				newSet.remove(i);
			}
			for (Integer i : set) {
				newSet.remove(i);
			}
		} else {
			newSet = empty();
			
			if (infinite) {
				for (Integer i : set) {
					if (contains(i)) {
						newSet.add(i);
					}
				}
			} else {
				for (Integer i : this) {
					if (set.contains(i)) {
						newSet.add(i);
					}
				}
			}
		}
		
		return newSet;
	}
	
	public IntegerSet union(IntegerSet set) {
		IntegerSet newSet;
		
		if (infinite || set.infinite) {
			newSet = full();
			
			if (infinite && set.infinite) {
				for (Integer i : this) {
					if (!set.contains(i)) {
						newSet.remove(i);
					}
				}
				for (Integer i : set) {
					if (!contains(i)) {
						newSet.remove(i);
					}
				}
			} else if (infinite) {
				for (Integer i : this) {
					if (!set.contains(i)) {
						newSet.remove(i);
					}
				}
				for (Integer i : set) {
					newSet.add(i);
				}
			} else {
				for (Integer i : this) {
					newSet.add(i);
				}
				for (Integer i : set) {
					if (!contains(i)) {
						newSet.remove(i);
					}
				}
			}
		} else {
			newSet = empty();
			
			for (Integer i : this) {
				newSet.add(i);
			}
			for (Integer i : set) {
				newSet.add(i);
			}
		}
		
		return newSet;
	}
	/*
	public IntegerSet minus(IntegerSet set) {
		IntegerSet newSet;
		
		if (infinite) {
			newSet = full();
			
			for (Integer i : this) {
				newSet.remove(i);
			}
			for (Integer i : set) {
				newSet.remove(i);
			}
		} else {
			newSet = empty();
			
			for (Integer i : this) {
				if (!set.contains(i)) {
					newSet.add(i);
				}
			}
		}
		
		return newSet;
	}*/

	@Override
	public Iterator<Integer> iterator() {
		return set.iterator();
	}
	
	@Override
	public String toString() {
		return null;
	}
}
