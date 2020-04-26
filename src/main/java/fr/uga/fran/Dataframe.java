package fr.uga.fran;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * A two-dimensional table with heterogeneous data types.
 * Each column is labeled with a name and contains data of a single type.
 * Two columns of the same dataframe can contain data of different types.
 * String representations of a dataframe are obtained by a DataframeViewer.
 * By default, the viewer used is a TabularDataframeViewer.
 *
 * @author ANDRE Stephen
 * @author FREBY Laura
 * @since 0.1.0
 * @see fr.uga.fran.DataframeViewer
 * @see fr.uga.fran.TabularDataframeViewer
 */
public class Dataframe {
	/*
	 * Class for the labeled columns inside a Dataframe.
	 */
	private class Column {
		private final Class<?> type;
		private String label;
		private List<Object> list;

		/*
		 * Create a column with the specified data type and label.
		 */
		public Column(Class<?> type, String label) {
			this.type = type;
			this.label = label;
			list = new ArrayList<>();
		}

		/*
		 * Add an element of data type at the end of this column.
		 * Throws IllegalArgumentException if the specified element is not of the same type
		 * as the column data type.
		 */
		public void add(Object element) throws IllegalArgumentException {
			if (element == null || type.isInstance(element)) {
				list.add(element);
			} else {
				throw new IllegalArgumentException(
						"Element is of " + element.getClass() + " instead of " + type);
			}
		}

		/*
		 * Returns the object located at the specified index.
		 */
		public Object get(int index) { return list.get(index); }

		/*
		 * Returns the data type of this column.
		 */
		public Class<?> getType() { return type; }

		/*
		 * Returns the label of this column.
		 */
		public String getLabel() { return label; }
	}

	private List<Column> columns;
	private int rowCount;
	private DataframeViewer viewer;

	/**
	 * Constructs a dataframe from an array of labels and arrays of columns.
	 *
	 * @param labels the array of labels, in the same order as the columns
	 * @param data variable amount of arrays each containing the content of a column
	 * @throws java.lang.IllegalArgumentException if a column array is composed of objects of different types
	 */
	public Dataframe(String labels[], Object[] ...data) throws IllegalArgumentException {
		this();

		int numRows = 0;

		// Add columns
		for (int i=0; i<data.length; i++) {
			// If no label is provided, the label is an empty string
			String label = "";
			if (i < labels.length) {
				label = labels[i];
			}

			// The data type of the column is retrieved from the first element of this column
			addColumn(data[i][0].getClass(), label);

			// Keep track of the maximum number of rows between all columns
			if (data[i].length > numRows) {
				numRows = data[i].length;
			}
		}

		// Add rows
		for (int i=0; i<numRows; i++) {
			Object row[] = new Object[columns.size()];
			for (int j=0; j<columns.size(); j++) {
				if (i < data[j].length) {
					row[j] = data[j][i];
				} else {
					row[j] = null;
				}
			}
			addRow(row);
		}
	}

	/**
	 * Constructs a dataframe from a CSV file.
	 *
	 * @param pathname pathname of the CSV file to use
	 * @throws java.io.FileNotFoundException if the file at pathname is not found
	 * @throws fr.uga.fran.InvalidCSVFormatException if the file does not follow CSV format
	 * @since 0.2.0
	 */
	public Dataframe(String pathname) throws FileNotFoundException, InvalidCSVFormatException {
		this();

		CSVParser parser = new CSVParser(pathname);

		// First row of the file contains the labels
		Object[] labels = parser.readLine();
		if (labels == null) {
			throw new InvalidCSVFormatException("file is empty");
		}

		List<Object[]> lines = new ArrayList<>();
		Object[] data;

		// Parse and store all lines first
		while ((data = parser.readLine()) != null) {
			lines.add(data);
			// All lines must have the same number of fields
			if (data.length != labels.length) {
				throw new InvalidCSVFormatException("invalid number of fields at line " + lines.size());
			}
		}

		// Add columns
		for (int i=0; i<labels.length; i++) {
			// Search first not null element in the column to retrieve the type
			int j = 0;
			while (j < lines.size() && lines.get(j)[i] == null) {
				j++;
			}
			// Empty column is considered an error because we cannot infer data type
			if (j >= lines.size()) {
				throw new InvalidCSVFormatException("no data found in column " + i);
			}

			addColumn(lines.get(j)[i].getClass(), (String) labels[i]);
		}

		// Add rows
		for (Object[] row : lines) {
			addRow(row);
		}
	}

	/**
	 * Access a single element from a pair of row/column indexes.
	 *
	 * @param row the row index
	 * @param column the column index
	 * @return the element located at indexes (row, column)
	 */
	public Object get(int row, int column) {
		return columns.get(column).get(row);
	}

	/**
	 * Access a single element from a row index and a column label.
	 *
	 * @param row	the row index
	 * @param label	the column label
	 * @return the element located on column label at index row
	 * @throws java.lang.IllegalArgumentException if label is not an existing column label in this dataframe
	 * @since 0.3.0
	 */
	public Object get(int row, String label) throws IllegalArgumentException {
		return get(row, labelToIndexStrict(label));
	}

	/**
	 * Access the label of a column.
	 *
	 * @param column the column index
	 * @return the label of the column at the given index
	 */
	public String getLabel(int column) {
		return columns.get(column).getLabel();
	}

	/**
	 * Access the data type of a column from the column index.
	 *
	 * @param column the column index
	 * @return the data type of the column at the given index
	 */
	public Class<?> getType(int column) {
		return columns.get(column).getType();
	}

	/**
	 * Access the data type of a column from the column label.
	 *
	 * @param label the column label
	 * @return the data type of the column labeled as label
	 * @throws java.lang.IllegalArgumentException if label is not an existing column label in this dataframe
	 * @since 0.3.0
	 */
	public Class<?> getType(String label) throws IllegalArgumentException {
		return getType(labelToIndexStrict(label));
	}

	/**
	 * Add a row of data to this dataframe.
	 * Elements in the specified row should be in the same order as the columns in this dataframe,
	 * and of the same type as the data type of their corresponding column.
	 *
	 * @param row the array of row data to add
	 * @throws java.lang.IllegalArgumentException if an element in the row is not of the appropriate type
	 * @since 0.3.0
	 */
	public void addRow(Object row[]) throws IllegalArgumentException {
		for (int i=0; i<columns.size(); i++) {
			if (i < row.length) {
				columns.get(i).add(row[i]);
			} else {
				columns.get(i).add(null);
			}
		}
		rowCount++;
	}

	/**
	 * Returns the number of rows in this dataframe.
	 * Only data rows are counted, not labels.
	 *
	 * @return the number of rows in this dataframe
	 * @since 0.3.0
	 */
	public int rowCount() {
		return rowCount;
	}

	/**
	 * Returns the number of columns in this dataframe.
	 *
	 * @return the number of columns in this dataframe
	 * @since 0.3.0
	 */
	public int columnCount() {
		return columns.size();
	}

	/**
	 * Set the viewer to be used by this dataframe.
	 *
	 * @param viewer viewer to be used by this dataframe
	 * @since 0.3.0
	 */
	public void setViewer(DataframeViewer viewer) {
		this.viewer = viewer;
	}

	/**
	 * Returns a string representation of this entire dataframe.
	 * Uses the viewer set (or the default one) to get the representation.
	 *
	 * @return a string representation of this entire dataframe
	 * @since 0.3.0
	 */
	public String view() {
		return viewer.view(this);
	}

	/**
	 * Returns a string representation of the first rows of this dataframe.
	 * Uses the viewer set (or the default one) to get the representation.
	 *
	 * @return a string representation of the first rows of this dataframe
	 * @since 0.3.0
	 */
	public String head() {
		return viewer.head(this);
	}

	/**
	 * Returns a string representation of the first rows of this dataframe with the specified number of rows.
	 * Uses the viewer set (or the default one) to get the representation.
	 *
	 * @param num the number of rows to display
	 * @return a string representation of the first rows of this dataframe
	 * @since 0.3.0
	 */
	public String head(int num) {
		return viewer.head(this, num);
	}

	/**
	 * Returns a string representation of the last rows of this dataframe.
	 * Uses the viewer set (or the default one) to get the representation.
	 *
	 * @return a string representation of the last rows of this dataframe
	 * @since 0.3.0
	 */
	public String tail() {
		return viewer.tail(this);
	}

	/**
	 * Returns a string representation of the last rows of this dataframe with the specified number of rows.
	 * Uses the viewer set (or the default one) to get the representation.
	 *
	 * @param num the number of rows to display
	 * @return a string representation of the last rows of this dataframe
	 * @since 0.3.0
	 */
	public String tail(int num) {
		return viewer.tail(this, num);
	}

	/**
	 * Returns a row subsection of a dataframe based on an array of indexes.
	 * 
	 * @param indexes the array of row indexes
	 * @return the new dataframe based on the array of indexes
	 * @throws java.lang.IllegalArgumentException if one of the indexes is invalid
	 * @since 0.4.0
	 */
	public Dataframe selectRows(int[] indexes) {
		Object[] row = new Object[this.columns.size()];
		Dataframe newDataframe = this.extractColumns();
		
		for(Integer i : indexes) {
			for(int j=0; j<this.columns.size(); j++) {
				row[j] = this.get(i, j);
			}
			newDataframe.addRow(row);
		}
		
		return newDataframe;
	}

	/**
	 * Returns a column subsection of a dataframe based on an array of labels.
	 * 
	 * @param labels the array of column labels
	 * @return the new dataframe based on the array of labels
	 * @throws java.lang.IllegalArgumentException if one of the labels is invalid
	 * @since 0.4.0
	 */
	public Dataframe selectColumns(String[] labels) throws IllegalArgumentException {
		Dataframe newDataframe = this.extractColumns(labels);
		int[] index = range(0, this.rowCount);
		newDataframe = this.extractRows(index, newDataframe);
		return newDataframe;
	}

	/**
	 * Returns a subsection of a dataframe based of a list of indexes and labels.
	 * 
	 * @param indexes the array of row indexes
	 * @param labels the array of labels
	 * @return the new dataframe based on the array of indexes and the array of labels
	 * @throws java.lang.IllegalArgumentException if one of the indexes or labels is invalid
	 * @since 0.4.0
	 */
	public Dataframe crossSelect(int[] indexes, String[] labels) throws IllegalArgumentException {
		Dataframe newDataframe = this.extractColumns(labels);
		newDataframe = this.extractRows(indexes, newDataframe);
		return newDataframe;
	}
	
	/**
	 * Returns a new dataframe with only the rows that have a value equals to a specified value.
	 * 
	 * @param label the label of the column to compare
	 * @param val the value to compare
	 * @return a new dataframe made up of the right values
	 */
	public Dataframe selectEquals(String label, Object val) {
		Dataframe newDataframe = this.extractColumns();
		for (int i=0; i<this.rowCount; i++) {
			if(this.get(i, label).equals(val)) {
				newDataframe.addRow(getRow(i));
			}
		}
		return newDataframe;
	}
	
	public Dataframe selectNotEquals(String label, Object val) {
		Dataframe newDataframe = this.extractColumns();
		for (int i=0; i<this.rowCount; i++) {
			if(!this.get(i, label).equals(val)) {
				newDataframe.addRow(getRow(i));
			}
		}
		return newDataframe;
	}
	
	/**
	 * Returns a new dataframe with only the rows that have a value greater than a specified value.
	 * 
	 * @param label the label of the column to compare
	 * @param val the value to compare
	 * @param strict a boolean, if true then the comparison is strict.
	 * @return a new dataframe made up of the right values
	 */
	@SuppressWarnings("unchecked")
	public <T> Dataframe selectGreaterThan(String label, Comparable<T> val, Boolean strict) {
		Dataframe newDataframe = this.extractColumns();
		for(int i=0; i<this.rowCount; i++) {
			if(strict) {
				if(val.compareTo((T) get(i, label)) < 0) {
					newDataframe.addRow(getRow(i));
				}
			}
			else {
				if(val.compareTo((T) get(i, label)) <= 0) {
					newDataframe.addRow(getRow(i));
				}
			}
		}
		return newDataframe;
	}
	
	/**
	 * Returns a new dataframe with only the rows that have a value greater than a specified value.
	 * 
	 * @param label the label of the column to compare
	 * @param val the value to compare
	 * @param strict a boolean, if true then the comparison is strict.
	 * @return a new dataframe made up of the right values
	 */
	@SuppressWarnings("unchecked")
	public <T> Dataframe selectLessThan(String label, Comparable<T> val, Boolean strict) {
		Dataframe newDataframe = this.extractColumns();
		for(int i=0; i<this.rowCount; i++) {
			if(strict) {
				if(val.compareTo((T) get(i, label)) > 0) {
					newDataframe.addRow(getRow(i));
				}
			}
			else {
				if(val.compareTo((T) get(i, label)) >= 0) {
					newDataframe.addRow(getRow(i));
				}
			}
		}
		return newDataframe;
	}
	
	/**
	 * Returns the row of the specified index.
	 * 
	 * @param index the index of the row
	 * @return an array of the data of the specified row
	 */
	public Object[] getRow(int index) {
		Object[] row = new Object[this.columns.size()];
		for(int i=0; i<this.columns.size(); i++) {
			row[i] = get(index, i);
		}
		return row;
	}

	@Override
	public String toString() {
		return view();
	}


	/*---------------------------------*/
	/*-----    Private methods    -----*/
	/*---------------------------------*/

	/*
	 * Private default constructor for a Dataframe.
	 */
	private Dataframe() {
		columns = new ArrayList<>();
		rowCount = 0;
		viewer = new TabularDataframeViewer();
	}

	/*
	 * Add to this Dataframe a labeled column of given data type.
	 */
	private void addColumn(Class<?> type, String label) {
		columns.add(new Column(type, label));
	}

	/*
	 * Get index of first column labeled by label or -1 if label cannot be found.
	 */
	private int labelToIndex(String label) {
		for (int i=0; i<columns.size(); i++) {
			if (columns.get(i).getLabel().equals(label)) {
				return i;
			}
		}
		return -1;
	}

	/*
	 * Get index of first column labeled by label, throw exception if label cannot be found.
	 */
	private int labelToIndexStrict(String label) throws IllegalArgumentException {
		int index = labelToIndex(label);
		if (index == -1) {
			throw new IllegalArgumentException("Label " + label + " does not belong to this dataframe");
		}
		return index;
	}

	// Extracts the columns of a dataframe based of an array of labels
	private Dataframe extractColumns(String[] label) throws IllegalArgumentException {
		int index;
		Dataframe newDataframe = new Dataframe();
		for(String s : label) {
			index = this.labelToIndexStrict(s);
			newDataframe.addColumn(this.columns.get(index).getType(), this.columns.get(index).getLabel());
		}
		return newDataframe;
	}
	
	// Extracts all the columns of a dataframe
	private Dataframe extractColumns() {
		Dataframe newDataframe = new Dataframe();
		for (int i=0; i<this.columns.size(); i++) {
			newDataframe.addColumn(this.columns.get(i).getType(), this.columns.get(i).getLabel());
		}
		return newDataframe;
	}
	
	// Extracts the rows of a dataframe based of an array of labels
	private Dataframe extractRows(int[] index, Dataframe data) throws IllegalArgumentException {
		Object[] row = new Object[data.columns.size()];
		for(int i : index) {
			for(int j=0; j<data.columns.size(); j++) {
				row[j] = get(i, labelToIndex(data.getLabel(j)));
			}
			data.addRow(row);
		}
		return data;
	}

	// Returns an array of int ranging from begin to end
	private int[] range(int begin, int end) {
		int[] array = new int[end];
		for(int i=begin; i<end; i++) {
			array[i] = i;
		}
		return array;
	}
}
