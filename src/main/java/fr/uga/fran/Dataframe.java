package fr.uga.fran;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * A two-dimensional table with heterogeneous data types. 
 * Each column is labeled with a name and contains data of a single type.
 * Two columns of the same Dataframe can contain data of different types.
 * @author ANDRE Stephen
 * @author FREBY Laura
 * @version 
 * @since 0.1.0
 */
public class Dataframe {
	// Class for the labeled columns inside a Dataframe
	private class Column {
		private final Class<?> type;
		private String label;
		private List<Object> list;
		
		public Column(Class<?> type, String label) {
			this.type = type;
			this.label = label;
			list = new ArrayList<>();
		}
		
		public void add(Object element) {
			list.add(element);
		}
		
		public Object get(int index) { return list.get(index); }
		public Class<?> getType() { return type; }
		public String getLabel() { return label; }
	}
	
	private List<Column> columns;
	
	/**
	 * Constructs a Dataframe from an array of labels and arrays of columns. 
	 * @param labels	the array of labels, in the same order as the columns
	 * @param data		variable amount of arrays each containing the content of a column
	 */
	public Dataframe(String labels[], Object[] ...data) {
		columns = new ArrayList<>();
		int numLines = 0;
		
		// Add columns
		for (int i=0; i<data.length; i++) {
			// If no label is provided, the label is an empty string
			String label = "";
			if (i < labels.length) {
				label = labels[i];
			}
			
			// The data type of the column is retrieved from the first element of this column
			addColumn(data[i][0].getClass(), label);
			
			// Keep track of the maximum number of lines between all columns
			if (data[i].length > numLines) {
				numLines = data[i].length;
			}
		}
		
		// Add lines
		for (int i=0; i<numLines; i++) {
			Object line[] = new Object[columns.size()];
			for (int j=0; j<columns.size(); j++) {
				if (i < data[j].length) {
					line[j] = data[j][i];
				} else {
					line[j] = null;
				}
			}
			addLine(line);
		}
	}
	
	/**
	 * Constructs a Dataframe from a CSV file. 
	 * @param pathname	pathname of the CSV file to use
	 * @throws FileNotFoundException	if the file at pathname is not found
	 * @throws InvalidCSVFormatException	if the file does not follow CSV format
	 * @since 0.2.0
	 */
	public Dataframe(String pathname) throws FileNotFoundException, InvalidCSVFormatException {
		columns = new ArrayList<>();
		
		CSVParser parser = new CSVParser(pathname);
		
		// First line of the file contains the labels
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
		
		// Add lines
		for (Object[] line : lines) {
			addLine(line);
		}
	}
	
	/**
	 * Access a single element from a pair of line/column indexes. 
	 * @param line	the line index 
	 * @param column	the column index
	 * @return	the element located at indexes (line, column)
	 */
	public Object get(int line, int column) {
		return columns.get(column).get(line);
	}
	
	/**
	 * Access the label of a column. 
	 * @param column	the column index
	 * @return	the label of the column at the given index
	 */
	public String getLabel(int column) {
		return columns.get(column).getLabel();
	}
	
	/**
	 * Access the data type of a column from the column index. 
	 * @param column	the column index
	 * @return	the data type of the column at the given index
	 */
	public Class<?> getType(int column) {
		return columns.get(column).getType();
	}
	
	/**
	 * Add a line of data to this Dataframe. 
	 * @param line	the array of line data to add
	 */
	public void addLine(Object line[]) {
		for (int i=0; i<columns.size(); i++) {
			if (i < line.length) {
				columns.get(i).add(line[i]);
			} else {
				columns.get(i).add(null);
			}
		}
	}
	
	
	/** Private methods **/
	
	// Add to this Dataframe a labeled column of given data type
	private void addColumn(Class<?> type, String label) {
		columns.add(new Column(type, label));
	}
}
