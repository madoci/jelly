package fr.uga.fran.dataframe;

/**
 * An interface providing methods to get string representations of a Dataframe object.
 * Any class meant to get a string representation of a dataframe must implement this interface
 * in order to be used as a default viewer of a Dataframe object.
 * 
 * @author ANDRE Stephen
 * @author FREBY Laura
 * @since 0.3.0
 * @see fr.uga.fran.dataframe.Dataframe
 * @see fr.uga.fran.dataframe.TabularDataframeViewer
 */
public interface DataframeViewer {
	/**
	 * Returns a string representation of the entire specified dataframe.
	 * 
	 * @param dataframe dataframe to get a representation from
	 * @return a string representation of the entire specified dataframe
	 */
	public String view(Dataframe dataframe);
	
	/**
	 * Returns a string representation of the first rows of the specified dataframe.
	 * The number of rows to display with this method is up to the class implementing this interface.
	 * 
	 * @param dataframe dataframe to get a representation from
	 * @return a string representation of the first rows of the specified dataframe
	 */
	public String head(Dataframe dataframe);
	
	/**
	 * Returns a string representation of the first rows of the specified dataframe with the specified number of rows.
	 * 
	 * @param dataframedataframe to get a representation from
	 * @param num the number of rows from the dataframe to display
	 * @return a string representation of the first rows of the specified dataframe
	 */
	public String head(Dataframe dataframe, int num);
	
	/**
	 * Returns a string representation of the last rows of the specified dataframe.
	 * The number of rows to display with this method is up to the class implementing this interface.
	 * 
	 * @param dataframe dataframe to get a representation from
	 * @return a string representation of the last rows of the specified dataframe
	 */
	public String tail(Dataframe dataframe);
	
	/**
	 * Returns a string representation of the last rows of the specified dataframe with the specified number of rows.
	 * 
	 * @param dataframedataframe to get a representation from
	 * @param num the number of rows from the dataframe to display
	 * @return a string representation of the last rows of the specified dataframe
	 */
	public String tail(Dataframe dataframe, int num);
}
