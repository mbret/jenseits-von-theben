package com.miage.game;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



public class TestPieceStack {
	
	private PlayerTokenStack playerTokenStack;
	private PlayerToken playerToken1, playerToken2, playerToken3, playerToken4;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		playerTokenStack = new PlayerTokenStack();
		
		LocalDate d1 = LocalDate.of(1900, 12, 31);
		LocalDate d2 = LocalDate.of(1901, 3, 20);
		LocalDate d3 = LocalDate.of(1901, 3, 27);
		LocalDate d4 = LocalDate.of(1900, 12, 31);
		
		playerToken1 = new PlayerToken("red");
		playerToken2 = new PlayerToken("blue");
		playerToken3 = new PlayerToken("green");
		playerToken4 = new PlayerToken("yellow");
		
		playerToken1.setTimeState(d1);
		playerToken2.setTimeState(d2);
		playerToken3.setTimeState(d3);
		playerToken4.setTimeState(d4);
		
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	
	/**
	 * Test of the method addPiece()
	 */
	@Test
	public void testAddPiece() {
		
		playerTokenStack.addPlayerToken(playerToken1);
		assertEquals("red ", playerTokenStack.toString());
		playerTokenStack.addPlayerToken(playerToken2);
		assertEquals("red blue ", playerTokenStack.toString());
		playerTokenStack.addPlayerToken(playerToken3);
		assertEquals("red blue green ", playerTokenStack.toString());
		playerTokenStack.addPlayerToken(playerToken4);
		assertEquals("yellow red blue green ", playerTokenStack.toString());
		
	}
	
	
	/**
	 * Test of the method getFirst()
	 */
	@Test
	public void testGetFirst(){
		
		playerTokenStack.addPlayerToken(playerToken1);
		playerTokenStack.addPlayerToken(playerToken2);
		playerTokenStack.addPlayerToken(playerToken3);
		playerTokenStack.addPlayerToken(playerToken4);
		
		assertEquals("yellow", playerTokenStack.getFirstPlayerToken().getColor());
		assertEquals("red", playerTokenStack.getFirstPlayerToken().getColor());
		assertEquals("blue", playerTokenStack.getFirstPlayerToken().getColor());
		assertEquals("green", playerTokenStack.getFirstPlayerToken().getColor());
		
	}

}
