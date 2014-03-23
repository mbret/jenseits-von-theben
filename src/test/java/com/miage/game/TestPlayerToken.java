package com.miage.game;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



public class TestPlayerToken {
	
	PlayerToken playerToken1, playerToken2;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		playerToken1 = new PlayerToken("red");
		playerToken2 = new PlayerToken("blue");
		
		Calendar calendar = new GregorianCalendar(1900,11,31);
		Calendar calendar2 = new GregorianCalendar(1900,11,31);
		
		playerToken1.setTimeState(calendar);
		playerToken2.setTimeState(calendar2);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	/**
	 * Test of the method compareTo() for pieces
	 */
	public void testCompareTo() {
		assertEquals(-1, playerToken1.compareTo(playerToken2));
		
		playerToken2.addWeeks(1);

		assertEquals(-1, playerToken1.compareTo(playerToken2));
		
		playerToken1.addWeeks(2);
		
		assertEquals(1, playerToken1.compareTo(playerToken2));
	}

}
