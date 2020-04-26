package fr.uga.fran.math;

public interface ArrayStatistics {
	public int argmin(Object[] array);
	public int argmax(Object[] array);
	public Object min(Object[] array);
	public Object max(Object[] array);
	public Object sum(Object[] array);
	public Object mean(Object[] array);
	public Object median(Object[] array);
}
