package fr.uga.fran.mathset;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.uga.fran.dataframe.Dataframe;
import fr.uga.fran.dataframe.TabularDataframeViewer;
import fr.uga.fran.math.ArrayStatistics;
import fr.uga.fran.math.IntegerOperator;

/**
 * 
 *
 */
public class App 
{
	private interface CommandSelect {
		public <E> Dataframe select(String label, Comparable<E> value);
	}
	
	private interface CommandStats {
		public <E> Object stats(String label);
	}
	
	private static final Integer[][] arrays = 
		{ 	{ 1, 2 }, 
			{ 0, 1, 3 },
			{ 8, 9 },
			{ 0 },
			{ 1, 4, 5, 7 },
			{ 3, 4, 8 },
			{ 6 },
			{ 2, 6, 9 },
			{ 3, 4 }
		};
	
	private static Dataframe dataframe;
	private static Dataframe original;
	private static Scanner input;
	
    public static void main( String[] args )
    {
        init();
        
        if (args.length > 0) {
        	if (args.length == 1 && args[0].equals("-i")) {
        		interactiveMode();
        	} else {
        		System.out.println("");
        	}
        } else {
        	scenarioMode();
        }
    }
    
    private static void scenarioMode() {
    	displayDataframe();
    }
    
    private static void interactiveMode() {
    	System.out.println("Orignal dataframe :");
    	System.out.println(dataframe);
    	System.out.println();
    	
    	input = new Scanner(System.in);
    	
    	globalInterface();
    }
    
    private static void globalInterface() {
    	while (true) {
    		showGlobalOptions();
    		
    		String option = input.nextLine();
    		
    		if (option.equals("s") || option.equals("select")) {
    			selectInterface();
    		} else if (option.equals("t") || option.equals("stats")) {
    			statsInterface();
    		} else if (option.equals("d") || option.equals("display")) {
    			displayDataframe();
    		} else if (option.equals("r") || option.equals("restore")) {
    			dataframe = original;
    			System.out.println("Dataframe restored.");
    			displayDataframe();
    		} else if (option.equals("c") || option.equals("scenario")) {
    			scenarioMode();
    		} else if (option.equals("q") || option.equals("quit")) {
    			System.out.println("Exiting application...");
    			System.exit(0);
    		} else {
    			System.out.println("Unknown option \""+option+"\".");
    		}
    	}
    }
    
    private static void showGlobalOptions() {
    	System.out.println();
    	System.out.println("Available global options:");
    	System.out.println("s | select   : show the selection interface to perform selection");
    	System.out.println("t | stats    : show the stats interface to perform satistics");
    	System.out.println("d | display  : display the dataframe");
    	System.out.println("r | restore  : restore the dataframe to its original state");
    	System.out.println("c | scenario : run a premade scenario");
    	System.out.println("q | quit     : quit the application");
    	System.out.println();
    	System.out.print("What do you want to do? ");
    }
    
    private static void selectInterface() {
    	while (true) {
    		showSelectOptions();
    		
    		String option = input.nextLine();
    		
    		if (option.equals("x") || option.equals("cross")) {
    			crossInterface();
    			displayDataframe();
    		} else if (option.equals("eq")) {
    			comparisonInterface(new CommandSelect() {
					@Override
					public <E> Dataframe select(String label, Comparable<E> value) {
						return dataframe.select().equal(label, value);
					}
    			});
    			displayDataframe();
    		} else if (option.equals("ne")) {
    			comparisonInterface(new CommandSelect() {
					@Override
					public <E> Dataframe select(String label, Comparable<E> value) {
						return dataframe.select().notEqual(label, value);
					}
				});
    			displayDataframe();
    		} else if (option.equals("lt")) {
    			comparisonInterface(new CommandSelect() {
					@Override
					public <E> Dataframe select(String label, Comparable<E> value) {
						return dataframe.select().lessThan(label, value);
					}
    			});
    			displayDataframe();
    		} else if (option.equals("le")) {
    			comparisonInterface(new CommandSelect() {
					@Override
					public <E> Dataframe select(String label, Comparable<E> value) {
						return dataframe.select().lessEqual(label, value);
					}
    			});
    			displayDataframe();
    		} else if (option.equals("gt")) {
    			comparisonInterface(new CommandSelect() {
					@Override
					public <E> Dataframe select(String label, Comparable<E> value) {
						return dataframe.select().greaterThan(label, value);
					}
    			});
    			displayDataframe();
    		} else if (option.equals("ge")) {
    			comparisonInterface(new CommandSelect() {
					@Override
					public <E> Dataframe select(String label, Comparable<E> value) {
						return dataframe.select().greaterEqual(label, value);
					}
    			});
    			displayDataframe();
    		} else if (option.equals("d") || option.equals("display")) {
    			displayDataframe();
    		} else if (option.equals("r") || option.equals("return")) {
    			return;
    		} else if (option.equals("q") || option.equals("quit")) {
    			System.out.println("Exiting application...");
    			System.exit(0);
    		} else {
    			System.out.println("Unknown option \""+option+"\".");
    		}
    	}
    }
    
    private static void showSelectOptions() {
    	System.out.println();
    	System.out.println("Available selection options:");
    	System.out.println("x | cross    : select row and column indexes");
    	System.out.println("eq           : select a value to be equal to");
    	System.out.println("ne           : select a value to be different from");
    	System.out.println("lt           : select a value to be less than");
    	System.out.println("le           : select a value to be less than or equal to");
    	System.out.println("gt           : select a value to be greater than");
    	System.out.println("ge           : select a value to be greater than or equal to");
    	System.out.println("d | display  : display the dataframe");
    	System.out.println("r | return   : return to the global interface");
    	System.out.println("q | quit     : quit the application");
    	System.out.println();
    	System.out.print("What do you want to do? ");
    }
    
    private static void crossInterface() {
    	System.out.print("Row indexes, seperated by a space (empty for all rows): ");
    	int rows[] = askIntArray();
    	
    	System.out.print("Column indexes, seperated by a space (empty for all columns): ");
    	int columns[] = askIntArray();
    	
    	if (rows.length == 0 && columns.length == 0) {
    		return;
    	} else if (rows.length == 0) {
    		dataframe = dataframe.select().column(columns);
    	} else if (columns.length == 0) {
    		dataframe = dataframe.select().row(rows);
    	} else {
    		dataframe = dataframe.select().cross(rows, columns);
    	}
    }
    
    private static void comparisonInterface(CommandSelect select) {
    	System.out.print("Label of the target column: ");
    	String label = input.nextLine();
    	
    	try {
    		Class<?> type = dataframe.getType(label);

    		System.out.print("Value to be compared to: ");
    		if (type.isAssignableFrom(Integer.class)) {
    			String line = input.nextLine();
    			try {
	    			int value = Integer.valueOf(line);
	    			dataframe = select.select(label, value);
    			} catch (Exception e) {
    				System.out.println(line+" is not a valid integer");
    			}
    		} else if (type.isAssignableFrom(Double.class)) {
    			String line = input.nextLine();
    			try {
	    			double value = Double.valueOf(line);
	    			dataframe = select.select(label, value);
    			} catch (Exception e) {
    				System.out.println(line+" is not a valid double");
    			}
    		} else if (type.isAssignableFrom(String.class)) {
    			String value = input.nextLine();
    			dataframe = select.select(label, value);
    		} else {
    			System.out.println("Comparison of "+type+" is not supported in interactive mode");
    		}
    	} catch (Exception e) {
    		System.out.println("Label \""+label+"\" cannot be found in the dataframe");
    	}
    }
    
    private static void statsInterface() {
    	while (true) {
    		showStatsOptions();
    		
    		String option = input.nextLine();
    		
    		if (option.equals("amin")) {
    			calculInterface(new CommandStats() {
					@Override
					public <E> Object stats(String label) {
						return dataframe.stats().argmin(label);
					}
    			});
    		} else if (option.equals("amax")) {
    			calculInterface(new CommandStats() {
					@Override
					public <E> Object stats(String label) {
						return dataframe.stats().argmax(label);
					}
    			});
    		} else if (option.equals("min")) {
    			calculInterface(new CommandStats() {
					@Override
					public <E> Object stats(String label) {
						return dataframe.stats().min(label);
					}
    			});
    		} else if (option.equals("max")) {
    			calculInterface(new CommandStats() {
					@Override
					public <E> Object stats(String label) {
						return dataframe.stats().max(label);
					}
    			});
    		} else if (option.equals("sum")) {
    			calculInterface(new CommandStats() {
					@Override
					public <E> Object stats(String label) {
						return dataframe.stats().sum(label);
					}
    			});
    		} else if (option.equals("mean")) {
    			calculInterface(new CommandStats() {
					@Override
					public <E> Object stats(String label) {
						return dataframe.stats().mean(label);
					}
    			});
    		} else if (option.equals("median")) {
    			calculInterface(new CommandStats() {
					@Override
					public <E> Object stats(String label) {
						return dataframe.stats().median(label);
					}
    			});
    		} else {
    			System.out.println("Unknown option \""+option+"\".");
    		}
    	}
    }
    
    private static void showStatsOptions() {
    	System.out.println();
    	System.out.println("Available statistics options:");
    	System.out.println("amin         : calculate the argmin of a dataframe column");
    	System.out.println("amax         : calculate the argmax of a dataframe column");
    	System.out.println("min          : calculate the min of a dataframe column");
    	System.out.println("max          : calculate the max of a dataframe column");
    	System.out.println("sum          : calculate the sum of a dataframe column");
    	System.out.println("mean         : calculate the mean of a dataframe column");
    	System.out.println("median       : calculate the median of a dataframe column");
    	System.out.println("d | display  : display the dataframe");
    	System.out.println("r | return   : return to the global interface");
    	System.out.println("q | quit     : quit the application");
    	System.out.println();
    	System.out.print("What do you want to do? ");
    }
    
    public static void calculInterface(CommandStats stats) {
    	System.out.print("Label of the target column: ");
    	String label = input.nextLine();
    	
    	try {
    		System.out.println(stats.stats(label));
    	} catch (Exception e) {
    		System.out.println("Label \""+label+"\" cannot be found in the dataframe");
    	}
    }
    
    private static int[] askIntArray() {
    	String line = input.nextLine();
    	
    	Scanner scanner = new Scanner(line);
    	List<Integer> list = new ArrayList<>();
    	while (scanner.hasNextInt()) {
    		list.add(scanner.nextInt());
    	}
    	scanner.close();
    	
    	int array[] = new int[list.size()];
    	
    	for (int i=0; i<list.size(); i++) {
    		array[i] = list.get(i);
    	}
    	
    	return array;
    }
    
    private static void displayDataframe() {
    	System.out.println("Dataframe :");
        System.out.println(dataframe);
    }
    
    private static void init() {
    	dataframe = createDataframe();
    	original = dataframe;
        
        TabularDataframeViewer viewer = new TabularDataframeViewer();
        viewer.setSeparator(" | ");
        dataframe.setViewer(viewer);

        ArrayStatistics.setOperator(Integer.class, new IntegerOperator());
        ArrayStatistics.setOperator(IntegerSet.class, new IntegerSetOperator());
    }
    
    private static Dataframe createDataframe() {
    	String labels[] = { "Set", "Count", "Name", "Cost" };
    	
    	IntegerSet sets[] = createSets();
    	
    	Integer counts[] = { 2, 7, 5, 3, 1, 1, 8, 2, 5 };
    	String names[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
    	Double costs[] = { 5.6, 1.25, 6.1, 0.74, 8.2, 3.57, 3.12, 1.8, 2.99 }; 
    	
    	return new Dataframe(labels, sets, counts, names, costs);
    }
    
    private static IntegerSet[] createSets() {
    	IntegerSet sets[] = new IntegerSet[arrays.length];
    	
    	for (int i=0; i<arrays.length; i++) {
    		sets[i] = new IntegerSet(arrays[i]);
    	}
    	
    	return sets;
    }
}
