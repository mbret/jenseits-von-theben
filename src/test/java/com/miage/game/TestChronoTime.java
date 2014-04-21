package com.miage.game;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestChronoTime {

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
	 * @author Gael
	 * 
	 * Test of the method getNbTokensToPickup
	 */
	@Test
	public void getNbTokensToPickUp() {
		
		try {
			Chronotime chronoTime = new Chronotime();
			
			assertEquals(0, chronoTime.getNbTokensToPickUp(1, 6));
			assertEquals(6, chronoTime.getNbTokensToPickUp(7, 6));
			
		} catch (IOException e) {
			fail("IOException");
		}
		
		
	}

}
