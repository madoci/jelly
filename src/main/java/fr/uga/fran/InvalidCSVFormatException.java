package fr.uga.fran;

/**
 * Exception thrown when trying to read an invalid CSV file.
 *
 * @author ANDRE Stephen
 * @author FREBY Laura
 * @since 0.2.0
 * @see CSVParser
 * @see Dataframe
 */
public class InvalidCSVFormatException extends Exception {

	private static final long serialVersionUID = -930853185148160673L;

	/**
	 * Constructs an InvalidCSVFormatException with the specified detail message.
	 *
	 * @param message the detail message
	 */
	public InvalidCSVFormatException(String message) {
		super(message);
	}
}
