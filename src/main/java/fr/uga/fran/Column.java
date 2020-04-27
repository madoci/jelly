package fr.uga.fran;

import java.util.ArrayList;
import java.util.List;

/**
 * Labeled list of objects of a certain data type.
 * This is the class for columns inside a dataframe.
 *
 * @author ANDRE Stephen
 * @author FREBY Laura
 * @since 1.0.0
 * @see fr.uga.fran.Dataframe
 */
public class Column {
	private final Class<?> type;
	private String label;
	private List<Object> list;

	/**
	 * Constructs a column with the specified data type and label.
	 * 
	 * @param type the data type of this column
	 * @param label the label of this column
	 */
	public Column(Class<?> type, String label) throws IllegalArgumentException {
		if (type == null) {
			throw new IllegalArgumentException("type cannot be null");
		} else if (label == null) {
			throw new IllegalArgumentException("label cannot be null");
		}
		
		this.type = type;
		this.label = label;
		list = new ArrayList<>();
	}

	/**
	 * Add an element of data type at the end of this column.
	 * 
	 * @param element the element to add to this column
	 * @throws java.lang.IllegalArgumentException if the specified element is not of the same type
	 * as the column data type.
	 */
	public void add(Object element) throws IllegalArgumentException {
		if (element == null || type.isInstance(element)) {
			list.add(element);
		} else {
			throw new IllegalArgumentException(
					"Element is of " + element.getClass() + " instead of " + type);
		}
	}

	/**
	 * Returns the object located at the specified index.
	 *
	 * @param index the row index
	 * @return the object located at the specified index
	 */
	public Object get(int index) { return list.get(index); }

	/**
	 * Returns the data type of this column.
	 *
	 * @return the data type of this column
	 */
	public Class<?> getType() { return type; }

	/**
	 * Returns the label of this column.
	 *
	 * @return the label of this column
	 */
	public String getLabel() { return label; }
}
