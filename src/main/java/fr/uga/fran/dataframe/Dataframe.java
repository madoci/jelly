package fr.uga.fran.dataframe;

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
 * @see fr.uga.fran.dataframe.Column
 * @see fr.uga.fran.dataframe.DataframeViewer
 * @see fr.uga.fran.dataframe.TabularDataframeViewer
 * @see fr.uga.fran.dataframe.DataframeSelection
 */
public class Dataframe {

	private List<Column> columns;
	private int rowCount;
	private DataframeViewer viewer;
	private DataframeSelection selection;
	private DataframeStatistics statistics;

	/**
	 * Constructs a dataframe from an array of labels and an array of data types.
	 *
	 * @param labels the array of labels
	 * @param types the array of data types
	 * @since 1.0.0
	 */
	public Dataframe(String[] labels, Class<?>[] types) {
		initialize(labels, types);
	}

	/**
	 * Constructs a dataframe from an array of labels and arrays of columns.
	 *
	 * @param labels the array of labels, in the same order as the columns
	 * @param data variable amount of arrays each containing the content of a column
	 * @throws java.lang.IllegalArgumentException if a column array is composed of objects
	 * of different types or only null references
	 */
	public Dataframe(String[] labels, Object[] ...data) throws IllegalArgumentException {
		Class<?> types[] = new Class<?>[data.length];
		int numRows = 0;

		// Find types for all columns
		for (int i=0; i<data.length; i++) {
			types[i] = findDataType(data[i]);

			// Keep track of the maximum number of rows
			if (data[i].length > numRows) {
				numRows = data[i].length;
			}
		}

		initialize(labels, types);

		// Add rows
		for (int i=0; i<numRows; i++) {
			Object row[] = new Object[columns.size()];
			for (int j=0; j<columns.size(); j++) {
				// Add the object to the row, or null to fill a column too small
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
	 * @throws fr.uga.fran.dataframe.InvalidCSVFormatException if the file does not follow CSV format
	 * @since 0.2.0
	 */
	public Dataframe(String pathname) throws FileNotFoundException, InvalidCSVFormatException {
		// Create a parser
		CSVParser parser = new CSVParser(pathname);

		// First row of the file contains the labels
		Object[] line = parser.readLine();
		if (line == null) {
			throw new InvalidCSVFormatException("file is empty");
		}

		// Converts the array of Object into an array of String to initialize
		String[] labels = new String[line.length];
		for (int i=0; i<line.length; i++) {
			labels[i] = (String) line[i];
		}

		// Parse and store all lines first
		List<Object[]> rows = new ArrayList<>();
		Object[] data;

		while ((data = parser.readLine()) != null) {
			rows.add(data);
			// All lines must have the same number of fields
			if (data.length != labels.length) {
				throw new InvalidCSVFormatException("invalid number of fields at line " + rows.size());
			}
		}

		// Search for the types of all columns
		Class<?> types[] = new Class<?>[labels.length];

		for (int i=0; i<labels.length; i++) {
			int j = 0;
			while (j < rows.size() && rows.get(j)[i] == null) {
				j++;
			}

			// If all objects in the column are null
			if (j == rows.size()) {
				throw new InvalidCSVFormatException("no data found in column " + i);
			}

			types[i] = rows.get(j)[i].getClass();
		}

		initialize(labels, types);

		// Add rows
		for (Object[] row : rows) {
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
	 * Returns the row of the specified index.
	 *
	 * @param index the index of the row inside this dataframe
	 * @return an array of the specified row data
	 * @since 0.4.0
	 */
	public Object[] getRow(int index) {
		Object[] row = new Object[this.columns.size()];

		for(int i=0; i<this.columns.size(); i++) {
			row[i] = get(index, i);
		}

		return row;
	}
	
	/**
	 * Returns an array of all object in the specified column.
	 * 
	 * @param index the index of the specified column
	 * @return an array of all object in the specified column
	 * @since 1.0.0
	 */
	public Object[] getColumn(int index) {
		return columns.get(index).getArray();
	}

	/**
	 * Returns an array of all object in the column labeled by the specified label.
	 * 
	 * @param label the label of the column
	 * @return an array of all object in the specified column
	 * @throws java.lang.IllegalArgumentException if label is not an existing column label in this dataframe
	 * @since 1.0.0
	 */
	public Object[] getColumn(String label) throws IllegalArgumentException {
		return columns.get(labelToIndexStrict(label)).getArray();
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
	 * Provides a DataframeSelection object to perform selection on this dataframe.
	 *
	 * @return a dataframe selection associated to this dataframe
	 * @since 1.0.0
	 */
	public DataframeSelection select() {
		return selection;
	}
	
	/**
	 * Provides a DataframeStatistics object to perform statistics on this dataframe.
	 * 
	 * @return a dataframe statistics associated to this dataframe
	 * @since 1.0.0
	 */
	public DataframeStatistics stats() {
		return statistics;
	}

	@Override
	public String toString() {
		return view();
	}


	/*---------------------------------*/
	/*-----    Private methods    -----*/
	/*---------------------------------*/

	/*
	 * Initialize this dataframe and creates the columns.
	 */
	private void initialize(String[] labels, Class<?>[] types) {
		columns = new ArrayList<>();
		rowCount = 0;
		viewer = new TabularDataframeViewer();
		selection = new DataframeSelection(this);
		statistics = new DataframeStatistics(this);

		// Add columns
		for (int i=0; i<types.length; i++) {
			// If no label is provided, the label is an empty string
			String label = "";
			if (i < labels.length) {
				label = labels[i];
			}

			addColumn(types[i], label);
		}
	}

	/*
	 * Add to this Dataframe a labeled column of given data type.
	 */
	private void addColumn(Class<?> type, String label) {
		columns.add(new Column(type, label));
	}

	/*
	 * Returns the class of the first non-null object in the specified array.
	 * Throws an IllegalArgumentException if all objects are null.
	 */
	private Class<?> findDataType(Object[] array) throws IllegalArgumentException {
		for (Object object : array) {
			if (object != null) {
				return object.getClass();
			}
		}

		throw new IllegalArgumentException("a column cannot contain only null references");
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

}
