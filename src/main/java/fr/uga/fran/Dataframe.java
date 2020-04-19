package fr.uga.fran;

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
			
			if (element != null) {
				String s = type.cast(element).toString();
			}
		}
		
		public Object get(int index) { return list.get(index); }
		public Class<?> getType() { return type; }
		public String getLabel() { return label; }
	}
	
	private List<Column> columns;
	private int size;
	
	/** Public methods **/
	
	public Dataframe(String labels[], Object[] ...data) {
		columns = new ArrayList<>();
		size = 0;
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
	
	public Dataframe(String CSVFileName) {
		
	}
	
	public Object get(int line, int column) {
		return columns.get(column).get(line);
	}
	
	public String getLabel(int column) {
		return columns.get(column).getLabel();
	}
	
	public void addLine(Object line[]) {
		for (int i=0; i<columns.size(); i++) {
			if (i < line.length) {
				columns.get(i).add(line[i]);
			} else {
				columns.get(i).add(null);
			}
		}
		size++;
	}
	
	/** Private methods **/
	
	private void addColumn(Class<?> type, String label) {
		columns.add(new Column(type, label));
	}
}
