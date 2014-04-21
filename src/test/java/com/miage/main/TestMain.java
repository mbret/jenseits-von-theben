/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.main;

import com.miage.areas.TouristicArea;
import com.miage.cards.Card;
import com.miage.cards.EthnologicalKnowledgeCard;
import com.miage.cards.ExpoCard;
import com.miage.cards.GeneralKnowledgeCard;
import com.miage.cards.ShovelCard;
import com.miage.cards.SpecificKnowledgeCard;
import com.miage.game.Board;
import com.miage.game.Deck;
import com.miage.game.LogDisplay;
import com.miage.game.Player;
import com.miage.game.PlayerToken;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

/**
 *
 * @author David
 */
public class TestMain {
   
    Method method_pickCardOnBoard;
        Method method_addExpoCardOnBoard;
        
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	
		
            // As board._addExpoCardOnBoard is private we need to set as accessible
            Class c = Board.class;
            Class[] cArg = new Class[1];
            cArg[0] = ExpoCard.class;
            method_addExpoCardOnBoard = c.getDeclaredMethod("_addExpoCardOnBoard", cArg);
            method_addExpoCardOnBoard.setAccessible(true);

	}

	@After
	public void tearDown() throws Exception {
	}
        
        /**
         * Test the save of a game
         * @throws IOException 
         */
        @Test
        public void testSaveGame() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
            List<Player> players = new ArrayList();
            players.add( new Player( "maxime", new PlayerToken( "#40A497" )));
            players.add( new Player( "anne la plus belle <3", new PlayerToken( "#111111" )));
            players.add( new Player( "gael la pelle", new PlayerToken( "#502222" )));
            players.add( new Player( "rouchard coeur de lion, richmont la raclette", new PlayerToken( "#40A100" )));

            Board board = new Board(4, players);
            List<Card> fourCards = new LinkedList<Card>();
            
            fourCards.add(0,new GeneralKnowledgeCard(0,"generalKnowledge", "berlin", 2, 2));		
            fourCards.add(1,new GeneralKnowledgeCard(0,"generalKnowledge", "paris", 2, 2));		
            fourCards.add(2,new GeneralKnowledgeCard(0,"generalKnowledge", "rome", 2, 2));		
            fourCards.add(3,new GeneralKnowledgeCard(0,"generalKnowledge", "vienna", 2, 2));
            
            board.setFourCurrentCards(fourCards);
            
            Deck deckTest = new Deck();
            deckTest.add(new ExpoCard(0,"expo", "moscow", 4, true, 5));
            deckTest.add(new ExpoCard(0,"expo", "warsaw", 4, true, 5));
            deckTest.add(new GeneralKnowledgeCard(0,"generalKnowledge", "berlin", 2, 3));
            deckTest.add(new ShovelCard(0,"shovel", "london", 2));
            deckTest.add(new EthnologicalKnowledgeCard(0,"ethnologicalKnowledge", "berlin", 2, 2,"greece"));
            deckTest.add(new EthnologicalKnowledgeCard(0,"ethnologicalKnowledge", "rome", 2, 2,"egypt"));
            deckTest.add(new SpecificKnowledgeCard(0,"specificKnowledge", "rome", 2, 2,"crete"));
            
            ExpoCard card1 = new ExpoCard(0,"expo", "berlin", 4, true, 5);
            ExpoCard card2 = new ExpoCard(0,"expo", "rome", 3, false, 4);
            ExpoCard card3 = new ExpoCard(0,"expo", "vienna", 4, true, 5);
            ExpoCard card4 = new ExpoCard(0,"expo", "paris", 3, false, 4);

            method_addExpoCardOnBoard.invoke(board, card1);
            method_addExpoCardOnBoard.invoke(board, card2);
            method_addExpoCardOnBoard.invoke(board, card3);
            method_addExpoCardOnBoard.invoke(board, card4);
            
            board.setDeck(deckTest);
            Main main = new Main();
            
            for(PlayerToken pt : board.getPlayerTokensAndPlayers().keySet()){
                switch(pt.getColor()){
                    case "#40A497":
                        board.setCurrentPlayerToken(pt);
                        break;
                }
            }
            String tempDate = new SimpleDateFormat("[HH:mm:ss]").format(new Date());
            LogDisplay.cleanLogBackup();
            LogDisplay.displayAction(board,"move",new TouristicArea(1,"paris", "paris"));
            
            main.saveGame(board);
            
            assertEquals(board.getLogDisplay(),tempDate+" maxime se déplace sur paris.");
            boolean testReussi;
            try{
                FileReader fr = new FileReader("save.boobs");
                testReussi = true;
            } catch (FileNotFoundException fnfe) {
                testReussi = false;
            }
            assertTrue(testReussi);
        }
        
        /**
         * Test the loading of a game
         * @throws IOException 
         */
        @Test
        public void testLoadGame() throws IOException{
            Set<Player> players = new HashSet<Player>();
            Board board;
            Main main = new Main();
            LogDisplay.cleanLogBackup();
            board = main.loadGame();
            Set<PlayerToken> playersTokens =  board.getPlayerTokensAndPlayers().keySet();
            HashMap<PlayerToken, Player> playerTokensAndPlayers = board.getPlayerTokensAndPlayers();
            for(PlayerToken pt : playersTokens){
                switch(pt.getColor()){
                    case "#40A497":
                        assertEquals(playerTokensAndPlayers.get(pt).getName(),"maxime");
                        break;
                    case "#111111":
                        assertEquals(playerTokensAndPlayers.get(pt).getName(),"anne la plus belle <3");
                        break;
                    case "#502222":
                        assertEquals(playerTokensAndPlayers.get(pt).getName(),"gael la pelle");
                        break;
                    case "#40A100":
                        assertEquals(playerTokensAndPlayers.get(pt).getName(),"rouchard coeur de lion, richmont la raclette");
                        break;
                }
            }
            
            assertEquals(board.getExpoCards().get(0).getDisplayName(),"expo");
            assertEquals(board.getExpoCards().get(0).getAreaName(),"paris");
            assertEquals(board.getExpoCards().get(0).getValue(),4);
            assertEquals(board.getExpoCards().get(0).getWeekCost(),3);
            
            assertEquals(board.getExpoCards().get(1).getDisplayName(),"expo");
            assertEquals(board.getExpoCards().get(1).getAreaName(),"vienna");
            assertEquals(board.getExpoCards().get(1).getValue(),5);
            assertEquals(board.getExpoCards().get(1).getWeekCost(),4);
            
            assertEquals(board.getExpoCards().get(2).getDisplayName(),"expo");
            assertEquals(board.getExpoCards().get(2).getAreaName(),"rome");
            assertEquals(board.getExpoCards().get(2).getValue(),4);
            assertEquals(board.getExpoCards().get(2).getWeekCost(),3);
            
            assertEquals(board.getDeck().get(0).getDisplayName(),"expo");
            assertEquals(board.getDeck().get(0).getAreaName(),"moscow");
            assertEquals(((ExpoCard)board.getDeck().get(0)).getValue(),5);
            assertEquals(board.getDeck().get(0).getWeekCost(),4);
            
            assertEquals(board.getDeck().get(1).getDisplayName(),"expo");
            assertEquals(board.getDeck().get(1).getAreaName(),"warsaw");
            assertEquals(((ExpoCard)board.getDeck().get(1)).getValue(),5);
            assertEquals(board.getDeck().get(1).getWeekCost(),4);
            
            assertEquals(board.getDeck().get(2).getDisplayName(),"generalKnowledge");
            assertEquals(board.getDeck().get(2).getAreaName(),"berlin");
            assertEquals(((GeneralKnowledgeCard)board.getDeck().get(2)).getValue(),3);
            assertEquals(board.getDeck().get(2).getWeekCost(),2);
            
            assertEquals(board.getDeck().get(3).getDisplayName(),"shovel");
            assertEquals(board.getDeck().get(3).getAreaName(),"london");
            assertEquals(board.getDeck().get(3).getWeekCost(),2);
            
            assertEquals(board.getDeck().get(4).getDisplayName(),"ethnologicalKnowledge");
            assertEquals(board.getDeck().get(4).getAreaName(),"berlin");
            assertEquals(((EthnologicalKnowledgeCard)board.getDeck().get(4)).getValue(),2);
            assertEquals(board.getDeck().get(4).getWeekCost(),2);
            assertEquals(((EthnologicalKnowledgeCard)board.getDeck().get(4)).getExcavationAreaName(),"greece");
            
            assertEquals(board.getDeck().get(5).getDisplayName(),"ethnologicalKnowledge");
            assertEquals(board.getDeck().get(5).getAreaName(),"rome");
            assertEquals(((EthnologicalKnowledgeCard)board.getDeck().get(5)).getValue(),2);
            assertEquals(board.getDeck().get(5).getWeekCost(),2);
            assertEquals(((EthnologicalKnowledgeCard)board.getDeck().get(5)).getExcavationAreaName(),"egypt");
            
            assertEquals(board.getDeck().get(6).getDisplayName(),"specificKnowledge");
            assertEquals(board.getDeck().get(6).getAreaName(),"rome");
            assertEquals(((SpecificKnowledgeCard)board.getDeck().get(6)).getValue(),2);
            assertEquals(board.getDeck().get(6).getWeekCost(),2);
            assertEquals(((SpecificKnowledgeCard)board.getDeck().get(6)).getExcavationAreaName(),"crete");
            
            assertEquals(board.getFourCurrentCards().get(0).getDisplayName(),"generalKnowledge");
            assertEquals(board.getFourCurrentCards().get(0).getAreaName(),"berlin");
            assertEquals(((GeneralKnowledgeCard)board.getFourCurrentCards().get(0)).getValue(),2);
            assertEquals(board.getFourCurrentCards().get(0).getWeekCost(),2);
            
            assertEquals(board.getFourCurrentCards().get(1).getDisplayName(),"generalKnowledge");
            assertEquals(board.getFourCurrentCards().get(1).getAreaName(),"paris");
            assertEquals(((GeneralKnowledgeCard)board.getFourCurrentCards().get(1)).getValue(),2);
            assertEquals(board.getFourCurrentCards().get(1).getWeekCost(),2);
            
            assertEquals(board.getFourCurrentCards().get(2).getDisplayName(),"generalKnowledge");
            assertEquals(board.getFourCurrentCards().get(2).getAreaName(),"rome");
            assertEquals(((GeneralKnowledgeCard)board.getFourCurrentCards().get(2)).getValue(),2);
            assertEquals(board.getFourCurrentCards().get(2).getWeekCost(),2);
            
            assertEquals(board.getFourCurrentCards().get(3).getDisplayName(),"generalKnowledge");
            assertEquals(board.getFourCurrentCards().get(3).getAreaName(),"vienna");
            assertEquals(((GeneralKnowledgeCard)board.getFourCurrentCards().get(3)).getValue(),2);
            assertEquals(board.getFourCurrentCards().get(3).getWeekCost(),2);
            
            assertEquals(LogDisplay.getLogBackup().split("]")[1]," maxime se déplace sur paris.");
        }
}
