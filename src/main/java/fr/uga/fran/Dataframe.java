package fr.uga.fran;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * A two-dimensional table with heterogeneous data types.
 * Each column is labeled with a name and contains data of a single type.
 * Two columns of the same dataframe can contain data of different types.
 *
 * @author ANDRE Stephen
 * @author FREBY Laura
 * @since 0.1.0
 */
public class Dataframe {
	// Class for the labeled columns inside a Dataframe
	private class Column {
		private final Class<?> type;
		private String label;
		private List<Object> list;
		
		// Create a column with a data type and a label
		public Column(Class<?> type, String label) {
			this.type = type;
			this.label = label;
			list = new ArrayList<>();
		}
		
		// Add an element of data type at the end of this column
		public void add(Object element) {
			list.add(element);
		}
		
		public Object get(int index) { return list.get(index); }
		public Class<?> getType() { return type; }
		public String getLabel() { return label; }
	}
	
	private List<Column> columns;
	private int rowCount;
	
	/**
	 * Constructs a dataframe from an array of labels and arrays of columns.
	 *
	 * @param labels the array of labels, in the same order as the columns
	 * @param data variable amount of arrays each containing the content of a column
	 */
	public Dataframe(String labels[], Object[] ...data) {
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
	 *
	 * @param row the array of row data to add
	 */
	public void addRow(Object row[]) {
		for (int i=0; i<columns.size(); i++) {
			if (i < row.length) {
				columns.get(i).add(row[i]);
			} else {
				columns.get(i).add(null);
			}
		}
		rowCount++;
	}
	

	public Dataframe selectRows(int[] index) {
		int nbCol = this.columns.size();
		Dataframe newDataframe = new Dataframe();
		Object[] row = new Object[nbCol];
		for (int i=0; i<nbCol; i++) {
			newDataframe.addColumn(this.columns.get(i).getType(), this.columns.get(i).getLabel());
		}
		for(Integer i : index) {
			for(int j=0; j<nbCol; j++) {
				row[j] = this.get(i, j);
			}
			newDataframe.addRow(row);
		}
		return newDataframe;
	}
	
	public Dataframe selectColumns(String[] label) throws IllegalArgumentException {
		Dataframe newDataframe = new Dataframe();
		int index;
		int cpt=0;
		for(String s : label) {
			index = this.labelToIndexStrict(s);
			newDataframe.addColumn(this.columns.get(index).getType(), this.columns.get(index).getLabel());
			cpt++;
		}
		Object[] row = new Object[cpt];
		for(int i=0; i<this.rowCount; i++) {
			for(int j=0; j<cpt; j++) {
				row[j] = get(i, labelToIndex(newDataframe.getLabel(j)));
			}
			newDataframe.addRow(row);
		}
		return newDataframe;
	}
	
	/**
	 * Returns the number of rows in this dataframe.
	 * Only data rows are counted, not labels.
	 * 
	 * @return the number of rows in this dataframe
	 */
	public int rowCount() {
		return rowCount;
	}
	
	/**
	 * Returns the number of columns in this dataframe.
	 * 
	 * @return the number of columns in this dataframe
	 */
	public int columnCount() {
		return columns.size();
	}
	
	
	/*---------------------------------*/
	/*-----    Private methods    -----*/
	/*---------------------------------*/
	
	// Private default constructor
	private Dataframe() {
		columns = new ArrayList<>();
		rowCount = 0;
	}
	
	// Add to this Dataframe a labeled column of given data type
	private void addColumn(Class<?> type, String label) {
		columns.add(new Column(type, label));
	}
	
	// Get index of first column labeled by label or -1 if label cannot be found
	private int labelToIndex(String label) {
		for (int i=0; i<columns.size(); i++) {
			if (columns.get(i).getLabel().equals(label)) {
				return i;
			}
		}
		return -1;
	}
	
	// Get index of first column labeled by label, throw exception if label cannot be found
	private int labelToIndexStrict(String label) throws IllegalArgumentException {
		int index = labelToIndex(label);
		if (index == -1) {
			throw new IllegalArgumentException("Label " + label + " does not belong to this dataframe");
		}
		return index;
	}
}
