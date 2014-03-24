package com.miage.game;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.miage.cards.Card;
import com.miage.cards.ExpoCard;
import com.miage.cards.GeneralKnowledgeCard;
import com.miage.cards.ShovelCard;




public class TestBoard {
	
	private Board board;
	private Card[] fourCards;
	private Deck deckTest;

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
		
		
		fourCards = new Card[4];
		
		fourCards[0] = new GeneralKnowledgeCard("berlin", 2, 2);		
		fourCards[1] = new GeneralKnowledgeCard("paris", 2, 2);		
		fourCards[2] = new GeneralKnowledgeCard("rome", 2, 2);		
		fourCards[3] = new GeneralKnowledgeCard("vienna", 2, 2);		
		
		board.setFourCurrentCards(fourCards);
		
		deckTest = new Deck();
		deckTest.addCard(new ExpoCard("moscow", 4, true));
		deckTest.addCard(new ExpoCard("warsaw", 4, true));
		deckTest.addCard(new ShovelCard("london", 2));
		board.setDeck(deckTest);
		
		
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	/**
	 * @author Gael
	 * Test of the method pickCardOnBoard
	 * 
	 */
	public void testPickCardOnBoard() {
		
		Card card = board.pickCardOnBoard(3);
		assertEquals(board.getFourCurrentCards()[3].toString(), "shovel,london,2");
		assertEquals(card.getAreaName(), "vienna");
		assertEquals(board.getThreeExpoCards()[0].toString(), "big expo,warsaw,4,5");
		assertEquals(board.getThreeExpoCards()[1].toString(), "big expo,moscow,4,5");
		
			
	}
	
	@Test
	/**
	 * @author Gael
	 * 
	 * Test of the method to add an expo card on the board
	 */
	public void testAddExpoCardOnTheBoard(){
		
		
		ExpoCard card1 = new ExpoCard("berlin", 4, true);
		ExpoCard card2 = new ExpoCard("roma", 3, false);
		ExpoCard card3 = new ExpoCard("vienna", 4, true);
		ExpoCard card4 = new ExpoCard("paris", 3, false);
		
		board.addExpoCardOnBoard(card1);
		assertEquals(board.getThreeExpoCards()[0].toString(), "big expo,berlin,4,5");
		
		board.addExpoCardOnBoard(card2);
		assertEquals(board.getThreeExpoCards()[0].toString(), "little expo,roma,3,4");
		assertEquals(board.getThreeExpoCards()[1].toString(), "big expo,berlin,4,5");
		
		board.addExpoCardOnBoard(card3);
		assertEquals(board.getThreeExpoCards()[0].toString(), "big expo,vienna,4,5");
		assertEquals(board.getThreeExpoCards()[1].toString(), "little expo,roma,3,4");
		assertEquals(board.getThreeExpoCards()[2].toString(), "big expo,berlin,4,5");
		
		board.addExpoCardOnBoard(card4);
		assertEquals(board.getThreeExpoCards()[0].toString(), "little expo,paris,3,4");
		assertEquals(board.getThreeExpoCards()[1].toString(), "big expo,vienna,4,5");
		assertEquals(board.getThreeExpoCards()[2].toString(), "little expo,roma,3,4");
		
	}
	
	

}
