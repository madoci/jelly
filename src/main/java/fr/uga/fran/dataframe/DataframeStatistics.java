package fr.uga.fran.dataframe;

import fr.uga.fran.math.ArrayStatistics;

public class DataframeStatistics {
	
	private Dataframe dataframe;
	
	public DataframeStatistics(Dataframe dataframe) {
		this.dataframe = dataframe;
	}
	
	public int argmin(int column) throws IllegalArgumentException {
		return ArrayStatistics.argmin(dataframe.getColumn(column));
	}
	
	public int argmin(String label) throws IllegalArgumentException {
		return ArrayStatistics.argmin(dataframe.getColumn(label));
	}
	
	public int argmax(int column) throws IllegalArgumentException {
		return ArrayStatistics.argmax(dataframe.getColumn(column));
	}
	
	public int argmax(String label) throws IllegalArgumentException {
		return ArrayStatistics.argmax(dataframe.getColumn(label));
	}
	
	public Object min(int column) throws IllegalArgumentException {
		return ArrayStatistics.min(dataframe.getColumn(column));
	}
	
	public Object min(String label) throws IllegalArgumentException {
		return ArrayStatistics.min(dataframe.getColumn(label));
	}
	
	public Object max(int column) throws IllegalArgumentException {
		return ArrayStatistics.max(dataframe.getColumn(column));
	}
	
	public Object max(String label) throws IllegalArgumentException {
		return ArrayStatistics.max(dataframe.getColumn(label));
	}
	
	public Object sum(int column) throws IllegalArgumentException {
		return ArrayStatistics.sum(dataframe.getColumn(column));
	}
	
	public Object sum(String label) throws IllegalArgumentException {
		return ArrayStatistics.sum(dataframe.getColumn(label));
	}
	
	public Object mean(int column) throws IllegalArgumentException {
		return ArrayStatistics.mean(dataframe.getColumn(column));
	}
	
	public Object mean(String label) throws IllegalArgumentException {
		return ArrayStatistics.mean(dataframe.getColumn(label));
	}
	
	public Object median(int column) throws IllegalArgumentException {
		return ArrayStatistics.median(dataframe.getColumn(column));
	}
	
	public Object median(String label) throws IllegalArgumentException {
		return ArrayStatistics.median(dataframe.getColumn(label));
	}
}
