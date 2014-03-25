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
		this.deck.addCard(new GeneralKnowledgeCard("berlin", 2, 2));
		this.deck.addCard(new SpecificKnowledgeCard("paris", 2, 2, "code"));
		this.deck.addCard(new GeneralKnowledgeCard("vienna", 3, 3));
		this.deck.addCard(new GeneralKnowledgeCard("rome", 4, 4));
		this.deck.addCard(new GeneralKnowledgeCard("moscow", 3, 2));
		this.deck.addCard(new GeneralKnowledgeCard("warsaw", 2, 3));
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	/**
	 * Test of the method pick()
	 * @author Gael
	 */
	public void testPick() {
		int sizeOfDeck = this.deck.size();
		
		assertEquals(this.deck.pick().toString(), "generalKnowledge,berlin,2,2");
		assertTrue(this.deck.size() == sizeOfDeck-1);
		assertEquals(this.deck.pick().toString(), "specificKnowledge,paris,2,2,code");
		
	}

}
