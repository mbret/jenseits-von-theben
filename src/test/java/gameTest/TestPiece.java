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
	
	Piece p1, p2;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		p1 = new Piece("red");
		p2 = new Piece("blue");
		
		Calendar calendar = new GregorianCalendar(1900,11,31);
		Calendar calendar2 = new GregorianCalendar(1900,11,31);
		
		p1.setTimeState(calendar);
		p2.setTimeState(calendar2);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCompareTo() {
		assertEquals(-1, p1.compareTo(p2));
		
		p2.addWeeks(1);

		assertEquals(-1, p1.compareTo(p2));
		
		p1.addWeeks(2);
		
		assertEquals(1, p1.compareTo(p2));
	}

}
