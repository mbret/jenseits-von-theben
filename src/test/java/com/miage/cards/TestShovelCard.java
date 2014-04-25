package com.miage.cards;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestShovelCard {

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

	@Test
	public void getTokensPointsWhenCombinated() {
	
		int[] points = {ShovelCard.getTokensPointsWhenCombinated(0), 
				 ShovelCard.getTokensPointsWhenCombinated(3), 
				 ShovelCard.getTokensPointsWhenCombinated(5)};
		
		// has to return 0 , then 2 and finally 3
		assertArrayEquals(new int[]{0,2,3}, points);
		
	}

}
