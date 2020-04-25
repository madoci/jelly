package fr.uga.fran;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A CSV parser that can read and parse CSV files.
 * This class uses a Scanner to read the file.
 *
 * @author ANDRE Stephen
 * @author FREBY Laura
 * @since 0.2.0
 */
public class CSVParser {
	/*
	 * A scanner to parse a CSV format string.
	 */
	private class CSVScanner {
		private String string;
		private int position;
		
		/*
		 * Constructs a CSVStringScanner with the specified string to be parsed.
		 */
		public CSVScanner(String string) {
			this.string = string;
			this.position = 0;
		}
		
		/*
		 * Returns true if the string to be parsed has another CSV field.
		 */
		public boolean hasNextField() {
			return position <= string.length();
		}
		
		/*
		 * Advances to the next field and returns the field skipped.
		 */
		public String nextField() throws InvalidCSVFormatException {
			String field = new String();
			
			while (currentChar() != ',') {
				if (currentChar() == '"') {
					position++;
					field = nextQuote();
				} else {
					field += currentChar();
				}
				position++;
			}
			position++;
			
			return field;
		}
		
		/*
		 * Advances to the next not-paired double quote and returns the string skipped.
		 * The string returned stops just before the closing double quote.
		 * After calling this method, the position is set on the closing double quote.
		 */
		private String nextQuote() throws InvalidCSVFormatException {
			String quote = new String();
			
			while (position < string.length()) {
				if (currentChar() == '"') {
					// We check the character right after the double quote
					position++;
					
					// If it's a pair of double quote, we add one and skip it
					if (currentChar() == '"') {
						quote += '"';
					} else {
						// We move one backward to ensure we aim at the closing double quote
						position--;
						break;
					}
				} else {
					quote += currentChar();
				}
				position++;
			}
			
			// If the current character isn't a double quote, the format is invalid
			if (currentChar() != '"') {
				throw new InvalidCSVFormatException("missing '\"' at line " + lineNumber);
			}
			
			return quote;
		}
		
		/*
		 * Returns the current character, or ',' if the scanner is passed the end of the string
		 */
		private char currentChar() {
			if (position >= string.length()) {
				return ',';
			}
			return string.charAt(position);
		}
	}
	
	Scanner scanner;	// Scanner to read the file
	int lineNumber;		// Current line number for debug only
	
	/**
	 * Constructs a CSVParser from the specified file.
	 *
	 * @param pathname the path of the CSV file to parse
	 * @throws java.io.FileNotFoundException if the file at pathname is not found
	 */
	public CSVParser(String pathname) throws FileNotFoundException {
		scanner = new Scanner(new File(pathname));
		lineNumber = 0;
	}
	
	/**
	 * Parses the current line of the CSV file, and advances to the next line.
	 * First call of this method will parse the first line of the file.
	 *
	 * @return an array of all data read on the current line, or null if
	 * @throws fr.uga.fran.InvalidCSVFormatException if the file misses a closing double quote on a line
	 */	
	public Object[] readLine() throws InvalidCSVFormatException {
		// If the scanner is at the end of the file, return null
		if (!scanner.hasNextLine()) {
			return null;
		}
		
		// Retrieves current line and advances the scanner to the next line
		String line = scanner.nextLine();
		lineNumber++;
		
		List<Object> objects = new ArrayList<>();
		CSVScanner lineScanner = new CSVScanner(line);
		
		while (lineScanner.hasNextField()) {
			String field = lineScanner.nextField();
			objects.add(processData(field));
		}
		
		return objects.toArray();
	}
	
	
	/*---------------------------------*/
	/*-----    Private methods    -----*/
	/*---------------------------------*/
	
	/*
	 * Converts a String object into the interpreted type (Integer, Double or String).
	 * The interpreted type is returned as an Object.
	 */
	private Object processData(String data) {
		// If data is empty, we return null
		if (data.length() == 0) {
			return null;
		}
		
		Object obj;
		
		// We first try to convert it into an Integer
		try {
			obj = Integer.valueOf(data);
		} catch (Exception notInt) {
			// If it fails, we then try to convert it into a Double
			try {
				obj = Double.valueOf(data);
			} catch (Exception notDouble) {
				// If it fails again, we keep it as a String
				obj = data;
			}
		}
		
		return obj;
	}
}
