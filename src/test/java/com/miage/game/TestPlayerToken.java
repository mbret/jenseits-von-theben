package com.miage.game;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



public class TestPlayerToken {
	
	private Board board;
	private PlayerToken playerToken1, playerToken2;
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		board = new Board(2);
		
		playerToken1 = new PlayerToken("red");
		playerToken2 = new PlayerToken("blue");
		
		LocalDate date1 = LocalDate.of(1900, 1, 1);
		LocalDate date2 = LocalDate.of(1900, 1, 1);
		
		playerToken1.setTimeState(date1);
		playerToken2.setTimeState(date2);
		
		playerToken1.setPosition(board.getArea("warsaw"));
		playerToken2.setPosition(board.getArea("warsaw"));
		
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
		assertEquals(-1, playerToken2.compareTo(playerToken1));
		
			
		playerToken2.addWeeks(1);
		


		assertEquals(-1, playerToken1.compareTo(playerToken2));
		assertEquals(1, playerToken2.compareTo(playerToken1));
		
		
		
		playerToken1.addWeeks(2);

		
		assertEquals(1, playerToken1.compareTo(playerToken2));
		assertEquals(-1, playerToken2.compareTo(playerToken1));
		

		
		playerToken2.addWeeks(1);
		
		
		assertEquals(-1, playerToken1.compareTo(playerToken2));
		assertEquals(-1, playerToken2.compareTo(playerToken1));
		
	}
	
	
	
	@Test
	/**
	 * Test of the method to move a playerToken
	 */
	public void testMove(){
		
		String[] moveWarsawToLondon = this.playerToken1.move("london", board);
		
		assertEquals(moveWarsawToLondon[0], "berlin");
		assertEquals(this.playerToken1.getPosition().toString(), "london");
		
		String[] moveLondonToPalestine = this.playerToken1.move("palestine", board);
		
		assertEquals(moveLondonToPalestine[0], "paris");
		assertEquals(moveLondonToPalestine[1], "roma");
		assertEquals(moveLondonToPalestine[2], "crete");
		assertEquals(this.playerToken1.getPosition().toString(), "palestine");
		
		String[] movePalestineToEgypt = this.playerToken1.move("egypt", board);
		
		assertEquals(movePalestineToEgypt.length, 0);
		assertEquals(this.playerToken1.getPosition().toString(), "egypt");
		
		
	}

}
