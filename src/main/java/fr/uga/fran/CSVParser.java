package fr.uga.fran;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVParser {
	Scanner scanner;
	int lineNumber;
	
	public CSVParser(String fileName) throws FileNotFoundException {
		scanner = new Scanner(new File(fileName));
		lineNumber = 0;
	}
	
	public Object[] readLine() throws InvalidCSVFormatException {
		if (!scanner.hasNextLine()) {
			return null;
		}
		
		String line = scanner.nextLine();
		lineNumber++;
		
		List<Object> objects = new ArrayList<>();
		String data = "";
		int i = 0;
		while (i < line.length()) {
			switch (line.charAt(i)) {
			case ',':
				objects.add(processData(data));
				data = "";
				break;
			case '"':
				i++;
				while (i < line.length()) {
					if (line.charAt(i) == '"') {
						if (i+1 < line.length() && line.charAt(i+1) == '"') {
							i++;
						} else {
							break;
						}
					}
					data += line.charAt(i);
					i++;
				}
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
		objects.add(processData(data));
		
		return objects.toArray();
	}
	
	private Object processData(String data) {
		Object obj = null;
		
		if (data.length() > 0) {
			// Reconnait les champs de type String, Integer ou Double
			try {
				obj = Integer.valueOf(data);
			} catch (Exception notInt) {
				try {
					obj = Double.valueOf(data);
				} catch (Exception notDouble) {
					obj = data;
				}
			}
		}
		
		return obj;
	}
}
