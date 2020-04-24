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
		CSVStringScanner lineScanner = new CSVStringScanner(line);
		
		while (lineScanner.hasNextField()) {
			String field = lineScanner.nextField();
			objects.add(processData(field));
		}
		
		return objects.toArray();
	}
	
	
	/*---------------------------------*/
	/*-----    Private methods    -----*/
	/*---------------------------------*/
	
	private class CSVStringScanner {
		private String string;
		private int position;
		
		public CSVStringScanner(String string) {
			this.string = string;
			this.position = 0;
		}
		
		public boolean hasNextField() {
			return position <= string.length();
		}
		
		public String nextField() throws InvalidCSVFormatException {
			String field = new String();
			
			while (position < string.length() && currentChar() != ',') {
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
		
		private String nextQuote() throws InvalidCSVFormatException {
			String quote = new String();
			
			while (position < string.length()) {
				if (currentChar() == '"') {
					position++;
					if (position < string.length() && currentChar() == '"') {
						quote += '"';
					} else {
						position--;
						break;
					}
				} else {
					quote += currentChar();
				}
				position++;
			}
			
			if (position >= string.length() || currentChar() != '"') {
				throw new InvalidCSVFormatException("missing '\"' at line " + lineNumber);
			}
			
			return quote;
		}
		
		private char currentChar() { return string.charAt(position); }
	}
	
	// Convert a String object into the interpreted type (Integer, Double or String)
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
