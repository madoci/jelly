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
		String data = "";
		int i = 0;
		
		// Read the line character by character
		while (i < line.length()) {
			switch (line.charAt(i)) {
			case ',':
				// If we find a comma, we convert the current data into the correct type,
				// add it to the list and reset the data to an empty string
				objects.add(processData(data));
				data = "";
				break;
			case '"':
				// If we find an opening double quote, all characters until the next closing 
				// double quote will be part of the data.
				i++;
				while (i < line.length()) {
					if (line.charAt(i) == '"') {
						// If a double quote is directly followed by another one, it is
						// interpreted as the double quote character.
						// Otherwise, it is a closing double quote.
						if (i+1 < line.length() && line.charAt(i+1) == '"') {
							i++;
						} else {
							break;
						}
					}
					data += line.charAt(i);
					i++;
				}
				// If we're at the end of the line and no closing double quote has been found,
				// the format of the CSV file is invalid.
				if (i >= line.length()) {
					throw new InvalidCSVFormatException("missing '\"' at line " + lineNumber);
				}
				break;
			default:
				data += line.charAt(i);
				break;
			}
			i++;
		}
		
		// Add the remaining data to the list
		objects.add(processData(data));
		
		return objects.toArray();
	}
	
	
	/*---------------------------------*/
	/*-----    Private methods    -----*/
	/*---------------------------------*/
	
	// Convert a String object into the interpreted type (Integer, Double or String)
	private Object processData(String data) {
		Object obj = null;
		
		if (data.length() > 0) {
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
		}
		
		return obj;
	}
}
