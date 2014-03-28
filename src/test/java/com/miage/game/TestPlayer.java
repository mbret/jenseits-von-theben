package com.miage.game;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.miage.areas.ExcavationArea;
import com.miage.cards.*;




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
		this.deck.addCard(new SpecificKnowledgeCard("paris", 2, 3, "greece"));
		this.deck.addCard(new GeneralKnowledgeCard("vienna", 2, 3));
		this.deck.addCard(new GeneralKnowledgeCard("rome", 2, 3));
		this.deck.addCard(new GeneralKnowledgeCard("moscow", 2, 3));
		this.deck.addCard(new GeneralKnowledgeCard("warsaw", 2, 3));
		
		
		
		this.deck.addCard(new AssistantCard("london", 2));
		this.deck.addCard(new AssistantCard("roma", 2));
		this.deck.addCard(new AssistantCard("vienna", 2));
		this.deck.addCard(new CarCard("berlin", 2));
		this.deck.addCard(new ZeppelinCard("paris", 2));
		this.deck.addCard(new CongressCard("roma", 2));
		this.deck.addCard(new CongressCard("paris", 2));
		this.deck.addCard(new CongressCard("berlin", 2));
		this.deck.addCard(new EthnologicalKnowledgeCard("warsaw", 2, 3, "greece"));
		this.deck.addCard(new ExcavationAuthorizationCard("warsaw", 2));
		this.deck.addCard(new ExpoCard("warsaw", 2, true));
		this.deck.addCard(new ExpoCard("moscow", 2, false));
		this.deck.addCard(new ShovelCard("warsaw", 2));
		this.deck.addCard(new ShovelCard("london", 2));
	
		
		Card[] fourCards = new Card[4];
		
		fourCards[0] = new AssistantCard("berlin", 2);		
		fourCards[1] = new GeneralKnowledgeCard("paris", 2, 3);		
		fourCards[2] = new GeneralKnowledgeCard("rome", 2, 3);		
		fourCards[3] = new GeneralKnowledgeCard("vienna", 2, 3);
		
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
		this.deck.addCard(new AssistantCard("london", 2));
		this.deck.addCard(new CarCard("berlin", 2));
		this.deck.addCard(new AssistantCard("roma", 2));
		this.deck.addCard(new AssistantCard("vienna", 2));
		this.deck.addCard(new ZeppelinCard("paris", 2));
		
		this.board.setDeck(this.deck);
		
	
		
		player.pickCard(board, 0);
		player.pickCard(board, 0);
		player.pickCard(board, 0);
		player.pickCard(board, 0);
		player.pickCard(board, 0);
		
		assertEquals(player.getCards().size(), 5);
		assertEquals(player.getCards().get(0).getName(), "assistant");
		assertEquals(player.getCards().get(0).getAreaName(), "berlin");
		
		assertEquals(board.getSideDeck().size(), 0);
		
		player.useCard(player.getCards().get(0), board.getSideDeck());
		
		assertEquals(player.getCards().size(), 4);
		assertEquals(player.getCards().get(0).getName(), "assistant");
		assertEquals(player.getCards().get(0).getAreaName(), "london");
		
		assertEquals(board.getSideDeck().get(0).getName(), "assistant");
		assertEquals(board.getSideDeck().get(0).getAreaName(), "berlin");
		
		assertEquals(board.getSideDeck().size(), 1);
		
		
		player.useCard(player.getCards().get(0), board.getSideDeck());
		player.useCard(player.getCards().get(0), board.getSideDeck());
		
		assertEquals(player.getCards().size(), 3);
		assertEquals(board.getSideDeck().size(), 2);
		
		
		
		
		
	}
	

	
	
	
	

}
