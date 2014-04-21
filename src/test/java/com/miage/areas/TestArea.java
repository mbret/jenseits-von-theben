package com.miage.areas;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.miage.game.Board;
import com.miage.game.Player;

public class TestArea {
	
	Board board;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		board = new Board(2, new ArrayList<Player>());
		
	}

	@After
	public void tearDown() throws Exception {
	}

	
	
	/**
	 * @author gael
	 * 
	 * test of the method to get the steps between areas
	 */
	@Test
	public void getDistanceAreasStep() {
		
		
		Area areaTest = board.getArea("london");
		String[] steps = areaTest.getDistanceAreasSteps("crete");
		String[] expected = {"paris", "rome"};
		
		assertArrayEquals(expected, steps);
		
		/*
		 * No steps between paris and london -> empty table
		 */
		String[] noSteps = areaTest.getDistanceAreasSteps("paris");
		assertArrayEquals(new String[0], noSteps);
		
	}
	
	
	/**
	 * @author gael
	 * 
	 * test of the method to get the week cost between Area
	 */
	@Test
	public void getDistanceCostWeekTo() {
		
		Area areaTest = board.getArea("london");
		
		assertEquals(4, areaTest.getDistanceWeekCostTo("palestine"));
		
		assertEquals(0, areaTest.getDistanceWeekCostTo("london"));
		
	}

}
