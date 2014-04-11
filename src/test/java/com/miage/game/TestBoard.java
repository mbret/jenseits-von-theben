package com.miage.game;

import com.miage.areas.ExcavationArea;
import com.miage.cards.Card;
import com.miage.cards.EthnologicalKnowledgeCard;
import com.miage.cards.ExcavationAuthorizationCard;
import com.miage.cards.ExpoCard;
import com.miage.cards.GeneralKnowledgeCard;
import com.miage.cards.ShovelCard;
import com.miage.cards.SpecificKnowledgeCard;
import com.miage.interfaces.UsableElement;
import com.miage.tokens.BlankToken;
import com.miage.tokens.PointToken;
import com.miage.tokens.Token;
import java.io.IOException;
import java.lang.reflect.Method;
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
import java.lang.reflect.*;
import java.util.Collections;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;





public class TestBoard {
	
	private Board board;
	private List<Card> fourCards;
	private Deck deckTest;
        Player maxime;
        Player richard;
        List<Card> allCards; // Contain all cards of the board (deck + four cards)
        List<Player> players;
                
        // Method private in board but accessible from these methods below
        Method method_pickCardOnBoard;
        Method method_addExpoCardOnBoard;
        Method method_actionPlayerDoPickCard;
        
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
            // init
            this.maxime = new Player("maxime", new PlayerToken("color"));
            this.richard = new Player("richard", new PlayerToken("color"));
            this.players = new ArrayList();
            this.players.add( maxime );
            this.players.add( richard );
            this.board = new Board(2, players);
            
            // Get all the cards
            this.allCards = new ArrayList();
            this.allCards.addAll( board.getFourCurrentCards() );
            this.allCards.addAll( board.getDeck() );
            this.allCards.addAll( board.getSideDeck() );

		
            // As board._addExpoCardOnBoard is private we need to set as accessible
            Class c = this.board.getClass();
            Class[] cArg = new Class[1];
            cArg[0] = ExpoCard.class;
            method_addExpoCardOnBoard = c.getDeclaredMethod("_addExpoCardOnBoard", cArg);
            method_addExpoCardOnBoard.setAccessible(true);
	
            // As board._pickCardOnBoard is private we need to set as accessible
            cArg = new Class[1];
            cArg[0] = Integer.class;
            method_pickCardOnBoard = c.getDeclaredMethod("_pickCardOnBoard", cArg);
            method_pickCardOnBoard.setAccessible(true);
            
            // As board._actionPlayerDoPickCard is private we need to set as accessible
             //Player player, Card cardToPickUp, boolean useZeppelinCard, boolean useCarCard
            cArg = new Class[4];
            cArg[0] = Player.class;
            cArg[1] = Card.class;
            cArg[2] = boolean.class;
            cArg[3] = boolean.class;
            method_actionPlayerDoPickCard = c.getDeclaredMethod("_actionPlayerDoPickCard", cArg);
            method_actionPlayerDoPickCard.setAccessible(true);
	}

	@After
	public void tearDown() throws Exception {
	}
        
	/**
	 * @author Gael
	 * 
	 * Test of the method to add an expo card on the board
	 */
	@Test
	public void testAddExpoCardOnTheBoard() throws IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException{
		
		
		ExpoCard card1 = new ExpoCard(0,"expo", "berlin", 4, true, 5);
		ExpoCard card2 = new ExpoCard(0,"expo", "rome", 3, false, 4);
		ExpoCard card3 = new ExpoCard(0,"expo", "vienna", 4, true, 5);
		ExpoCard card4 = new ExpoCard(0,"expo", "paris", 3, false, 4);
		

		method_addExpoCardOnBoard.invoke( board, card1);
		assertEquals(board.getExpoCards().get(0).toString(), "expo,berlin,4,5");
		
		method_addExpoCardOnBoard.invoke( board, card2);
		assertEquals(board.getExpoCards().get(0).toString(), "expo,rome,3,4");
		assertEquals(board.getExpoCards().get(1).toString(), "expo,berlin,4,5");
		
		method_addExpoCardOnBoard.invoke( board, card3);
		assertEquals(board.getExpoCards().get(0).toString(), "expo,vienna,4,5");
		assertEquals(board.getExpoCards().get(1).toString(), "expo,rome,3,4");
		assertEquals(board.getExpoCards().get(2).toString(), "expo,berlin,4,5");
		
		method_addExpoCardOnBoard.invoke( board, card4);
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
            Integer nbPointToken = 14; // (greece should have 15 pointTokens but one is placed on the excavation area for the first excavation)
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
            
            // Special decks when 2 players
            board = new Board(2);
            assertEquals( 95 - 4, board.getDeck().size()); // 95 cards - four cards
            assertTrue( board.getSideDeck().isEmpty());
            
            // Special deck when > 2 players
            board = new Board(3);
            assertEquals( 27+27 + 5, board.getDeck().size()); // 2 decks + small expo - four cards
            assertEquals( 27 + 5, board.getSideDeck().size() ); // 5 big expo + 1 deck
            
            
            // Now we get all cards of the board (four + deck)
            // We test only expo card because there are 4 cards which are picked randomly (so its impossible to test)
            boolean found1 = false;
            for (Card card : allCards) {
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
            
            assertEquals(board.getCurrentPlayerToken().getPosition().getName(),"warsaw");
            
            assertEquals(board.getFourCurrentCards().get(1).getDisplayName(),"shovel");
            assertEquals(board.getFourCurrentCards().get(1).getAreaName(),"london");
            assertEquals(board.getFourCurrentCards().get(1).getWeekCost(),2);
            
            assertEquals(board.getFourCurrentCards().get(2).getDisplayName(),"ethnologicalKnowledge");
            assertEquals(board.getFourCurrentCards().get(2).getAreaName(),"berlin");
            assertEquals(((EthnologicalKnowledgeCard)board.getFourCurrentCards().get(2)).getValue(),2);
            assertEquals(board.getFourCurrentCards().get(2).getWeekCost(),2);
            assertEquals(((EthnologicalKnowledgeCard)board.getFourCurrentCards().get(2)).getExcavationAreaName(),"greece");
            
            assertEquals(board.getFourCurrentCards().get(3).getDisplayName(),"ethnologicalKnowledge");
            assertEquals(board.getFourCurrentCards().get(3).getAreaName(),"rome");
            assertEquals(((EthnologicalKnowledgeCard)board.getFourCurrentCards().get(3)).getValue(),2);
            assertEquals(board.getFourCurrentCards().get(3).getWeekCost(),2);
            assertEquals(((EthnologicalKnowledgeCard)board.getFourCurrentCards().get(3)).getExcavationAreaName(),"egypt");
           
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
            List<Player> players = new ArrayList(){{
                this.add( player );
            }};
            Board board = new Board(4, players);
            
            HashMap<String, Object> playerActionParams = new HashMap();
            playerActionParams.put("player", player); // wet set the current player
            
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
            method_addExpoCardOnBoard.invoke( board, card );
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
        
        /**
         * Test the player action (pick a card on board).
         * 
         * @throws IOException
         * @throws IllegalAccessException
         * @throws IllegalArgumentException
         * @throws InvocationTargetException
         * @throws Exception 
         */
        @Test
        public void test_actionPlayerDoPickCard() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, Exception {
            
            // Player cards should be empty
            assertTrue( maxime.getCards().isEmpty() );
            
            // Now maxime will pick up a the first card on board
            Card cardPicked = board.getFourCurrentCards().get(0);
            
            // do action
            HashMap<String, Object> playerActionParams = new HashMap();
            playerActionParams.put("player", maxime); 
            playerActionParams.put("cardToPickUp", cardPicked);
            board.doPlayerRoundAction(Player.ACTION_PICK_CARD, playerActionParams);
            
            // Player go to pick a card, so he should have one card
            assertTrue( maxime.getCards().size() == 1 );
            assertEquals( cardPicked , maxime.getCards().get( 0 ) );
            
            
        }
        
        /**
         * Test the player action change four cards
         * 
         * @throws IOException
         * @throws IllegalAccessException
         * @throws IllegalArgumentException
         * @throws InvocationTargetException
         * @throws Exception 
         */
        @Test
        public void test_actionPlayerDoChangeFourCards() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, Exception {
            
            // We keep the actual four cards
            List<Card> previousFourCards = new ArrayList();
            previousFourCards.addAll( board.getFourCurrentCards() );
            
            // Now maxime will go to warsaw to change the four cards
            // do action
            HashMap<String, Object> playerActionParams = new HashMap();
            playerActionParams.put("player", maxime); 
            board.doPlayerRoundAction(Player.ACTION_CHANGE_FOUR_CARDS, playerActionParams);
            
            // Here the four new cards must be different
            boolean oneEqual = false;
            for (Card card : previousFourCards){
                for (Card card1 : board.getFourCurrentCards()) {
                    if(card.equals(card1)){
                        oneEqual = true;
                    }
                }
            }
            assertFalse(oneEqual);
            
            
        }
        
        /**
         * Test the player action do excavation
         * @throws Exception 
         */
        @Test
        public void test_actionPlayerDoExcavateArea() throws Exception{
            
            // We try to find one specific knowledge card for egypt to allow maxime to excavate
            for (Card card : allCards) {
                if( card instanceof SpecificKnowledgeCard && ( (((SpecificKnowledgeCard)card).getExcavationAreaName().equals("egypt"))) ){
                    maxime.getCards().add( card );
                    break;
                }
            }
            assertTrue( maxime.getSpecificCards( SpecificKnowledgeCard.class ).size() == 1);
            
            // Now maxime has one specific knowledge card for egypt
            // do action
            HashMap<String, Object> playerActionParams = new HashMap();
            playerActionParams.put("player", maxime); 
            playerActionParams.put("areaToExcavate", board.getArea("egypt")); 
            int knowledgePoint = maxime.getTotalAskedKnowledgePoint( board.getArea("egypt"), null); // no special used knowledge except the unique specific added previously
            playerActionParams.put("nbTokenToPickUp", board.getChronotime().getNbTokensToPickUp( knowledgePoint, 1));
            board.doPlayerRoundAction(Player.ACTION_EXCAVATE, playerActionParams);

            // Here maxime should has egypt excavated
            assertTrue( maxime.hasAlreadyExcavateArea("egypt") );
            // He took the first token on area so he has at least 1 point
            assertTrue( maxime.getTokens().size() > 0);
            // Player must be moved on egypt
            assertTrue( maxime.getPlayerToken().getPosition().equals( board.getArea("egypt") ) );
            // His timestamp must be dfferent from others players (who never moved)
            assertFalse( maxime.getPlayerToken().getTimeState().equals( richard.getPlayerToken().getTimeState() ) );
        }

}
