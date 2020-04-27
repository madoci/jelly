package fr.uga.fran.dataframe;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class DataframeStatisticsTest {
	
	private DataframeStatistics stats;
	private final double epsilon = 0.00001;
	
	@Before
	public void setUp() throws Exception {
		Dataframe data = new Dataframe("src/test/resources/medium.csv");
		stats = new DataframeStatistics(data);
	}

	@Test
	public void testArgmin() throws Exception {
		assertEquals(11, stats.argmin(0));
		
		assertEquals(13, stats.argmin("Acres"));
	}

	@Test
	public void testArgmax() throws Exception {
		assertEquals(28, stats.argmax(1));
		
		assertEquals(31, stats.argmax("Acres"));
	}

	@Test
	public void testMin() throws Exception {
		assertEquals(1, (int) stats.min("Baths"));
		
		assertEquals(0.22, (double) stats.min(7), epsilon);
	}

	@Test
	public void testMax() throws Exception {
		assertEquals(4, (int) stats.max("Baths"));
		
		assertEquals(6.49, (double) stats.max(7), epsilon);
	}

	@Test
	public void testSum() throws Exception {
		assertEquals(185305, (int) stats.sum("Taxes"));
		
		assertEquals(51.03, (double) stats.sum(7), epsilon);
	}

	@Test
	public void testMean() throws Exception {
		assertEquals(178, (int) stats.mean("List"));
		
		assertEquals(1.0206, (double) stats.mean(7), epsilon);
	}

	@Test
	public void testMedian() throws Exception {
		assertEquals(24, (int) stats.median("Age"));
		
		assertEquals(0.55, (double) stats.median(7), epsilon);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalStats() throws Exception {
		Dataframe data = new Dataframe("src/test/resources/small.csv");
		DataframeStatistics illegalstats = new DataframeStatistics(data);
		
		illegalstats.sum("Modèle");
	}
	
}
