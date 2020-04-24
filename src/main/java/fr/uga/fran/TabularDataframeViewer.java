package fr.uga.fran;

public class TabularDataframeViewer implements DataframeViewer {
	
	
	public TabularDataframeViewer() {
		
	}

	@Override
	public String view(Dataframe dataframe) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String head(Dataframe dataframe) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String head(Dataframe dataframe, int num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String tail(Dataframe dataframe) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String tail(Dataframe dataframe, int num) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/*---------------------------------*/
	/*-----    Private methods    -----*/
	/*---------------------------------*/
	
	private int[] columnsWidth(Dataframe dataframe) {
		int columnsWidth[] = new int[dataframe.columnCount()];
		
		for (int i=0; i<dataframe.columnCount(); i++) {
			columnsWidth[i] = 0;
			for (int j=0; j<dataframe.lineCount(); i++) {
				Object object = dataframe.get(j, i);
				
				if (object != null) {
					String word = object.toString();
					if (word.length() > columnsWidth[i]) {
						columnsWidth[i] = word.length();
					}
				}
			}
		}
		
		return columnsWidth;
	}
	
	private String labelsToString(Dataframe dataframe, int[] columnsWidth) {
		String labels = new String();
		
		for (int i=0; i<dataframe.columnCount(); i++) {
			String label = dataframe.getLabel(i);
			
			// Padding to align columns		
			label = rightPadding(label, columnsWidth[i]);
			
			labels += label + " ";
		}
		
		return labels;
	}
	
	private String lineToString(Dataframe dataframe, int[] columnsWidth, int index) {
		String line = new String();
		
		for (int i=0; i<columns.size(); i++) {
			String data = new String();
			
			Object obj = columns.get(i).get(index);
			if (obj != null) {
				data += columns.get(i).getType().cast(obj);
			}
			
			// Remplissage pour l'alignement des colonnes
			if (columns.get(i).getType() == String.class) {
				data = rightPadding(data, columns.get(i).getWidth());
			} else {
				data = leftPadding(data, columns.get(i).getWidth());
			}
			
			line += data + " ";
		}
		
		return line;
	}
	
	private String leftPadding(String s, int size) {
		String res = s;
		for (int i=0; i<size-s.length(); i++) {
			res = " " + res;
		}
		return res;
	}
	
	private String rightPadding(String s, int size) {
		String res = s;
		for (int i=0; i<size-s.length(); i++) {
			res += " ";
		}
		return res;
	}

}
