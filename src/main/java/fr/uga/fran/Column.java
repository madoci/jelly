package fr.uga.fran;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for a labeled array of objects of a certain data type.
 */
public class Column {
	private final Class<?> type;
	private String label;
	private List<Object> list;

	/**
	 * Create a column with the specified data type and label.
	 */
	public Column(Class<?> type, String label) {
		this.type = type;
		this.label = label;
		list = new ArrayList<>();
	}

	/**
	 * Add an element of data type at the end of this column.
	 * Throws IllegalArgumentException if the specified element is not of the same type
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
	 */
	public Object get(int index) { return list.get(index); }

	/**
	 * Returns the data type of this column.
	 */
	public Class<?> getType() { return type; }

	/**
	 * Returns the label of this column.
	 */
	public String getLabel() { return label; }
}
