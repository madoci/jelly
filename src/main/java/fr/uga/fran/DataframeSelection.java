package fr.uga.fran;

import java.util.ArrayList;
import java.util.List;

public class DataframeSelection {
	private enum Comparison {
		EQUAL,
		NOTEQUAL,
		LESSTHAN,
		LESSEQUAL,
		GREATERTHAN,
		GREATEREQUAL
	}
	
	private Dataframe dataframe;
	
	public DataframeSelection(Dataframe dataframe) {
		this.dataframe = dataframe;
	}
	
	/**
	 * Returns a row subsection of a dataframe based on an array of indexes.
	 * 
	 * @param rows the array of row indexes
	 * @return the new dataframe based on the array of indexes
	 * @throws java.lang.IllegalArgumentException if one of the indexes is invalid
	 * @since 0.4.0
	 */
	public Dataframe row(int[] rows) {
		int columns[] = range(0, dataframe.columnCount());
		return cross(rows, columns);
	}

	/**
	 * Returns a column subsection of a dataframe based on an array of column indexes.
	 * 
	 * @param columns the array of column labels
	 * @return the new dataframe based on the array of labels
	 * @throws java.lang.IllegalArgumentException if one of the labels is invalid
	 * @since 0.4.0
	 */
	public Dataframe column(int[] columns) {
		int rows[] = range(0, dataframe.rowCount());
		return cross(rows, columns);
	}

	/**
	 * Returns a column subsection of a dataframe based on an array of labels.
	 * 
	 * @param labels the array of column labels
	 * @return the new dataframe based on the array of labels
	 * @throws java.lang.IllegalArgumentException if one of the labels is invalid
	 * @since 0.4.0
	 */
	public Dataframe column(String[] labels) {
		int rows[] = range(0, dataframe.rowCount());
		int columns[] = columnIndexes(labels);
		return cross(rows, columns);
	}

	/**
	 * Returns a subsection of a dataframe based of the specified row and column indexes.
	 * 
	 * @param rows the array of row indexes
	 * @param columns the array of column indexes
	 * @return the new dataframe based on the specified row and column indexes
	 * @throws java.lang.IllegalArgumentException if one of the indexes is invalid
	 * @since 0.4.0
	 */
	public Dataframe cross(int[] rows, int[] columns) {
		String labels[] = new String[columns.length];
		Class<?> types[] = new Class<?>[columns.length];
		
		for (int i=0; i<columns.length; i++) {
			labels[i] = dataframe.getLabel(columns[i]);
			types[i] = dataframe.getType(columns[i]);
		}
		
		Dataframe newDataframe = new Dataframe(labels, types);
		
		Object row[] = new Object[columns.length];
		
		for (int i : rows) {
			for (int j=0; j<columns.length; j++) {
				row[j] = dataframe.get(i, columns[j]);
			}
			newDataframe.addRow(row);
		}
		
		return newDataframe;
	}
	
	/**
	 * Returns a subsection of a dataframe based of the specified row indexes and column labels.
	 * 
	 * @param rows the array of row indexes
	 * @param labels the array of labels
	 * @return the new dataframe based on the specified row indexes and column labels
	 * @throws java.lang.IllegalArgumentException if one of the indexes or labels is invalid
	 * @since 0.4.0
	 */
	public Dataframe cross(int[] rows, String[] labels) {
		int columns[] = columnIndexes(labels);
		return cross(rows, columns);
	}

	/**
	 * Returns a new dataframe with only the rows that have a value equals to the specified value.
	 * 
	 * @param label the label of the column to compare
	 * @param value the value to compare
	 * @return a new dataframe made up of the right values
	 * @since 0.4.0
	 */	
	public <T> Dataframe equal(String label, Comparable<T> value) {
		return where(label, value, Comparison.EQUAL);
	}
	
	/**
	 * Returns a new dataframe with only the rows that have a value different to the specified value.
	 * 
	 * @param label the label of the column to compare
	 * @param value the value to compare
	 * @return a new dataframe made up of the right values
	 * @since 0.4.0
	 */
	public <T> Dataframe notEqual(String label, Comparable<T> value) {
		return where(label, value, Comparison.NOTEQUAL);
	}

	/**
	 * Returns a new dataframe with only the rows that have a value less than the specified value.
	 * 
	 * @param label the label of the column to compare
	 * @param value the value to compare
	 * @return a new dataframe made up of the right values
	 * @since 0.4.0
	 */
	public <T> Dataframe lessThan(String label, Comparable<T> value) {
		return where(label, value, Comparison.LESSTHAN);
	}

	/**
	 * Returns a new dataframe with only the rows that have a value less than or equal to the specified value.
	 * 
	 * @param label the label of the column to compare
	 * @param value the value to compare
	 * @return a new dataframe made up of the right values
	 * @since 0.4.0
	 */
	public <T> Dataframe lessEqual(String label, Comparable<T> value) {
		return where(label, value, Comparison.LESSEQUAL);
	}
	
	/**
	 * Returns a new dataframe with only the rows that have a value greater than the specified value.
	 * 
	 * @param label the label of the column to compare
	 * @param value the value to compare
	 * @return a new dataframe made up of the right values
	 * @since 0.4.0
	 */
	public <T> Dataframe greaterThan(String label, Comparable<T> value) {
		return where(label, value, Comparison.GREATERTHAN);
	}

	/**
	 * Returns a new dataframe with only the rows that have a value greater than or equal to the specified value.
	 * 
	 * @param label the label of the column to compare
	 * @param value the value to compare
	 * @return a new dataframe made up of the right values
	 * @since 0.4.0
	 */
	public <T> Dataframe greaterEqual(String label, Comparable<T> value) {
		return where(label, value, Comparison.GREATEREQUAL);
	}
	
	
	/*---------------------------------*/
	/*-----    Private methods    -----*/
	/*---------------------------------*/
	
	/*
	 * Returns an array of int ranging from begin to end not included.
	 */
	private int[] range(int begin, int end) {
		int[] array = new int[end-begin];
		
		for(int i=begin; i<end; i++) {
			array[i-begin] = i;
		}
		
		return array;
	}
	
	/*
	 * Returns an array of indexes of the dataframe columns with the specified labels.
	 */
	private int[] columnIndexes(String[] labels) {
		int indexes[] = new int[labels.length];
		
		for (int i=0; i<labels.length; i++) {
			int j = 0;
			while (j < dataframe.columnCount() && !labels[i].equals(dataframe.getLabel(j))) {
				j++;
			}
			
			if (j == dataframe.columnCount()) {
				throw new IllegalArgumentException("");
			}
			
			indexes[i] = j;
		}
		
		return indexes;
	}
	
	private <T> Dataframe where(String label, Comparable<T> value, Comparison sign) {
		int column = -1;
		for (int i=0; i<dataframe.columnCount(); i++) {
			if (label.equals(dataframe.getLabel(i))) {
				column = i;
			}
		}
		
		List<Integer> indexes = rowIndexes(column, value, sign);
		int rows[] = new int[indexes.size()];
		for (int i=0; i<indexes.size(); i++) {
			rows[i] = indexes.get(i);
		}
		
		int columns[] = range(0, dataframe.columnCount());
		
		return cross(rows, columns);
	}

	@SuppressWarnings("unchecked")
	private <T> List<Integer> rowIndexes(int column, Comparable<T> value, Comparison sign) {
		List<Integer> indexes = new ArrayList<Integer>();
		
		for (int i=0; i<dataframe.rowCount(); i++) {
			int compare = value.compareTo((T) dataframe.get(i, column));
			
			if (isComparisonValid(compare, sign)) {
				indexes.add(i);
			}
		}
		
		return indexes;
	}
	
	private boolean isComparisonValid(int compare, Comparison sign) {
		switch (sign) {
		case NOTEQUAL:
			return (compare != 0);
		case LESSTHAN:
			return (compare > 0);
		case LESSEQUAL:
			return (compare >= 0);
		case GREATERTHAN:
			return (compare < 0);
		case GREATEREQUAL:
			return (compare <= 0);
		default:
			return (compare == 0);
		}
	}
	
}
