package com.miage.game;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;




public class TestBoard {
	
	Board board;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		board = new Board(3);
		board.initAreas();
		board.initializationDecks();
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	/**
	 * Test of the method distance()
	 */
	public void testDistance() {
		
		
	}
	
	

}
