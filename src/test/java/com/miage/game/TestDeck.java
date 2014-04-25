package com.miage.game;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.miage.cards.Card;
import com.miage.cards.GeneralKnowledgeCard;
import com.miage.cards.SpecificKnowledgeCard;

public class TestDeck {
	
	private Deck deck;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		this.deck = new Deck();
		this.deck.add(new GeneralKnowledgeCard(0,"generalKnowledge", "berlin", 2, 2));
		this.deck.add(new SpecificKnowledgeCard(0,"specificKnowledge", "paris", 2, 2, "greece"));
		this.deck.add(new GeneralKnowledgeCard(0,"generalKnowledge", "vienna", 3, 3));
		this.deck.add(new GeneralKnowledgeCard(0,"generalKnowledge", "rome", 4, 4));
		this.deck.add(new GeneralKnowledgeCard(0,"generalKnowledge", "moscow", 3, 2));
		this.deck.add(new GeneralKnowledgeCard(0,"generalKnowledge", "warsaw", 2, 3));
		//incorrect cards
		this.deck.add(new GeneralKnowledgeCard(0,"generalKnowledge", "crete", 4, 4));
		this.deck.add(new GeneralKnowledgeCard(0,"generalKnowledge", "palestine", 3, 2));

		
	}

	@After
	public void tearDown() throws Exception {
	}

	
	/**
	 * Test of the method pick()
	 * @author Gael
	 */
	@Test
	public void pick() {
		int sizeOfDeck = this.deck.size();
		
		assertEquals(this.deck.pick().getAreaName(), "berlin");
		assertTrue(this.deck.size() == sizeOfDeck-1);
		assertEquals(this.deck.pick().getAreaName(), "paris");
		
	}
	
	
	/**
	 * Test of the method divideDeck()
	 * @author Gael
	 */
	@Test
	public void divideDeck() {
		
		Deck[] decks = this.deck.getDividedDeck(3);
		
		int[] sizes = {decks[0].size(), decks[1].size(), decks[2].size()};
		String[] firstCards = { decks[0].getFirst().getAreaName(), 
								decks[1].getFirst().getAreaName(), 
								decks[2].getFirst().getAreaName()};
		
		
		assertArrayEquals(new int[]{3,3,2}, sizes);
		
		assertArrayEquals(new String[]{"berlin","rome","crete"}, firstCards);
		
		
		
	}
	
	
	
	

}
