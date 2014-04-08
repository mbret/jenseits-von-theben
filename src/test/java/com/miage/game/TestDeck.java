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
		this.deck.add(new GeneralKnowledgeCard(0,"generalKnowledge", "berlin", 2, 2));
		this.deck.add(new SpecificKnowledgeCard(0,"specificKnowledge", "paris", 2, 2, "greece"));
		this.deck.add(new GeneralKnowledgeCard(0,"generalKnowledge", "vienna", 3, 3));
		this.deck.add(new GeneralKnowledgeCard(0,"generalKnowledge", "rome", 4, 4));
		this.deck.add(new GeneralKnowledgeCard(0,"generalKnowledge", "moscow", 3, 2));
		this.deck.add(new GeneralKnowledgeCard(0,"generalKnowledge", "warsaw", 2, 3));
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	
	/**
	 * Test of the method pick()
	 * @author Gael
	 */
	@Test
	public void testPick() {
		int sizeOfDeck = this.deck.size();
		
		assertEquals(this.deck.pick().toString(), "generalKnowledge,berlin,2,2");
		assertTrue(this.deck.size() == sizeOfDeck-1);
		assertEquals(this.deck.pick().toString(), "specificKnowledge,paris,2,2,greece");
		
	}

}
