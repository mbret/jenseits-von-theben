package com.miage.game;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.miage.cards.GeneralKnowledgeCard;
import com.miage.cards.SpecificKnowledgeCard;

public class TestDeck {
	
	Deck deck;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		this.deck = new Deck();
		this.deck.addCard(new GeneralKnowledgeCard("Berlin", 2, 2));
		this.deck.addCard(new SpecificKnowledgeCard("Paris", 2, 2, "Red"));
		this.deck.addCard(new GeneralKnowledgeCard("Vienne", 3, 3));
		this.deck.addCard(new GeneralKnowledgeCard("Rome", 4, 4));
		this.deck.addCard(new GeneralKnowledgeCard("Moscou", 3, 2));
		this.deck.addCard(new GeneralKnowledgeCard("Varsovie", 2, 3));
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPick() {
		int sizeOfDeck = this.deck.size();
		
		assertEquals(this.deck.pick().toString(), "Connaissance générale,Berlin,2,2");
		assertTrue(this.deck.size() == sizeOfDeck-1);
		assertEquals(this.deck.pick().toString(), "Connaissance spécifique,Paris,2,2,Red");
		
	}

}
