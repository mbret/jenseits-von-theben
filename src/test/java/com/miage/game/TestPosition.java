package com.miage.game;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestPosition {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test of the position depending on the weeks
	 */
	@Test
	public void testPosition() {
		
		Position position = new Position(0, 0);		// Position on the square 1
		
		
		// square 1
		position.positionDependingOnWeeks(1);
		assertEquals(70, position.getX());
		assertEquals(37, position.getY());
		
		
		// square 6
		position.positionDependingOnWeeks(6);
		assertEquals(385, position.getX());
		assertEquals(37, position.getY());
		
		// square 16
		position.positionDependingOnWeeks(16);
		assertEquals(1015, position.getX());
		assertEquals(37, position.getY());
		
		
		// square 25
		position.positionDependingOnWeeks(25);
		assertEquals(1015, position.getX());
		assertEquals(604, position.getY());
		
		// square 27
		position.positionDependingOnWeeks(27);
		assertEquals(1015, position.getX());
		assertEquals(730, position.getY());
		
		// square 38
		position.positionDependingOnWeeks(38);
		assertEquals(322, position.getX());
		assertEquals(730, position.getY());
		
		// square 42
		position.positionDependingOnWeeks(42);
		assertEquals(70, position.getX());
		assertEquals(730, position.getY());
		
		// square 52
		position.positionDependingOnWeeks(52);
		assertEquals(70, position.getX());
		assertEquals(100, position.getY());
	
		
		
		
	}

}
