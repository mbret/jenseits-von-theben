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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author David
 */
public class TestMain {
   
        /**
         * Test the save of a game
         * @throws IOException 
         */
        @Test
        public void testSaveGame() throws IOException{
//            Set<Player> players = new HashSet(){{
//                this.add( new Player( "maxime", new PlayerToken( "#40A497" )));
//                this.add( new Player( "anne la plus belle <3", new PlayerToken( "#111111" )));
//                this.add( new Player( "gael la pelle", new PlayerToken( "#502222" )));
//                this.add( new Player( "rouchard coeur de lion, richmont la raclette", new PlayerToken( "#40A100" )));
//            }};
//            Board board = new Board(4, players);
//            
//            Card[] fourCards = new Card[4];
//            
//            fourCards[0] = new GeneralKnowledgeCard(0,"generalKnowledge", "berlin", 2, 2);		
//            fourCards[1] = new GeneralKnowledgeCard(0,"generalKnowledge", "paris", 2, 2);		
//            fourCards[2] = new GeneralKnowledgeCard(0,"generalKnowledge", "rome", 2, 2);		
//            fourCards[3] = new GeneralKnowledgeCard(0,"generalKnowledge", "vienna", 2, 2);
//            
//            board.setFourCurrentCards(fourCards);
//            
//            Deck deckTest = new Deck();
//            deckTest.addCard(new ExpoCard(0,"expo", "moscow", 4, true, 5));
//            deckTest.addCard(new ExpoCard(0,"expo", "warsaw", 4, true, 5));
//            deckTest.addCard(new GeneralKnowledgeCard(0,"generalKnowledge", "berlin", 2, 3));
//            deckTest.addCard(new ShovelCard(0,"shovel", "london", 2));
//            deckTest.addCard(new EthnologicalKnowledgeCard(0,"ethnologicalKnowledge", "berlin", 2, 2,"greece"));
//            deckTest.addCard(new EthnologicalKnowledgeCard(0,"ethnologicalKnowledge", "rome", 2, 2,"egypt"));
//            deckTest.addCard(new SpecificKnowledgeCard(0,"specificKnowledge", "rome", 2, 2,"crete"));
//            
//            ExpoCard card1 = new ExpoCard(0,"expo", "berlin", 4, true, 5);
//            ExpoCard card2 = new ExpoCard(0,"expo", "rome", 3, false, 4);
//            ExpoCard card3 = new ExpoCard(0,"expo", "vienna", 4, true, 5);
//            ExpoCard card4 = new ExpoCard(0,"expo", "paris", 3, false, 4);
//
//            board.addExpoCardOnBoard(card1);
//            board.addExpoCardOnBoard(card2);
//            board.addExpoCardOnBoard(card3);
//            board.addExpoCardOnBoard(card4);
//            
//            board.setDeck(deckTest);
//            Main main = new Main();
//            
//            for(PlayerToken pt : board.getPlayerTokensAndPlayers().keySet()){
//                switch(pt.getColor()){
//                    case "#40A497":
//                        board.setCurrentPlayerToken(pt);
//                        break;
//                }
//            }
//            String tempDate = new SimpleDateFormat("[HH:mm:ss]").format(new Date());
//            LogDisplay.cleanLogBackup();
//            LogDisplay.displayAction(board,"move",new TouristicArea(1,"paris"));
//            
//            main.saveGame(board,"save");
//            
//            assertEquals(board.getLogDisplay(),tempDate+" maxime se déplace sur paris.");
//            boolean testReussi;
//            try{
//                FileReader fr = new FileReader("save.boobs");
//                testReussi = true;
//            } catch (FileNotFoundException fnfe) {
//                testReussi = false;
//            }
//            assertTrue(testReussi);
            
        }
        
        /**
         * Test the loading of a game
         * @throws IOException 
         */
        @Test
        public void testLoadGame() throws IOException{
//            Set<Player> players = new HashSet<Player>();
//            Board board;
//            Main main = new Main();
//            LogDisplay.cleanLogBackup();
//            board = main.loadGame("save.boobs");
//            Set<PlayerToken> playersTokens =  board.getPlayerTokensAndPlayers().keySet();
//            HashMap<PlayerToken, Player> playerTokensAndPlayers = board.getPlayerTokensAndPlayers();
//            for(PlayerToken pt : playersTokens){
//                switch(pt.getColor()){
//                    case "#40A497":
//                        assertEquals(playerTokensAndPlayers.get(pt).getName(),"maxime");
//                        break;
//                    case "#111111":
//                        assertEquals(playerTokensAndPlayers.get(pt).getName(),"anne la plus belle <3");
//                        break;
//                    case "#502222":
//                        assertEquals(playerTokensAndPlayers.get(pt).getName(),"gael la pelle");
//                        break;
//                    case "#40A100":
//                        assertEquals(playerTokensAndPlayers.get(pt).getName(),"rouchard coeur de lion, richmont la raclette");
//                        break;
//                }
//            }
//            
//            assertEquals(board.getExpoCards().get(0).toString(), "expo,paris,3,4");
//            assertEquals(board.getExpoCards().get(1).toString(), "expo,vienna,4,5");
//            assertEquals(board.getExpoCards().get(2).toString(), "expo,rome,3,4");
//            assertEquals(board.getDeck().get(0).toString(),"expo,moscow,4,5");
//            assertEquals(board.getDeck().get(1).toString(),"expo,warsaw,4,5");
//            assertEquals(board.getDeck().get(2).toString(),"generalKnowledge,berlin,2,3");
//            assertEquals(board.getDeck().get(3).toString(),"shovel,london,2");
//            assertEquals(board.getDeck().get(4).toString(),"ethnologicalKnowledge,berlin,2,2,greece");
//            assertEquals(board.getDeck().get(5).toString(),"ethnologicalKnowledge,rome,2,2,egypt");
//            assertEquals(board.getDeck().get(6).toString(),"specificKnowledge,rome,2,2,crete");
//            
//            assertEquals(board.getFourCurrentCards()[0].toString(),"generalKnowledge,berlin,2,2");
//            assertEquals(board.getFourCurrentCards()[1].toString(),"generalKnowledge,paris,2,2");	
//            assertEquals(board.getFourCurrentCards()[2].toString(),"generalKnowledge,rome,2,2");	
//            assertEquals(board.getFourCurrentCards()[3].toString(),"generalKnowledge,vienna,2,2");
//            assertEquals(LogDisplay.getLogBackup().split("]")[1]," maxime se déplace sur paris.");
        }
}
