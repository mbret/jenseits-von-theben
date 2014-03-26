package com.miage.game;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.miage.cards.Card;
import com.miage.cards.GeneralKnowledgeCard;




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
		deckTest.addCard(new GeneralKnowledgeCard("moscow", 2, 2));
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
	public void testpickCardOnBoard() {
		
		Card card = board.pickCardOnBoard(3);
		assertEquals(board.getFourCurrentCards()[3].toString(), "generalKnowledge,moscow,2,2");
		assertEquals(card.getAreaName(), "vienna");
		
		
		
		
	}
	
	

}
