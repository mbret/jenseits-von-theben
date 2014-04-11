package com.miage.game;

import com.miage.areas.Area;
import com.miage.areas.ExcavationArea;
import com.miage.cards.*;
import java.io.IOException;
import java.time.LocalDate;
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
            this.deck.add(new SpecificKnowledgeCard(0,"specificKnowledge", "paris", 2, 3, "greece"));
            this.deck.add(new GeneralKnowledgeCard(0,"generalKnowledge", "vienna", 2, 3));
            this.deck.add(new GeneralKnowledgeCard(0,"generalKnowledge", "rome", 2, 3));
            this.deck.add(new GeneralKnowledgeCard(0,"generalKnowledge", "moscow", 2, 3));
            this.deck.add(new GeneralKnowledgeCard(0,"generalKnowledge", "warsaw", 2, 3));
            this.deck.add(new AssistantCard(0,"assistant", "london", 2));
            this.deck.add(new AssistantCard(0,"assistant", "rome", 2));
            this.deck.add(new AssistantCard(0,"assistant", "vienna", 2));
            this.deck.add(new CarCard(0,"car", "berlin", 2));
            this.deck.add(new ZeppelinCard(0,"paris", 2));
            this.deck.add(new CongressCard(0,"rome", 2));
            this.deck.add(new CongressCard(0,"paris", 2));
            this.deck.add(new CongressCard(0,"berlin", 2));
            this.deck.add(new EthnologicalKnowledgeCard(0,"warsaw", 2, 3, "greece"));
            this.deck.add(new ExcavationAuthorizationCard(0,"warsaw", 2));
            this.deck.add(new ExpoCard(0,"expo", "warsaw", 2, true, 5));
            this.deck.add(new ExpoCard(0,"expo", "moscow", 2, false, 4));
            this.deck.add(new ShovelCard(0, "shovel", "warsaw", 2));
            this.deck.add(new ShovelCard(0,"shovel", "london", 2));


            List<Card> fourCards = new LinkedList();

            fourCards.add( new AssistantCard(0,"assistant", "berlin", 2));		
            fourCards.add( new GeneralKnowledgeCard(0,"generalKnowledge", "paris", 2, 3));		
            fourCards.add( new GeneralKnowledgeCard(0,"generalKnowledge", "rome", 2, 3));		
            fourCards.add( new GeneralKnowledgeCard(0,"generalKnowledge", "vienna", 2, 3));

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
//        player.pickCard(board,2);
//        assertEquals(player.getCards().toString(), "[generalKnowledge,rome,2,3]");
//        player.pickCard(board, 2);
//        assertEquals(player.getCards().toString(), "[generalKnowledge,rome,2,3, specificKnowledge,paris,2,3,greece]");
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
     * We test if the player contain the specific card we need
     * @author Maxime
     */
    @Test
    public void testHasSpecificCard(){
        Player player2 = new Player("");
        player2.getCards().add(new ExcavationAuthorizationCard(0, "ExcavationAuthorizationCard", "london", 0));
        assertTrue( player2.hasSpecificCard( ExcavationAuthorizationCard.class ));
    }

    /**
     * Test if the player is authorized to excavate any one the excavate areas
     * @author maxime
     */
    @Test
    public void testIsAuthorizedToExcavateOneArea() throws IOException{
        final Player player2 = new Player("");
        List<Player> players = new ArrayList(){{
           this.add( player2 );
        }};
        Board board = new Board(4, players);

        // Not enough knowledge yet
        assertFalse(player2.isAuthorizedToExcavateOneArea( board.getAreas( ExcavationArea.class ).values() ));

        // Specific knowledge for egypt
        player2.getCards().add( new SpecificKnowledgeCard(0, "SpecificKnowledgeCard", "berlin", 0, 1, "egypt") );
        assertTrue(player2.isAuthorizedToExcavateOneArea( board.getAreas( ExcavationArea.class ).values() ));

        // Specific knowledge for egypt but already visited
        player2.addAreaAlreadyExcavate( "egypt" );
        assertFalse(player2.isAuthorizedToExcavateOneArea( board.getAreas( ExcavationArea.class ).values() ));

        // Specific knowledge for egypt but already visited but AuthorizationExcavationCard
        player2.getCards().add( new ExcavationAuthorizationCard(0, "ExcavationAuthorizationCard", "berlin", 0));
        assertTrue(player2.isAuthorizedToExcavateOneArea( board.getAreas( ExcavationArea.class ).values() ));

        // Specific knowledge for egypt but already visited and general knowledge
        player2.getCards().remove(0);
        player2.getCards().add( new GeneralKnowledgeCard(0, "GeneralKnowledgeCard", "berlin", 0, 1) );
        assertFalse(player2.isAuthorizedToExcavateOneArea( board.getAreas( ExcavationArea.class ).values() ));
    }

    /**
     * Test if player is authorized to excavate one area
     * @author david
     * @throws java.io.IOException
     */
    @Test
    public void testIsAuthorizedToExcavateArea() throws IOException{

//        this.player.addCompetencesPointsOrKnowledge(new SpecificKnowledgeCard(0,"specificKnowledge", "paris", 2, 3, "greece"));
        this.player.getCards().add( new SpecificKnowledgeCard(0,"specificKnowledge", "paris", 2, 3, "greece") );
        this.player.addAreaAlreadyExcavate("egypt"); 
        assertTrue(this.player.isAuthorizedToExcavateArea( new ExcavationArea(0, "greece", null, null) ));
        this.player.addAreaAlreadyExcavate("greece");
        assertFalse(this.player.isAuthorizedToExcavateArea( new ExcavationArea(0, "greece", null, null) ));
//        this.player.addCompetencesPointsOrKnowledge(new ExcavationAuthorizationCard(0, "ExcavationAuthorizationCard", "warsaw", 2));
        this.player.getCards().add( new ExcavationAuthorizationCard(0, "ExcavationAuthorizationCard", "warsaw", 2) );
        assertFalse(this.player.isAuthorizedToExcavateArea( new ExcavationArea(0, "warsaw", null, null) ));
        assertFalse(this.player.isAuthorizedToExcavateArea( new ExcavationArea(0, "egypt", null, null) ));
        assertTrue(this.player.isAuthorizedToExcavateArea( new ExcavationArea(0, "greece", null, null) ));
        
        // Refaire cette derniere partie
//        this.player.getCompetences().put("excavationAuthorization", this.player.getCompetences().get("excavationAuthorization")-1);
//        assertFalse(this.player.isAuthorizedToExcavateArea( new ExcavationArea(0, "rome", null, null) ));
//        assertFalse(this.player.isAuthorizedToExcavateArea( new ExcavationArea(0, "greece", null, null) ));
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

//        this.deck = new Deck();
//        this.deck.add(new AssistantCard(0,"assistant", "london", 2));
//        this.deck.add(new CarCard(0,"car", "berlin", 2));
//        this.deck.add(new AssistantCard(0,"assistant", "rome", 2));
//        this.deck.add(new AssistantCard(0,"assistant", "vienna", 2));
//        this.deck.add(new ZeppelinCard(0,"zeppelin", "paris", 2));
//
//        this.board.setDeck(this.deck);
//
//        // player retrieve first card or fourCurrentCard
//        player.getCards().add( board.pickCardOnBoard( board.getFourCurrentCards().get(0) ) );
//
//        assertEquals(player.getCards().size(), 5);
//        assertEquals(player.getCards().get(0).getDisplayName(), "assistant");
//        assertEquals(player.getCards().get(0).getAreaName(), "berlin");
//
//        assertEquals(board.getSideDeck().size(), 0);
//
//        player.useCard(player.getCards().get(0), board.getSideDeck());
//
//        assertEquals(player.getCards().size(), 4);
//        assertEquals(player.getCards().get(0).getDisplayName(), "assistant");
//        assertEquals(player.getCards().get(0).getAreaName(), "london");
//
//        assertEquals(board.getSideDeck().get(0).getDisplayName(), "assistant");
//        assertEquals(board.getSideDeck().get(0).getAreaName(), "berlin");
//
//        assertEquals(board.getSideDeck().size(), 1);
//
//        player.useCard(player.getCards().get(0), board.getSideDeck());
//        player.useCard(player.getCards().get(0), board.getSideDeck());
//
//        assertEquals(player.getCards().size(), 3);
//        assertEquals(board.getSideDeck().size(), 2);

    }

    /**
     * Test of the method which calcul the total of knowledge points
     */
    @Test
    public void testTotalPointsOfKnowledge(){

//            for(int i=0;i < 8;i++)
////            this.player.pickCard(board, 3);
//            this.player.getCards().add( this.board.pickCardOnBoard( board.getFourCurrentCards().get(3) ) );
//            assertEquals(this.player.totalKnowledgePoints(board.getArea("greece"), true), 6);
//            assertEquals(this.player.totalKnowledgePoints(board.getArea("greece"), false), 6);
//
//            for(int i=0;i < 8;i++)
//                this.player.getCards().add( this.board.pickCardOnBoard( board.getFourCurrentCards().get(3) ) );
////                    this.player.pickCard(board, 3);
//
//            assertEquals(this.player.totalKnowledgePoints(board.getArea("greece"), true), 12);
//            assertEquals(this.player.totalKnowledgePoints(board.getArea("greece"), false), 6);

    }

    /**
     * Test of addAreaAlreadyExcavate method, of class Player.
     */
    @Test
    public void testAddAreaAlreadyExcavate() {
        // ...
    }

    /**
     * Test of getSpecificCards method, of class Player.
     */
    @Test
    public void testGetSpecificCards() {
        // ...
    }

    /**
     * Test of getSpecificTokens method, of class Player.
     */
    @Test
    public void testGetSpecificTokens() {
        // ...
    }

    /**
     * Test of hasSpecificKnowledgeCardForThisExcavationArea method, of class Player.
     */
    @Test
    public void testHasSpecificKnowledgeCardForThisExcavationArea() {
        // ...
    }

    /**
     * Test of hasKnowledgeCardForThisExcavationArea method, of class Player.
     */
    @Test
    public void testHasKnowledgeCardForThisExcavationArea() {
        // ...
    }

    /**
     * Test of hasSpecificKnowledgeTokenForThisExcavationArea method, of class Player.
     */
    @Test
    public void testHasSpecificKnowledgeTokenForThisExcavationArea() {
        // ...
    }

    /**
     * Test of hasKnowledgeTokenForThisExcavationArea method, of class Player.
     */
    @Test
    public void testHasKnowledgeTokenForThisExcavationArea() {
        // ...
    }

    /**
     * Test of updateCompetencesPointsOrKnowledge method, of class Player.
     */
    @Test
    public void testUpdateCompetencesPointsOrKnowledge() {
        // ...
    }

    /**
     * Test of addCompetencesPointsOrKnowledge method, of class Player.
     */
    @Test
    public void testAddCompetencesPointsOrKnowledge() {
        // ...
    }

    /**
     * Test of removeCompetencesPointsOrKnowledge method, of class Player.
     */
    @Test
    public void testRemoveCompetencesPointsOrKnowledge() {
        // ...
    }

    /**
     * Test of useCard method, of class Player.
     */
    @Test
    public void testUseCard() {
        // ...
    }

    /**
     * Test of totalKnowledgePoints method, of class Player.
     */
    @Test
    public void testTotalKnowledgePoints() {
        // ...
    }
	
	

}
