package fr.uga.fran;

/**
 * A dataframe viewer representing dataframes in simple tabular form.
 * Each column are separated by a unique string that can be defined by the user.
 * The first row of any representation shows the labels, and then each row is directly
 * followed by a line separator.
 * 
 * @author ANDRE Stephen
 * @author FREBY Laura
 * @since 0.3.0
 * @see fr.uga.fran.Dataframe
 */
public class TabularDataframeViewer implements DataframeViewer {
	private String separator;
	private int sampleSize;
	
	/**
	 * Constructs a TabularDataframeViewer with default values.
	 * The default value for the separator between columns is " ".
	 * The default value for the number of rows to display with methods head(Dataframe) and tail(Dataframe) is 5.
	 */
	public TabularDataframeViewer() {
		separator = " ";
		sampleSize = 5;
	}
	
	@Override
	public String view(Dataframe dataframe) {
		return dataframeToString(dataframe, 0, dataframe.rowCount()-1);
	}

	@Override
	public String head(Dataframe dataframe) {
		return head(dataframe, sampleSize);
	}

	@Override
	public String head(Dataframe dataframe, int num) {
		return dataframeToString(dataframe, 0, num-1);
	}

	@Override
	public String tail(Dataframe dataframe) {
		return tail(dataframe, sampleSize);
	}

	@Override
	public String tail(Dataframe dataframe, int num) {
		return dataframeToString(dataframe, dataframe.rowCount()-num, dataframe.rowCount()-1);
	}
	
	/**
	 * Set the separator string used between the columns of a dataframe.
	 * 
	 * @param separator the separator to be used
	 */
	public void setSeparator(String separator) {
		this.separator = separator;
	}
	
	/**
	 * Set the number of rows displayed by the methods head(Dataframe) and tail(Dataframe).
	 * 
	 * @param sampleSize the number of rows to display
	 */
	public void setSampleSize(int sampleSize) {
		this.sampleSize = sampleSize;
	}
	
	
	/*---------------------------------*/
	/*-----    Private methods    -----*/
	/*---------------------------------*/
	
	/*
	 * Returns a string of labels and rows from start to end included of the specified dataframe.
	 */
	private String dataframeToString(Dataframe dataframe, int start, int end) {
		// Ensure we don't have indexes out of bounds
		if (start < 0) {
			start = 0;
		}
		if (end >= dataframe.rowCount()) {
			end = dataframe.rowCount() - 1;
		}
		
		// Retrieve columns width first
		int columnsWidth[] = columnsWidth(dataframe, start, end);
		
		// Add the labels
		String result = labelsToString(dataframe, columnsWidth) + System.lineSeparator();
		
		// Add all rows specified
		for (int i=start; i<=end; i++) {
			result += rowToString(dataframe, columnsWidth, i) + System.lineSeparator();
		}
		
		return result;
	}
	
	/*
	 * Returns an array of all columns width from the specified dataframe.
	 */
	private int[] columnsWidth(Dataframe dataframe, int start, int end) {
		int columnsWidth[] = new int[dataframe.columnCount()];
		
		// For each column
		for (int i=0; i<dataframe.columnCount(); i++) {
			// Retrieve max width of column i
			columnsWidth[i] = maxWidth(dataframe, i, start, end);
			
			// Real max width is max between max width and label width
			if (dataframe.getLabel(i).length() > columnsWidth[i]) {
				columnsWidth[i] = dataframe.getLabel(i).length();
			}
		}
		
		return columnsWidth;
	}
	
	/*
	 * Returns the max width of a column between row start and end.
	 */
	private int maxWidth(Dataframe dataframe, int column, int start, int end) {
		int width = 0;
		
		// For each row, search the maximum width for column i
		for (int i=start; i<=end; i++) {
			Object object = dataframe.get(i, column);
			
			if (object != null) {
				String word = object.toString();
				
				// Update maximum width
				if (word.length() > width) {
					width = word.length();
				}
			}
		}
		
		return width;
	}
	
	/*
	 * Returns a string of all labels of the specified dataframe.
	 */
	private String labelsToString(Dataframe dataframe, int[] columnsWidth) {
		// Any line begins with a separator
		String labels = separator;
		
		// For each column
		for (int i=0; i<dataframe.columnCount(); i++) {
			String label = dataframe.getLabel(i);
			
			// Padding to align columns
			label = rightPadding(label, columnsWidth[i]);
			
			labels += label + separator;
		}
		
		return labels;
	}
	
	/*
	 * Returns a string of the index-th row in the specified dataframe.
	 */
	private String rowToString(Dataframe dataframe, int[] columnsWidth, int index) {
		// Any line begins with a separator
		String line = separator;
		
		// For each column
		for (int i=0; i<dataframe.columnCount(); i++) {
			// Get the string representation of the element with padding
			String data = objectToString(dataframe.get(index, i), dataframe.getType(i), columnsWidth[i]);
			
			line += data + separator;
		}
		
		return line;
	}
	
	/*
	 * Returns a string representation of an object with padding according to the type.
	 */
	private String objectToString(Object object, Class<?> type, int size) {
		String s = new String();
		
		if (object != null) {
			s = object.toString();
		}
		
		// Numbers are aligned to the right, any other type is aligned to the left
		if (object instanceof Number) {
			s = leftPadding(s, size);
		} else {
			s = rightPadding(s, size);
		}
		
		return s;
	}
	
	/*
	 * Returns a string of length size with spaces to the left of s.
	 */
	private String leftPadding(String s, int size) {
		String result = s;
		for (int i=0; i<size-s.length(); i++) {
			result = " " + result;
		}
		return result;
	}
	
	/*
	 * Returns a string of length size with spaces to the right of s.
	 */
	private String rightPadding(String s, int size) {
		String result = s;
		for (int i=0; i<size-s.length(); i++) {
			result += " ";
		}
		return result;
	}

}
