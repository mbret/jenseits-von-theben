package com.miage.cards;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.miage.game.Deck;

public class TestCard {
	
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
		this.deck.addCard(new GeneralKnowledgeCard(0, "berlin", 2, 2));
		this.deck.addCard(new SpecificKnowledgeCard(0, "paris", 2, 2, "code"));
		this.deck.addCard(new GeneralKnowledgeCard(0,"vienna", 3, 3));
		this.deck.addCard(new GeneralKnowledgeCard(0,"rome", 4, 4));
		this.deck.addCard(new GeneralKnowledgeCard(0,"moscow", 3, 2));
		this.deck.addCard(new GeneralKnowledgeCard(0,"warsaw", 2, 3));
	}

	@After
	public void tearDown() throws Exception {
	}

	
	/**
	 * @author Gael
	 * Test of the cards downcast method 
	 */
	@Test
	public void testCastCards() {
		
		Card card = this.deck.pick();
		card = card.downCastCard();
		
		assertEquals(card.toString(), "generalKnowledge,berlin,2,2");
		
		
	}

}
