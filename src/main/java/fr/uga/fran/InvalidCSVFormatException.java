package fr.uga.fran;

public class InvalidCSVFormatException extends Exception {

	private static final long serialVersionUID = -930853185148160673L;

	public InvalidCSVFormatException(String message) {
		super(message);
	}
}
