package com.miage.game;

import Interface.UsableElement;
import com.miage.areas.ExcavationArea;
import com.miage.cards.Card;
import com.miage.cards.EthnologicalKnowledgeCard;
import com.miage.cards.ExcavationAuthorizationCard;
import com.miage.cards.ExpoCard;
import com.miage.cards.GeneralKnowledgeCard;
import com.miage.cards.ShovelCard;
import com.miage.cards.SpecificKnowledgeCard;
import com.miage.tokens.BlankToken;
import com.miage.tokens.PointToken;
import com.miage.tokens.Token;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;





public class TestBoard {
	
	private Board board;
	private List<Card> fourCards;
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
            fourCards = new LinkedList();

            fourCards.add( new GeneralKnowledgeCard(0,"generalKnowledge", "berlin", 2, 2) );		
            fourCards.add( new GeneralKnowledgeCard(0,"generalKnowledge", "paris", 2, 2) );		
            fourCards.add( new GeneralKnowledgeCard(0,"generalKnowledge", "rome", 2, 2) );		
            fourCards.add( new GeneralKnowledgeCard(0,"generalKnowledge", "vienna", 2, 2) );		

            board.setFourCurrentCards(fourCards);

            deckTest = new Deck();
            deckTest.add(new ExpoCard(0, "expo", "moscow", 4, true, 5));
            deckTest.add(new ExpoCard(0,"expo", "warsaw", 4, true, 5));
            deckTest.add(new ShovelCard(0,"shovel", "london", 2));
            board.setDeck(deckTest);
		
		
	
	}

	@After
	public void tearDown() throws Exception {
	}

	
	/**
	 * @author maxime
	 * Test of the method pickCardOnBoard
	 * 
	 */
	@Test
	public void testPickCardOnBoard() throws IOException {
            final Player player = new Player("maxime", new PlayerToken("color"));
            Board board = new Board(2, new HashSet<Player>(){{ this.add( player ); }} );
            
            // we check the fourth card 
            Card card4 = board.getFourCurrentCards().get( 3 );
            assertEquals( card4, board.pickCardOnBoard( 3 ) );
            
            // now the fourth card should be different
            assertNotSame( card4, board.pickCardOnBoard( 3 ) );
	}
	
        
        
	/**
	 * @author Gael
	 * 
	 * Test of the method to add an expo card on the board
	 */
	@Test
	public void testAddExpoCardOnTheBoard(){
		
		
		ExpoCard card1 = new ExpoCard(0,"expo", "berlin", 4, true, 5);
		ExpoCard card2 = new ExpoCard(0,"expo", "rome", 3, false, 4);
		ExpoCard card3 = new ExpoCard(0,"expo", "vienna", 4, true, 5);
		ExpoCard card4 = new ExpoCard(0,"expo", "paris", 3, false, 4);
		
		board.addExpoCardOnBoard(card1);
		assertEquals(board.getExpoCards().get(0).toString(), "expo,berlin,4,5");
		
		board.addExpoCardOnBoard(card2);
		assertEquals(board.getExpoCards().get(0).toString(), "expo,rome,3,4");
		assertEquals(board.getExpoCards().get(1).toString(), "expo,berlin,4,5");
		
		board.addExpoCardOnBoard(card3);
		assertEquals(board.getExpoCards().get(0).toString(), "expo,vienna,4,5");
		assertEquals(board.getExpoCards().get(1).toString(), "expo,rome,3,4");
		assertEquals(board.getExpoCards().get(2).toString(), "expo,berlin,4,5");
		
		board.addExpoCardOnBoard(card4);
		assertEquals(board.getExpoCards().get(0).toString(), "expo,paris,3,4");
		assertEquals(board.getExpoCards().get(1).toString(), "expo,vienna,4,5");
		assertEquals(board.getExpoCards().get(2).toString(), "expo,rome,3,4");
		
	}
	
        
        /**
         * Test if the initArea has been a success
         * - test some area and their attributes
         * @throws IOException 
         */
        @Test
        public void testInitArea() throws IOException{
            Board b = new Board(3);
            
            // test name
            assertEquals( true , b.getAreas().containsKey("london")); 
            
            // test color
            assertEquals( "#ff5b2b" , ((ExcavationArea)b.getAreas().get("greece")).getCodeColor()); 
            
            // test distance
            String[] londonToPalestine = {"paris","rome","crete"};
            assertArrayEquals(londonToPalestine, b.getAreas().get("london").getDistances().get("palestine"));
            
            // test total point token 
            // test total point token of 4 
            Integer nbPointToken = 15; // (greece should have 15 pointTokens)
            Integer nbPointTokenOf4InsideGreece = 1; // (greece should have 3 pointTokens of 4)
            LinkedList<Token> tokens = ((ExcavationArea)b.getAreas().get("greece")).getTokenList();
            Integer countedPointToken = 0;
            int expectedNbEmptyTokens = 16;
            int nbEmptyTokens = 0;
            Integer countedNbPointTokenOf4InsideCrete = 0;
            for (Token token : tokens){
                if(token instanceof PointToken){
                    countedPointToken ++;
                    if( ((PointToken)token).getValue() == 4 ){
                        countedNbPointTokenOf4InsideCrete ++;
                    }
                }
                else if( token instanceof BlankToken ){
                    nbEmptyTokens++;
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
            deckTest2.add(new ExpoCard(0,"expo", "moscow", 4, true, 5));
            deckTest2.add(new ExpoCard(0,"expo", "warsaw", 4, true, 5));
            deckTest2.add(new GeneralKnowledgeCard(0,"generalKnowledge", "berlin", 2, 3));
            deckTest2.add(new ShovelCard(0,"shovel", "london", 2));
            deckTest2.add(new EthnologicalKnowledgeCard(0,"ethnologicalKnowledge", "berlin", 2, 2,"greece"));
            deckTest2.add(new EthnologicalKnowledgeCard(0,"ethnologicalKnowledge", "rome", 2, 2,"egypt"));
            deckTest2.add(new SpecificKnowledgeCard(0,"specificKnowledge", "rome", 2, 2,"crete"));
            board.setDeck(deckTest2);
            
            board.changeFourCurrentCards();
            
            assertEquals(board.getCurrentPlayerToken().getPosition().toString(),"warsaw");
//            assertEquals(board.getFourCurrentCards()[0].toString(),"generalKnowledge,berlin,2,3");
            assertEquals(board.getFourCurrentCards().get(1).toString(),"shovel,london,2");	
            assertEquals(board.getFourCurrentCards().get(2).toString(),"ethnologicalKnowledge,berlin,2,2,greece");	
            assertEquals(board.getFourCurrentCards().get(3).toString(),"ethnologicalKnowledge,rome,2,2,egypt");
           
        }
	
        
        /**
         * Test if a player has enough time before end game
         */
        @Test
        public void testHasEnoughTimeBeforeEndGame(){
            LocalDate startDate = LocalDate.of(1900, 1, 1);
            assertTrue( Board.hasEnoughTimeBeforeEndGame(startDate, 10, startDate.plusWeeks(10))); // time for 10 supplementary weeks
            assertFalse( Board.hasEnoughTimeBeforeEndGame(startDate, 10, startDate.plusWeeks(9))); // time for 10 supplementary weeks
        }
        
        
        
        /**
         * Test all method to check if a player can make a round action
         * @throws IOException 
         */
        @Test
        public void testIsPlayerAbleToMakeRoundAction() throws IOException, Exception{
            final Player player = new Player("maxime", new PlayerToken("color"));
            HashSet<Player> players = new HashSet(){{
                this.add( player );
            }};
            Board board = new Board(4, players);
            
            HashMap<String, Object> playerActionParams = new HashMap();
            playerActionParams.put("player", player); // wet set the current player
            playerActionParams.put("usedElements", new ArrayList<UsableElement>());
            playerActionParams.put("areaToExcavate", null);
            playerActionParams.put("cardToPickUp", null);
            playerActionParams.put("expoCardToDo", null);
            playerActionParams.put("nbWeeksForExcavation", null);
            
            // test all 4 actions
            assertTrue(board.isPlayerAbleToMakeRoundAction( Player.ACTION_CHANGE_FOUR_CARDS, playerActionParams));
            for (ExcavationArea area : board.getAreas( ExcavationArea.class ).values()) {
                playerActionParams.put("areaToExcavate", area);
                assertFalse( board.isPlayerAbleToMakeRoundAction( Player.ACTION_EXCAVATE, playerActionParams) );
            }
            for (ExpoCard card : board.getExpoCards()) {
                playerActionParams.put("expoCardToDo", card);
                assertFalse( board.isPlayerAbleToMakeRoundAction( Player.ACTION_ORGANIZE_EXPO, playerActionParams) );
            }
            for (Card card : board.getFourCurrentCards()) {
                playerActionParams.put("cardToPickUp", card);
                assertTrue( board.isPlayerAbleToMakeRoundAction( Player.ACTION_PICK_CARD, playerActionParams) );
            }
            
            // player have special authorization to excavate but not enough point
            player.getCards().add( new ExcavationAuthorizationCard(0, "ExcavationAuthorizationCard", "berlin", 0));
            for (ExcavationArea area : board.getAreas( ExcavationArea.class ).values()) {
                playerActionParams.put("areaToExcavate", area);
                assertFalse( board.isPlayerAbleToMakeRoundAction( Player.ACTION_EXCAVATE, playerActionParams) );
            }
            
            // player have special authorization to excavate and general point
            player.getCards().add( new GeneralKnowledgeCard(0, null, null, 0, 0));
            for (ExcavationArea area : board.getAreas( ExcavationArea.class ).values()) {
                playerActionParams.put("areaToExcavate", area);
                assertFalse( board.isPlayerAbleToMakeRoundAction( Player.ACTION_EXCAVATE, playerActionParams) );
            }
            
            // player have special authorization to excavate and general point and specific knowledge point
            player.getCards().add( new SpecificKnowledgeCard(0, "SpecificKnowledgeCard", "berlin", 0, 1, "egypt") );
            playerActionParams.put("areaToExcavate", (ExcavationArea)board.getArea("egypt"));
            assertTrue( board.isPlayerAbleToMakeRoundAction( Player.ACTION_EXCAVATE, playerActionParams) );
            
            // player have not enough point to make expo in berlin
            player.getTokens().add( new PointToken(null, "egypt", null, 2));
            ExpoCard card = new ExpoCard(0, null, "berlin", 0, true, 5);
            card.setTokens(
                new ArrayList<PointToken>(){{
                    this.add(new PointToken(null, "egypt", null, 1));
                    this.add(new PointToken(null, "crete", null, 2));
                }}
            );
            board.addExpoCardOnBoard( card );
            playerActionParams.put("expoCardToDo", card);
            assertFalse( board.isPlayerAbleToMakeRoundAction( Player.ACTION_ORGANIZE_EXPO, playerActionParams) );
            
            // player have enough point to make this expo in berlin
            player.getTokens().add( new PointToken(null, "crete", null, 1));
            player.getTokens().add( new PointToken(null, "crete", null, 1));
            assertTrue( board.isPlayerAbleToMakeRoundAction( Player.ACTION_ORGANIZE_EXPO, playerActionParams) );
            
            // player is on the end of the board position so he is not able to do anything anymore
            player.getPlayerToken().setTimeState( board.getEndGameDatePosition() );
            player.getPlayerToken().setPosition( board.getArea("egypt") ); // we need it in case of the player is on an touristic city and one of the fourcurrentcard is about this area
            for (Card card2 : board.getFourCurrentCards()) {
                playerActionParams.put("cardToPickUp", card2);
                assertFalse( board.isPlayerAbleToMakeRoundAction( Player.ACTION_PICK_CARD, playerActionParams) );
            }
            assertFalse(board.isPlayerAbleToMakeRoundAction( Player.ACTION_CHANGE_FOUR_CARDS, playerActionParams));
            
            
        }
        
        

}
