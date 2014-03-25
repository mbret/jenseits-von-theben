package com.miage.game;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.miage.cards.Card;
import com.miage.cards.GeneralKnowledgeCard;
import com.miage.cards.SpecificKnowledgeCard;




public class TestPlayer {
	
	private Board board;
	private Player player;
	private Deck deck;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		this.board = new Board(2);
		
		this.player = new Player("player");
		
		this.deck = new Deck();
		this.deck.addCard(new SpecificKnowledgeCard("paris", 2, 2, "code"));
		this.deck.addCard(new GeneralKnowledgeCard("vienna", 3, 3));
		this.deck.addCard(new GeneralKnowledgeCard("rome", 4, 4));
		this.deck.addCard(new GeneralKnowledgeCard("moscow", 3, 2));
		this.deck.addCard(new GeneralKnowledgeCard("warsaw", 2, 3));
		
		Card[] fourCards = new Card[4];
		
		fourCards[0] = new GeneralKnowledgeCard("berlin", 2, 2);		
		fourCards[1] = new GeneralKnowledgeCard("paris", 2, 2);		
		fourCards[2] = new GeneralKnowledgeCard("rome", 2, 2);		
		fourCards[3] = new GeneralKnowledgeCard("vienna", 2, 2);
		
		this.board.setDeck(deck);
		this.board.setFourCurrentCards(fourCards);
		
		
		
		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	@Test
	/**
	 * Test of the pick by the player
	 * @author Gael
	 */
	public void testPickCard(){
		
		player.pickCard(board,2);
		
		assertEquals(player.getCards().toString(), "[generalKnowledge,rome,2,2]");
		
		player.pickCard(board, 2);
		
		assertEquals(player.getCards().toString(), "[generalKnowledge,rome,2,2, specificKnowledge,paris,2,2,code]");
		
	}

	
	
	
	

}
