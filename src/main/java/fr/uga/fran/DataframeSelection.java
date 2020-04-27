package fr.uga.fran;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to perform selection on a dataframe.
 * All selections return a new dataframe composed of specific rows and columns
 * from the original dataframe.
 *
 * @author ANDRE Stephen
 * @author FREBY Laura
 * @since 1.0.0
 * @see fr.uga.fran.Dataframe
 */
public class DataframeSelection {
	/*
	 * Inner enumeration of all comparison signs
	 */
	private enum Comparison {
		EQUAL,
		NOTEQUAL,
		LESSTHAN,
		LESSEQUAL,
		GREATERTHAN,
		GREATEREQUAL
	}
	
	private Dataframe dataframe;
	
	/**
	 * Constructs a DataframeSelection related to the specified dataframe.
	 * 
	 * @param dataframe the dataframe to perform selection on
	 */
	public DataframeSelection(Dataframe dataframe) {
		this.dataframe = dataframe;
	}
	
	/**
	 * Returns a row subsection of the dataframe based on the specified array of row indexes.
	 * 
	 * @param rows the array of row indexes to select
	 * @return the new dataframe based on the array of row indexes
	 */
	public Dataframe row(int[] rows) {
		int columns[] = range(0, dataframe.columnCount());
		return cross(rows, columns);
	}

	/**
	 * Returns a column subsection of the dataframe based on the specified array of column indexes.
	 * 
	 * @param columns the array of column indexes to select
	 * @return the new dataframe based on the array of column indexes
	 */
	public Dataframe column(int[] columns) {
		int rows[] = range(0, dataframe.rowCount());
		return cross(rows, columns);
	}

	/**
	 * Returns a column subsection of the dataframe based on the specified array of labels.
	 * 
	 * @param labels the array of column labels to select
	 * @return the new dataframe based on the array of labels
	 * @throws java.lang.IllegalArgumentException if one of the labels is not in the original dataframe
	 */
	public Dataframe column(String[] labels) throws IllegalArgumentException {
		int rows[] = range(0, dataframe.rowCount());
		int columns[] = columnIndexes(labels);
		return cross(rows, columns);
	}

	/**
	 * Returns a subsection of the dataframe based on the specified row and column indexes.
	 * 
	 * @param rows the array of row indexes
	 * @param columns the array of column indexes
	 * @return the new dataframe based on the specified row and column indexes
	 */
	public Dataframe cross(int[] rows, int[] columns) {
		// Retrieve labels and data types from the original dataframe
		String labels[] = new String[columns.length];
		Class<?> types[] = new Class<?>[columns.length];
		
		for (int i=0; i<columns.length; i++) {
			labels[i] = dataframe.getLabel(columns[i]);
			types[i] = dataframe.getType(columns[i]);
		}
		
		// Create an empty dataframe
		Dataframe newDataframe = new Dataframe(labels, types);
		
		// Populate the new dataframe with the specified rows
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
	 * Returns a subsection of the dataframe based on the specified row indexes and column labels.
	 * 
	 * @param rows the array of row indexes
	 * @param labels the array of column labels
	 * @return the new dataframe based on the specified row indexes and column labels
	 * @throws java.lang.IllegalArgumentException if one of the labels is not in the original dataframe
	 */
	public Dataframe cross(int[] rows, String[] labels) throws IllegalArgumentException {
		int columns[] = columnIndexes(labels);
		return cross(rows, columns);
	}

	/**
	 * Returns a new dataframe with only the rows that have a value equal to the specified value.
	 * 
	 * @param label the label of the column to compare
	 * @param value the value to compare to
	 * @return the new dataframe based on the rows found
	 * @throws java.lang.IllegalArgumentException if the label is not in the original dataframe
	 */	
	public <T> Dataframe equal(String label, Comparable<T> value) throws IllegalArgumentException {
		return compareSelect(label, value, Comparison.EQUAL);
	}
	
	/**
	 * Returns a new dataframe with only the rows that have a value different to the specified value.
	 * 
	 * @param label the label of the column to compare
	 * @param value the value to compare to
	 * @return the new dataframe based on the rows found
	 * @throws java.lang.IllegalArgumentException if the label is not in the original dataframe
	 */	
	public <T> Dataframe notEqual(String label, Comparable<T> value) throws IllegalArgumentException {
		return compareSelect(label, value, Comparison.NOTEQUAL);
	}

	/**
	 * Returns a new dataframe with only the rows that have a value less than the specified value.
	 * 
	 * @param label the label of the column to compare
	 * @param value the value to compare to
	 * @return the new dataframe based on the rows found
	 * @throws java.lang.IllegalArgumentException if the label is not in the original dataframe
	 */	
	public <T> Dataframe lessThan(String label, Comparable<T> value) throws IllegalArgumentException {
		return compareSelect(label, value, Comparison.LESSTHAN);
	}

	/**
	 * Returns a new dataframe with only the rows that have a value less than or equal to the specified value.
	 * 
	 * @param label the label of the column to compare
	 * @param value the value to compare to
	 * @return the new dataframe based on the rows found
	 * @throws java.lang.IllegalArgumentException if the label is not in the original dataframe
	 */	
	public <T> Dataframe lessEqual(String label, Comparable<T> value) throws IllegalArgumentException {
		return compareSelect(label, value, Comparison.LESSEQUAL);
	}
	
	/**
	 * Returns a new dataframe with only the rows that have a value greater than the specified value.
	 * 
	 * @param label the label of the column to compare
	 * @param value the value to compare to
	 * @return the new dataframe based on the rows found
	 * @throws java.lang.IllegalArgumentException if the label is not in the original dataframe
	 */	
	public <T> Dataframe greaterThan(String label, Comparable<T> value) throws IllegalArgumentException {
		return compareSelect(label, value, Comparison.GREATERTHAN);
	}

	/**
	 * Returns a new dataframe with only the rows that have a value greater than or equal to the specified value.
	 * 
	 * @param label the label of the column to compare
	 * @param value the value to compare to
	 * @return the new dataframe based on the rows found
	 * @throws java.lang.IllegalArgumentException if the label is not in the original dataframe
	 */	
	public <T> Dataframe greaterEqual(String label, Comparable<T> value) throws IllegalArgumentException {
		return compareSelect(label, value, Comparison.GREATEREQUAL);
	}
	
	
	/*---------------------------------*/
	/*-----    Private methods    -----*/
	/*---------------------------------*/
	
	/*
	 * Constructs a dataframe where all fields of the specified column respect the comparison 
	 * to the specified value.
	 */
	private <T> Dataframe compareSelect(String label, Comparable<T> value, Comparison sign) {
		// Search the column index of the specified label
		int column = -1;
		for (int i=0; i<dataframe.columnCount(); i++) {
			if (label.equals(dataframe.getLabel(i))) {
				column = i;
			}
		}
		
		// Search all row indexes respecting the comparison
		List<Integer> indexes = rowIndexes(column, value, sign);
		int rows[] = new int[indexes.size()];
		
		for (int i=0; i<indexes.size(); i++) {
			rows[i] = indexes.get(i);
		}
		
		// Select all columns
		int columns[] = range(0, dataframe.columnCount());
		
		return cross(rows, columns);
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

	/*
	 * Returns a list of row indexes that respect the comparison to the specified value.
	 */
	private <T> List<Integer> rowIndexes(int column, Comparable<T> value, Comparison sign) {
		List<Integer> indexes = new ArrayList<Integer>();
		
		for (int i=0; i<dataframe.rowCount(); i++) {
			if (dataframe.get(i, column) != null) {
				try {
					@SuppressWarnings("unchecked")
					int compare = value.compareTo((T) dataframe.get(i, column));
					
					if (isComparisonValid(compare, sign)) {
						indexes.add(i);
					}
				} catch (Exception e) {
					throw new IllegalArgumentException(
							"cannot compare "+dataframe.getType(column)+" to "+value.getClass(), e);
				}
			}
		}
		
		return indexes;
	}
	
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
	 * Returns true if the specified value compare respects the comparison sign, false otherwise.
	 */
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
