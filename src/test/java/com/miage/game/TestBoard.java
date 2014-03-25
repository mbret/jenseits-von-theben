package com.miage.game;

import com.miage.areas.ExcavationArea;
import com.miage.cards.Card;
import com.miage.cards.GeneralKnowledgeCard;
import com.miage.config.ConfigManager;
import com.miage.tokens.PointToken;
import com.miage.tokens.Token;
import java.io.IOException;
import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import com.miage.cards.ExpoCard;

import com.miage.cards.ShovelCard;





public class TestBoard {
	
	private Board board, board2;
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
		
		
		fourCards = new Card[4];
		
		fourCards[0] = new GeneralKnowledgeCard("berlin", 2, 2);		
		fourCards[1] = new GeneralKnowledgeCard("paris", 2, 2);		
		fourCards[2] = new GeneralKnowledgeCard("rome", 2, 2);		
		fourCards[3] = new GeneralKnowledgeCard("vienna", 2, 2);		
		
		board.setFourCurrentCards(fourCards);
		
		deckTest = new Deck();
		deckTest.addCard(new ExpoCard("moscow", 4, true));
		deckTest.addCard(new ExpoCard("warsaw", 4, true));
		deckTest.addCard(new ShovelCard("london", 2));
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
	public void testPickCardOnBoard() {
		
		Card card = board.pickCardOnBoard(3);
		assertEquals(board.getFourCurrentCards()[3].toString(), "shovel,london,2");
		assertEquals(card.getAreaName(), "vienna");
		assertEquals(board.getThreeExpoCards()[0].toString(), "big expo,warsaw,4,5");
		assertEquals(board.getThreeExpoCards()[1].toString(), "big expo,moscow,4,5");
		
			
	}
	
	@Test
	/**
	 * @author Gael
	 * 
	 * Test of the method to add an expo card on the board
	 */
	public void testAddExpoCardOnTheBoard(){
		
		
		ExpoCard card1 = new ExpoCard("berlin", 4, true);
		ExpoCard card2 = new ExpoCard("roma", 3, false);
		ExpoCard card3 = new ExpoCard("vienna", 4, true);
		ExpoCard card4 = new ExpoCard("paris", 3, false);
		
		board.addExpoCardOnBoard(card1);
		assertEquals(board.getThreeExpoCards()[0].toString(), "big expo,berlin,4,5");
		
		board.addExpoCardOnBoard(card2);
		assertEquals(board.getThreeExpoCards()[0].toString(), "little expo,roma,3,4");
		assertEquals(board.getThreeExpoCards()[1].toString(), "big expo,berlin,4,5");
		
		board.addExpoCardOnBoard(card3);
		assertEquals(board.getThreeExpoCards()[0].toString(), "big expo,vienna,4,5");
		assertEquals(board.getThreeExpoCards()[1].toString(), "little expo,roma,3,4");
		assertEquals(board.getThreeExpoCards()[2].toString(), "big expo,berlin,4,5");
		
		board.addExpoCardOnBoard(card4);
		assertEquals(board.getThreeExpoCards()[0].toString(), "little expo,paris,3,4");
		assertEquals(board.getThreeExpoCards()[1].toString(), "big expo,vienna,4,5");
		assertEquals(board.getThreeExpoCards()[2].toString(), "little expo,roma,3,4");
		
	}
	
        
        /**
         * Test if the initArea has been a success
         * - test some area and their attributes
         * @throws IOException 
         */
        @Test
        public void testInitArea() throws IOException{
            System.out.println("testInitArea");
            Board b = new Board(3);
            
            // test name
            assertEquals( true , b.getAreas().containsKey("london")); 
            
            // test color
            assertEquals( "#ff5b2b" , ((ExcavationArea)b.getAreas().get("greece")).getCodeColor()); 
            
            // test distance
            String[] londonToPalestine = {"paris","roma","crete"};
            assertArrayEquals(londonToPalestine, b.getAreas().get("london").getDistances().get("palestine"));
            
            // test total point token 
            // test total point token of 4 
            Integer nbPointToken = 14 + Integer.parseInt(ConfigManager.getInstance().getConfig().getProperty("nbEmptyTokenPoint")); // (greece should have 13 pointTokens)
            Integer nbPointTokenOf4InsideGreece = 1; // (greece should have 3 pointTokens of 4)
            LinkedList<Token> tokens = ((ExcavationArea)b.getAreas().get("greece")).getTokenList();
            Integer countedPointToken = 0;
            Integer countedNbPointTokenOf4InsideCrete = 0;
            for (Token token : tokens) {
                if(token instanceof PointToken){
                    countedPointToken ++;
                    if( ((PointToken)token).getValue() == 4 ){
                        countedNbPointTokenOf4InsideCrete ++;
                    }
                }
            }
            assertEquals(nbPointToken, countedPointToken );
            assertEquals(nbPointTokenOf4InsideGreece, countedNbPointTokenOf4InsideCrete );
        }
	

}
