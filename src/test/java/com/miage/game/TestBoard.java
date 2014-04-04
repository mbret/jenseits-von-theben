package com.miage.game;

import com.miage.areas.ExcavationArea;
import com.miage.cards.Card;
import com.miage.cards.EthnologicalKnowledgeCard;
import com.miage.cards.ExpoCard;
import com.miage.cards.GeneralKnowledgeCard;
import com.miage.cards.ShovelCard;
import com.miage.cards.SpecificKnowledgeCard;
import com.miage.tokens.PointToken;
import com.miage.tokens.Token;
import java.io.IOException;
import java.util.HashMap;
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
		
		fourCards[0] = new GeneralKnowledgeCard(0,"generalKnowledge", "berlin", 2, 2);		
		fourCards[1] = new GeneralKnowledgeCard(0,"generalKnowledge", "paris", 2, 2);		
		fourCards[2] = new GeneralKnowledgeCard(0,"generalKnowledge", "rome", 2, 2);		
		fourCards[3] = new GeneralKnowledgeCard(0,"generalKnowledge", "vienna", 2, 2);		
		
		board.setFourCurrentCards(fourCards);
		
		deckTest = new Deck();
		deckTest.addCard(new ExpoCard(0, "expo", "moscow", 4, true, 5));
		deckTest.addCard(new ExpoCard(0,"expo", "warsaw", 4, true, 5));
		deckTest.addCard(new ShovelCard(0,"shovel", "london", 2));
		board.setDeck(deckTest);
		
		
	
	}

	@After
	public void tearDown() throws Exception {
	}

	
	/**
	 * @author Gael
	 * Test of the method pickCardOnBoard
	 * 
	 */
	@Test
	public void testPickCardOnBoard() {
		
		Card card = board.pickCardOnBoard(3);
		assertEquals(board.getFourCurrentCards()[3].toString(), "shovel,london,2");
		assertEquals(card.getAreaName(), "vienna");
		assertEquals(board.getThreeExpoCards()[0].toString(), "expo,warsaw,4,5");
		assertEquals(board.getThreeExpoCards()[1].toString(), "expo,moscow,4,5");
		
			
	}
	
	
	/**
	 * @author Gael
	 * 
	 * Test of the method to add an expo card on the board
	 */
	@Test
	public void testAddExpoCardOnTheBoard(){
		
		
		ExpoCard card1 = new ExpoCard(0,"expo", "berlin", 4, true, 5);
		ExpoCard card2 = new ExpoCard(0,"expo", "roma", 3, false, 4);
		ExpoCard card3 = new ExpoCard(0,"expo", "vienna", 4, true, 5);
		ExpoCard card4 = new ExpoCard(0,"expo", "paris", 3, false, 4);
		
		board.addExpoCardOnBoard(card1);
		assertEquals(board.getThreeExpoCards()[0].toString(), "expo,berlin,4,5");
		
		board.addExpoCardOnBoard(card2);
		assertEquals(board.getThreeExpoCards()[0].toString(), "expo,roma,3,4");
		assertEquals(board.getThreeExpoCards()[1].toString(), "expo,berlin,4,5");
		
		board.addExpoCardOnBoard(card3);
		assertEquals(board.getThreeExpoCards()[0].toString(), "expo,vienna,4,5");
		assertEquals(board.getThreeExpoCards()[1].toString(), "expo,roma,3,4");
		assertEquals(board.getThreeExpoCards()[2].toString(), "expo,berlin,4,5");
		
		board.addExpoCardOnBoard(card4);
		assertEquals(board.getThreeExpoCards()[0].toString(), "expo,paris,3,4");
		assertEquals(board.getThreeExpoCards()[1].toString(), "expo,vienna,4,5");
		assertEquals(board.getThreeExpoCards()[2].toString(), "expo,roma,3,4");
		
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
            Integer nbPointToken = 31; // (greece should have 31 pointTokens)
            Integer nbPointTokenOf4InsideGreece = 1; // (greece should have 3 pointTokens of 4)
            LinkedList<Token> tokens = ((ExcavationArea)b.getAreas().get("greece")).getTokenList();
            Integer countedPointToken = 0;
            int expectedNbEmptyTokens = 16;
            int nbEmptyTokens = 0;
            Integer countedNbPointTokenOf4InsideCrete = 0;
            for (Token token : tokens){
                if(token instanceof PointToken){
                    countedPointToken ++;
                    if( ((PointToken)token).getValue() == 0 || ((PointToken)token).getValue() == null){
                        nbEmptyTokens++;
                    }
                    if( ((PointToken)token).getValue() == 4 ){
                        countedNbPointTokenOf4InsideCrete ++;
                    }
                }
            }
            assertEquals(expectedNbEmptyTokens, nbEmptyTokens);
            assertEquals(nbPointToken, countedPointToken );
            assertEquals(nbPointTokenOf4InsideGreece, countedNbPointTokenOf4InsideCrete );
        }
        
        /**
         * Valid only for 95 cards in total
         * @throws IOException 
         */
        @Test
        public void testInitCards() throws IOException{
            System.out.println("testInitCards");
            
            Board b = new Board(2);
            assertEquals( 95 - 4, b.getDeck().size()); // 95 cards - four cards
            assertTrue( b.getSideDeck().isEmpty());
            
            b = new Board(3);
            assertEquals( 27+27 + 5, b.getDeck().size()); // 2 decks + small expo - four cards
            assertEquals( 27 + 5, b.getSideDeck().size() ); // 5 big expo + 1 deck
            
            boolean found1 = false;
            
            // We test only expo card because there are 4 cards which are picked randomly (so its impossible to test)
            for (Card card : b.getDeck()) {
                // deck must have => card.1 = london,excavationAuthorizationCard,3
                if( card instanceof ExpoCard && ! ((ExpoCard)card).isBigExpo() && card.getAreaName().equals("berlin")){
                    found1 = true;
                }
            }
            assertTrue(found1);
        }
        
        @Test
        public void testChangeFourCurrentCards() throws IOException{
            /*
             * Initialize a player
             */
            Player p1 = new Player("player");
            PlayerToken pt = new PlayerToken("blue");
            HashMap<PlayerToken, Player> playersAndTokens= new HashMap<PlayerToken, Player>();
            playersAndTokens.put(pt, p1);
            pt.setPosition(board.getArea("warsaw"));
            board.setPlayerTokensAndPlayers(playersAndTokens);
            board.setCurrentPlayerToken(pt);
            
            /*
             * Set some cards into the deck
             */
            Deck deckTest2 = new Deck();
            deckTest2.addCard(new ExpoCard(0,"expo", "moscow", 4, true, 5));
            deckTest2.addCard(new ExpoCard(0,"expo", "warsaw", 4, true, 5));
            deckTest2.addCard(new GeneralKnowledgeCard(0,"generalKnowledge", "berlin", 2, 3));
            deckTest2.addCard(new ShovelCard(0,"shovel", "london", 2));
            deckTest2.addCard(new EthnologicalKnowledgeCard(0,"ethnologicalKnowledge", "berlin", 2, 2,"greece"));
            deckTest2.addCard(new EthnologicalKnowledgeCard(0,"ethnologicalKnowledge", "rome", 2, 2,"egypt"));
            deckTest2.addCard(new SpecificKnowledgeCard(0,"specificKnowledge", "rome", 2, 2,"crete"));
            board.setDeck(deckTest2);
            
            board.changeFourCurrentCards();
            
            assertEquals(board.getCurrentPlayerToken().getPosition().toString(),"warsaw");
//            assertEquals(board.getFourCurrentCards()[0].toString(),"generalKnowledge,berlin,2,3");
            assertEquals(board.getFourCurrentCards()[1].toString(),"shovel,london,2");	
            assertEquals(board.getFourCurrentCards()[2].toString(),"ethnologicalKnowledge,berlin,2,2,greece");	
            assertEquals(board.getFourCurrentCards()[3].toString(),"ethnologicalKnowledge,rome,2,2,egypt");
           
        }
	

}
