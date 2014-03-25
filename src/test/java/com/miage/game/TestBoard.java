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




public class TestBoard {
	
	private Board board;
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
		deckTest.addCard(new GeneralKnowledgeCard("moscow", 2, 2));
		board.setDeck(deckTest);
		
			
            board = new Board(3);
            board.initializationDecks();
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
	public void testpickCardOnBoard() {
		
		Card card = board.pickCardOnBoard(3);
		assertEquals(board.getFourCurrentCards()[3].toString(), "generalKnowledge,moscow,2,2");
		assertEquals(card.getAreaName(), "vienna");
		
		
		
		
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
