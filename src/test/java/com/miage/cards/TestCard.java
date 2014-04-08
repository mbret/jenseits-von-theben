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
		this.deck.add(new GeneralKnowledgeCard(0, "generalKnowledge", "berlin", 2, 2));
		this.deck.add(new SpecificKnowledgeCard(0, "generalKnowledge", "paris", 2, 2, "code"));
		this.deck.add(new GeneralKnowledgeCard(0, "generalKnowledge", "vienna", 3, 3));
		this.deck.add(new GeneralKnowledgeCard(0, "generalKnowledge", "rome", 4, 4));
		this.deck.add(new GeneralKnowledgeCard(0, "generalKnowledge", "moscow", 3, 2));
		this.deck.add(new GeneralKnowledgeCard(0, "generalKnowledge", "warsaw", 2, 3));
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
		
		GeneralKnowledgeCard card = (GeneralKnowledgeCard)this.deck.pick();
                
		assertEquals(card.getDisplayName() + "," + card.getAreaName() + "," + card.getWeekCost()+ "," + card.getValue(), "generalKnowledge,berlin,2,2");
		
	}

}
