package fr.uga.fran;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Dataframe {
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
	
	/** Public methods **/
	
	public Dataframe(String labels[], Object[] ...data) {
		columns = new ArrayList<>();
		int numLines = 0;
		
		// Ajout des colonnes
		for (int i=0; i<data.length; i++) {
			String label = "";
			if (i < labels.length) {
				label = labels[i];
			}
			addColumn(data[i][0].getClass(), label);
			
			if (data[i].length > numLines) {
				numLines = data[i].length;
			}
		}
		
		// Ajout des lignes
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
	
	public Dataframe(String CSVFileName) throws FileNotFoundException, InvalidCSVFormatException {
		columns = new ArrayList<>();
		
		CSVParser parser = new CSVParser(CSVFileName);
		
		Object[] labels = parser.readLine();
		if (labels == null) {
			throw new InvalidCSVFormatException("file is empty");
		}
		
		List<Object[]> lines = new ArrayList<>();
		Object[] data;
		while ((data = parser.readLine()) != null) {
			lines.add(data);
			if (data.length != labels.length) {
				throw new InvalidCSVFormatException("invalid number of fields at line " + lines.size());
			}
		}
		
		for (int i=0; i<labels.length; i++) {
			// Cherche le type de la colonne
			int j = 0;
			while (j < lines.size() && lines.get(j)[i] == null) {
				j++;
			}
			if (j >= lines.size()) {
				throw new InvalidCSVFormatException("no data found in column " + i);
			}
			
			addColumn(lines.get(j)[i].getClass(), (String) labels[i]);
		}
		
		for (Object[] line : lines) {
			addLine(line);
		}
	}
	
	public Object get(int line, int column) {
		return columns.get(column).get(line);
	}
	
	public String getLabel(int column) {
		return columns.get(column).getLabel();
	}
	
	public Class<?> getType(int column) {
		return columns.get(column).getType();
	}
	
	public void addLine(Object line[]) {
		for (int i=0; i<columns.size(); i++) {
			if (i < line.length) {
				columns.get(i).add(line[i]);
			} else {
				columns.get(i).add(null);
			}
		}
	}
	
	public Dataframe selectLines(int[] index) {
		int nbCol = this.columns.size();
		Dataframe newDataframe = new Dataframe();
		Object[] row = new Object[nbCol];
		for (int i=0; i<nbCol; i++) {
			newDataframe.addColumn(this.columns.get(i).getType(), this.columns.get(i).getLabel());
		}
		for(Integer i : index) {
			for(int j=0; j<nbCol; j++) {
				row[j] = this.get(i, j);
			}
			newDataframe.addLine(row);
		}
		return newDataframe;
	}
	
	public Dataframe selectColumns(String[] label) {
		
		return null;
	}
	
	/** Private methods **/
	
	private Dataframe() {
		columns = new ArrayList<>();
	}
	
	private void addColumn(Class<?> type, String label) {
		columns.add(new Column(type, label));
	}
}
