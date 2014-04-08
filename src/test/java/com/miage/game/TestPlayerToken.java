package com.miage.game;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.miage.cards.*;



public class TestPlayerToken {
	
	private Board board;
	private PlayerToken playerToken1, playerToken2;


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
		
		LocalDate date1 = LocalDate.of(1900, 12, 31);
		LocalDate date2 = LocalDate.of(1900, 12, 31);
		
		playerToken1.setTimeState(date1);
		playerToken2.setTimeState(date2);
		
		playerToken1.setPosition(board.getArea("warsaw"));
		playerToken2.setPosition(board.getArea("warsaw"));
		
		Player player1 = new Player("Gael");
		player1.updateCompetencesPointsOrKnowledge(new CarCard(0,"car", "berlin",1), 1);
		
		HashMap<PlayerToken, Player> playerTokensAndPlayers = new HashMap<PlayerToken, Player>();
		playerTokensAndPlayers.put(playerToken1, player1);
		
		board.setPlayerTokensAndPlayers(playerTokensAndPlayers);
		board.setCurrentPlayerToken(playerToken1);
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	
	/**
	 * Test of the method compareTo() for pieces
	 */
	@Test
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
	
	
	
	
	/**
	 * Test of the method to move a playerToken
	 */
	@Test
	public void testMove(){
		
		String[] moveWarsawToLondon = this.playerToken1.move("london", board, false);
		
		assertEquals(moveWarsawToLondon[0], "berlin");
		assertEquals(this.playerToken1.getPosition().toString(), "london");
		
		/*
		 * Test of the cost of move
		 */
		assertEquals(playerToken1.getCurrentWeek(), 2);

		
		String[] moveLondonToPalestine = this.playerToken1.move("palestine", board, false);
		
		assertEquals(moveLondonToPalestine[0], "paris");
		assertEquals(moveLondonToPalestine[1], "rome");
		assertEquals(moveLondonToPalestine[2], "crete");
		assertEquals(this.playerToken1.getPosition().toString(), "palestine");
		
		
		/*
		 * Test of the cost of move
		 */
		assertEquals(playerToken1.getCurrentWeek(), 5);
		
		String[] movePalestineToEgypt = this.playerToken1.move("egypt", board, true);
		
		assertEquals(movePalestineToEgypt.length, 0);
		assertEquals(this.playerToken1.getPosition().toString(), "egypt");
		
		/*
		 * Test of the cost of move
		 */
		assertEquals(playerToken1.getCurrentWeek(), 5);
		
		
		
		
		
	}
	
	
	/**
	 * 
	 * @author Gael
	 * 
	 * Test of the method addWeeksPlayerToken
	 * 
	 */
	@Test
	public void testAddWeeksPlayerToken(){
		
		GeneralKnowledgeCard generalKnowledgeCard = new GeneralKnowledgeCard(0,"generalKnowledge", "berlin",3,1);
		GeneralKnowledgeCard generalKnowledgeCard2 = new GeneralKnowledgeCard(0,"generalKnowledge", "berlin",6,1);
		ShovelCard shovelCard = new ShovelCard(0,"shovel", "paris", 3);
		
		
		this.playerToken1.addWeeksPlayerToken(generalKnowledgeCard);
		this.playerToken1.addWeeksPlayerToken(generalKnowledgeCard2);
		this.playerToken1.addWeeksPlayerToken(shovelCard);
		
		
		assertEquals(this.playerToken1.getCurrentWeek() , 12);
		
		
		
	}

}
