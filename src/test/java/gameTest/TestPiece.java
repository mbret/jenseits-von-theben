package gameTest;

import static org.junit.Assert.*;
import game.Piece;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestPiece {
	
	Piece piece1, piece2;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		piece1 = new Piece("red");
		piece2 = new Piece("blue");
		
		Calendar calendar = new GregorianCalendar(1900,11,31);
		Calendar calendar2 = new GregorianCalendar(1900,11,31);
		
		piece1.setTimeState(calendar);
		piece2.setTimeState(calendar2);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	/**
	 * Test of the method compareTo() for pieces
	 */
	public void testCompareTo() {
		assertEquals(-1, piece1.compareTo(piece2));
		
		piece2.addWeeks(1);

		assertEquals(-1, piece1.compareTo(piece2));
		
		piece1.addWeeks(2);
		
		assertEquals(1, piece1.compareTo(piece2));
	}

}
