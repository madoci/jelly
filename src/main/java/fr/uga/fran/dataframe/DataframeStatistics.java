package fr.uga.fran.dataframe;

import fr.uga.fran.math.ArrayStatistics;

/**
 * A class to calculate statistical information on a dataframe.
 * This class uses the static methods of ArrayStatistics to calculate
 * statistics on an array of objects.
 *
 * @author ANDRE Stephen
 * @author FREBY Laura
 * @since 1.0.0
 * @see fr.uga.fran.math.ArrayStatistics
 */
public class DataframeStatistics {
	
	private Dataframe dataframe;
	
	/**
	 * Constructs a DataframeStatistics associated to the specified dataframe.
	 * 
	 * @param dataframe the dataframe to perform statistics on
	 */
	public DataframeStatistics(Dataframe dataframe) {
		this.dataframe = dataframe;
	}
	
	/**
	 * Returns the row index of the lowest value in the specified column.
	 * 
	 * @param column the column index to get the values from
	 * @return the row index of the lowest value in the specified column
	 * @throws java.lang.IllegalArgumentException if no operator is associated to the data type of the column
	 */
	public int argmin(int column) throws IllegalArgumentException {
		return ArrayStatistics.argmin(dataframe.getColumn(column));
	}
	
	/**
	 * Returns the row index of the lowest value in the column labeled by the specified label.
	 * 
	 * @param label the label of the column to get the values from
	 * @return the row index of the lowest value in the specified column
	 * @throws java.lang.IllegalArgumentException if no operator is associated to the data type of the column
	 */
	public int argmin(String label) throws IllegalArgumentException {
		return ArrayStatistics.argmin(dataframe.getColumn(label));
	}

	/**
	 * Returns the row index of the greatest value in the specified column.
	 * 
	 * @param column the column index to get the values from
	 * @return the row index of the greatest value in the specified column
	 * @throws java.lang.IllegalArgumentException if no operator is associated to the data type of the column
	 */
	public int argmax(int column) throws IllegalArgumentException {
		return ArrayStatistics.argmax(dataframe.getColumn(column));
	}

	/**
	 * Returns the row index of the greatest value in the column labeled by the specified label.
	 * 
	 * @param label the label of the column to get the values from
	 * @return the row index of the greatest value in the specified column
	 * @throws java.lang.IllegalArgumentException if no operator is associated to the data type of the column
	 */
	public int argmax(String label) throws IllegalArgumentException {
		return ArrayStatistics.argmax(dataframe.getColumn(label));
	}

	/**
	 * Returns the lowest value in the specified column.
	 * 
	 * @param column the column index to get the values from
	 * @return the lowest value in the specified column
	 * @throws java.lang.IllegalArgumentException if no operator is associated to the data type of the column
	 */
	public Object min(int column) throws IllegalArgumentException {
		return ArrayStatistics.min(dataframe.getColumn(column));
	}

	/**
	 * Returns the lowest value in the specified column.
	 * 
	 * @param label the label of the column to get the values from
	 * @return the lowest value in the specified column
	 * @throws java.lang.IllegalArgumentException if no operator is associated to the data type of the column
	 */
	public Object min(String label) throws IllegalArgumentException {
		return ArrayStatistics.min(dataframe.getColumn(label));
	}

	/**
	 * Returns the greatest value in the specified column.
	 * 
	 * @param column the column index to get the values from
	 * @return the greatest value in the specified column
	 * @throws java.lang.IllegalArgumentException if no operator is associated to the data type of the column
	 */
	public Object max(int column) throws IllegalArgumentException {
		return ArrayStatistics.max(dataframe.getColumn(column));
	}

	/**
	 * Returns the greatest value in the specified column.
	 * 
	 * @param label the label of the column to get the values from
	 * @return the greatest value in the specified column
	 * @throws java.lang.IllegalArgumentException if no operator is associated to the data type of the column
	 */
	public Object max(String label) throws IllegalArgumentException {
		return ArrayStatistics.max(dataframe.getColumn(label));
	}
	
	/**
	 * Returns the sum of all elements in the specified column.
	 * 
	 * @param column the column index to get the values from
	 * @return the sum of all elements in the specified column
	 * @throws java.lang.IllegalArgumentException if no operator is associated to the data type of the column
	 */
	public Object sum(int column) throws IllegalArgumentException {
		return ArrayStatistics.sum(dataframe.getColumn(column));
	}

	/**
	 * Returns the sum of all elements in the specified column.
	 * 
	 * @param label the label of the column to get the values from
	 * @return the sum of all elements in the specified column
	 * @throws java.lang.IllegalArgumentException if no operator is associated to the data type of the column
	 */
	public Object sum(String label) throws IllegalArgumentException {
		return ArrayStatistics.sum(dataframe.getColumn(label));
	}

	/**
	 * Returns the mean of all elements in the specified column.
	 * 
	 * @param column the column index to get the values from
	 * @return the mean of all elements in the specified column
	 * @throws java.lang.IllegalArgumentException if no operator is associated to the data type of the column
	 */
	public Object mean(int column) throws IllegalArgumentException {
		return ArrayStatistics.mean(dataframe.getColumn(column));
	}

	/**
	 * Returns the mean of all elements in the specified column.
	 * 
	 * @param label the label of the column to get the values from
	 * @return the mean of all elements in the specified column
	 * @throws java.lang.IllegalArgumentException if no operator is associated to the data type of the column
	 */
	public Object mean(String label) throws IllegalArgumentException {
		return ArrayStatistics.mean(dataframe.getColumn(label));
	}

	/**
	 * Returns the median of all elements in the specified column.
	 * 
	 * @param column the column index to get the values from
	 * @return the median of all elements in the specified column
	 * @throws java.lang.IllegalArgumentException if no operator is associated to the data type of the column
	 */
	public Object median(int column) throws IllegalArgumentException {
		return ArrayStatistics.median(dataframe.getColumn(column));
	}

	/**
	 * Returns the median of all elements in the specified column.
	 * 
	 * @param label the label of the column to get the values from
	 * @return the median of all elements in the specified column
	 * @throws java.lang.IllegalArgumentException if no operator is associated to the data type of the column
	 */
	public Object median(String label) throws IllegalArgumentException {
		return ArrayStatistics.median(dataframe.getColumn(label));
	}
}
