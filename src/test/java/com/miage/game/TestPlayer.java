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
import java.io.IOException;




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
		this.deck.addCard(new SpecificKnowledgeCard(0,"specificKnowledge", "paris", 2, 3, "greece"));
		this.deck.addCard(new GeneralKnowledgeCard(0,"generalKnowledge", "vienna", 2, 3));
		this.deck.addCard(new GeneralKnowledgeCard(0,"generalKnowledge", "rome", 2, 3));
		this.deck.addCard(new GeneralKnowledgeCard(0,"generalKnowledge", "moscow", 2, 3));
		this.deck.addCard(new GeneralKnowledgeCard(0,"generalKnowledge", "warsaw", 2, 3));
		
		
		
		this.deck.addCard(new AssistantCard(0,"assistant", "london", 2));
		this.deck.addCard(new AssistantCard(0,"assistant", "roma", 2));
		this.deck.addCard(new AssistantCard(0,"assistant", "vienna", 2));
		this.deck.addCard(new CarCard(0,"car", "berlin", 2));
		this.deck.addCard(new ZeppelinCard(0,"paris", 2));
		this.deck.addCard(new CongressCard(0,"roma", 2));
		this.deck.addCard(new CongressCard(0,"paris", 2));
		this.deck.addCard(new CongressCard(0,"berlin", 2));
		this.deck.addCard(new EthnologicalKnowledgeCard(0,"warsaw", 2, 3, "greece"));
		this.deck.addCard(new ExcavationAuthorizationCard(0,"warsaw", 2));
		this.deck.addCard(new ExpoCard(0,"expo", "warsaw", 2, true, 5));
		this.deck.addCard(new ExpoCard(0,"expo", "moscow", 2, false, 4));
		this.deck.addCard(new ShovelCard(0, "shovel", "warsaw", 2));
		this.deck.addCard(new ShovelCard(0,"shovel", "london", 2));
	
		
		Card[] fourCards = new Card[4];
		
		fourCards[0] = new AssistantCard(0,"assistant", "berlin", 2);		
		fourCards[1] = new GeneralKnowledgeCard(0,"generalKnowledge", "paris", 2, 3);		
		fourCards[2] = new GeneralKnowledgeCard(0,"generalKnowledge", "rome", 2, 3);		
		fourCards[3] = new GeneralKnowledgeCard(0,"generalKnowledge", "vienna", 2, 3);
		
		this.board.setDeck(deck);
		this.board.setFourCurrentCards(fourCards);
		
		PlayerToken playerToken1 = new PlayerToken("red");
		LocalDate date1 = LocalDate.of(1900, 12, 31);
		
		playerToken1.setTimeState(date1);
	
		
		HashMap<PlayerToken, Player> playerTokensAndPlayers = new HashMap<PlayerToken, Player>();
		playerTokensAndPlayers.put(playerToken1, player);
		
		board.setPlayerTokensAndPlayers(playerTokensAndPlayers);
		board.setCurrentPlayerToken(playerToken1);
		
		
		
		
		
		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	
	/**
	 * Test of the pick by the player
	 * @author Gael
	 */
	@Test
	public void testPickCard(){
		
            player.pickCard(board,2);

            assertEquals(player.getCards().toString(), "[generalKnowledge,rome,2,3]");

            player.pickCard(board, 2);

            assertEquals(player.getCards().toString(), "[generalKnowledge,rome,2,3, specificKnowledge,paris,2,3,greece]");
		
	}
	
	
	/**
	 * @author Gael
	 * 
	 * Test of the method hasAlreadyExcavateArea
	 * 
	 */
	@Test
	public void testHasAlreadyExcavateArea(){
		
		this.player.addAreaAlreadyExcavate(this.board.getArea("greece").getName());
		this.player.addAreaAlreadyExcavate(this.board.getArea("egypt").getName());
		
		assertTrue(this.player.hasAlreadyExcavateArea("greece"));
		assertFalse(this.player.hasAlreadyExcavateArea("crete"));
		assertTrue(this.player.hasAlreadyExcavateArea("egypt"));
		
		
	}
        
        /**
	 * Test the allowed excavate
	 * @author david
	 */
	@Test
	public void testCanExcavate() throws IOException{
		
            this.player.addCompetencesPointsOrKnowledge(new SpecificKnowledgeCard(0,"specificKnowledge", "paris", 2, 3, "greece"));
            this.player.addAreaAlreadyExcavate(this.board.getArea("egypt").getName()); 
            assertTrue(this.player.canExcavate(this.board.getArea("greece")));
            this.player.addAreaAlreadyExcavate(this.board.getArea("greece").getName());
            assertFalse(this.player.canExcavate(this.board.getArea("greece")));
            this.player.addCompetencesPointsOrKnowledge(new ExcavationAuthorizationCard(0,"warsaw", 2));
            assertFalse(this.player.canExcavate(this.board.getArea("warsaw")));
            assertFalse(this.player.canExcavate(this.board.getArea("egypt")));
            assertTrue(this.player.canExcavate(this.board.getArea("greece")));
            this.player.getCompetences().put("excavationAuthorization", this.player.getCompetences().get("excavationAuthorization")-1);
            assertFalse(this.player.canExcavate(this.board.getArea("rome")));
            assertFalse(this.player.canExcavate(this.board.getArea("greece")));
	}
	
	
	/**
	 * @author Gael
	 * 
	 * Test of the method which add competences in function of a card
	 * 
	 */
	@Test
	public void testUpdateCompetencePoints(){
		
		// scan all the deck & add points with each card
		for(Card firstCardOfTheDeck : this.deck){
			
			this.player.addCompetencesPointsOrKnowledge(firstCardOfTheDeck);
		}
		
		
		
		assertEquals(this.player.getCompetences().get("assistant"), new Integer(3));
		assertEquals(this.player.getCompetences().get("car"), new Integer(1));
		assertEquals(this.player.getCompetences().get("zeppelin"), new Integer(1));
		assertEquals(this.player.getCompetences().get("congress"), new Integer(3));
		assertEquals(this.player.getCompetences().get("excavationAuthorization"), new Integer(1));
		assertEquals(this.player.getCompetences().get("shovel"), new Integer(2));
		
		assertEquals(this.player.getPlayerKnowledges().getGeneralKnowledge(), 12);
		assertEquals(this.player.getPlayerKnowledges().getSpecificKnowledges().get("greece"), new Integer(3));
		assertEquals(this.player.getPlayerKnowledges().getEthnologicalKnowledges().get("greece"), new Integer(3));
		
		assertEquals(this.player.getPoints(), 15);
		
		
		for(Card firstCardOfTheDeck : this.deck){
			
			this.player.removeCompetencesPointsOrKnowledge(firstCardOfTheDeck);
		}
		
		
		assertEquals(this.player.getCompetences().get("assistant"), new Integer(0));
	
		assertEquals(this.player.getCompetences().get("zeppelin"), new Integer(0));

		assertEquals(this.player.getCompetences().get("excavationAuthorization"), new Integer(0));
		assertEquals(this.player.getCompetences().get("shovel"), new Integer(0));

		assertEquals(this.player.getPlayerKnowledges().getEthnologicalKnowledges().get("greece"), new Integer(0));
		
	
		
		
	
		
	}
	
	
	/**
	 * Test of the method for discarding cards
	 * 
	 * @author Gael
	 */
	@Test
	public void testDiscardCard(){
		
		this.deck = new Deck();		
		this.deck.addCard(new AssistantCard(0,"assistant", "london", 2));
		this.deck.addCard(new CarCard(0,"car", "berlin", 2));
		this.deck.addCard(new AssistantCard(0,"assistant", "roma", 2));
		this.deck.addCard(new AssistantCard(0,"assistant", "vienna", 2));
		this.deck.addCard(new ZeppelinCard(0,"zeppelin", "paris", 2));
		
		this.board.setDeck(this.deck);
		
	
		
		player.pickCard(board, 0);
		player.pickCard(board, 0);
		player.pickCard(board, 0);
		player.pickCard(board, 0);
		player.pickCard(board, 0);
		
		assertEquals(player.getCards().size(), 5);
		assertEquals(player.getCards().get(0).getDisplayName(), "assistant");
		assertEquals(player.getCards().get(0).getAreaName(), "berlin");
		
		assertEquals(board.getSideDeck().size(), 0);
		
		player.useCard(player.getCards().get(0), board.getSideDeck());
		
		assertEquals(player.getCards().size(), 4);
		assertEquals(player.getCards().get(0).getDisplayName(), "assistant");
		assertEquals(player.getCards().get(0).getAreaName(), "london");
		
		assertEquals(board.getSideDeck().get(0).getDisplayName(), "assistant");
		assertEquals(board.getSideDeck().get(0).getAreaName(), "berlin");
		
		assertEquals(board.getSideDeck().size(), 1);
		
		
		player.useCard(player.getCards().get(0), board.getSideDeck());
		player.useCard(player.getCards().get(0), board.getSideDeck());
		
		assertEquals(player.getCards().size(), 3);
		assertEquals(board.getSideDeck().size(), 2);
		
		
		
		
		
	}
	
	
	/**
	 * Test of the method which calcul the total of knowledge points
	 */
	@Test
	public void testTotalPointsOfKnowledge(){
		
		for(int i=0;i < 8;i++)
		this.player.pickCard(board, 3);
		
		assertEquals(this.player.totalKnowledgePoints(board.getArea("greece"), true), 6);
		assertEquals(this.player.totalKnowledgePoints(board.getArea("greece"), false), 6);
		
		for(int i=0;i < 8;i++)
			this.player.pickCard(board, 3);
		
		assertEquals(this.player.totalKnowledgePoints(board.getArea("greece"), true), 12);
		assertEquals(this.player.totalKnowledgePoints(board.getArea("greece"), false), 6);
		
	}
	
	
	
	

	
	
	
	

}
