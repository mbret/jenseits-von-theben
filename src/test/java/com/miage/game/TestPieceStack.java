package com.miage.game;

import static org.junit.Assert.*;

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
		
		Calendar c1 = new GregorianCalendar(1900, 11, 31);
		Calendar c2 = new GregorianCalendar(1901, 3, 20);
		Calendar c3 = new GregorianCalendar(1901, 2, 27);
		Calendar c4 = new GregorianCalendar(1900, 11, 31);
		
		playerToken1 = new PlayerToken("red");
		playerToken2 = new PlayerToken("blue");
		playerToken3 = new PlayerToken("green");
		playerToken4 = new PlayerToken("yellow");
		
		playerToken1.setTimeState(c1);
		playerToken2.setTimeState(c2);
		playerToken3.setTimeState(c3);
		playerToken4.setTimeState(c4);
		
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	/**
	 * Test of the method addPiece()
	 */
	public void testAddPiece() {
		
		playerTokenStack.addPlayerToken(playerToken1);
		assertEquals("red ", playerTokenStack.toString());
		playerTokenStack.addPlayerToken(playerToken2);
		assertEquals("red blue ", playerTokenStack.toString());
		playerTokenStack.addPlayerToken(playerToken3);
		assertEquals("red green blue ", playerTokenStack.toString());
		playerTokenStack.addPlayerToken(playerToken4);
		assertEquals("yellow red green blue ", playerTokenStack.toString());
		
	}
	
	@Test
	/**
	 * Test of the method getFirst()
	 */
	public void testGetFirst(){
		
		playerTokenStack.addPlayerToken(playerToken1);
		playerTokenStack.addPlayerToken(playerToken2);
		playerTokenStack.addPlayerToken(playerToken3);
		playerTokenStack.addPlayerToken(playerToken4);
		
		assertEquals("yellow", playerTokenStack.getFirstPlayerToken().getColor());
		assertEquals("red", playerTokenStack.getFirstPlayerToken().getColor());
		assertEquals("green", playerTokenStack.getFirstPlayerToken().getColor());
		assertEquals("blue", playerTokenStack.getFirstPlayerToken().getColor());
		
	}

}
