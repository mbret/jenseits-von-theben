/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.game;

import com.miage.areas.ExcavationArea;
import com.miage.areas.TouristicArea;
import com.miage.cards.CarCard;
import com.miage.cards.Card;
import com.miage.cards.ExpoCard;
import com.miage.cards.GeneralKnowledgeCard;
import com.miage.cards.ShovelCard;
import com.miage.cards.SpecificKnowledgeCard;
import com.miage.tokens.GeneralKnowledgeToken;
import com.miage.tokens.PointToken;
import com.miage.tokens.SpecificKnowledgeToken;
import com.miage.tokens.Token;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
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
public class TestLogDisplay {
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
	
	}

	@After
	public void tearDown() throws Exception {
	}

	
	/**
	 * @author David
	 * Test of the method displayAction
	 * 
	 */
	@Test
	public void testDisplayAction() throws IOException {
            
            Board board = new Board(3);
            Card[] fourCards = new Card[4];
		
            fourCards[0] = new GeneralKnowledgeCard(0,"generalKnowledge", "berlin", 2, 2);		
            fourCards[1] = new GeneralKnowledgeCard(0,"generalKnowledge", "paris", 2, 2);		
            fourCards[2] = new GeneralKnowledgeCard(0,"generalKnowledge", "rome", 2, 2);		
            fourCards[3] = new GeneralKnowledgeCard(0,"generalKnowledge", "vienna", 2, 2);		

            board.setFourCurrentCards(fourCards);

            Deck deckTest = new Deck();
            deckTest.addCard(new ExpoCard(0, "expo", "moscow", 4, true, 5));
            deckTest.addCard(new ExpoCard(0,"expo", "warsaw", 4, true, 5));
            deckTest.addCard(new ShovelCard(0,"shovel", "london", 2));
            board.setDeck(deckTest);
            
            Player p = new Player("player");
            
            PlayerToken pt = new PlayerToken("red");
            
            pt.setPosition(board.getArea("warsaw"));

            p.updateCompetencesPointsOrKnowledge(new CarCard(0,"car", "berlin",1), 1);

            HashMap<PlayerToken, Player> playerTokensAndPlayers = new HashMap<PlayerToken, Player>();
            playerTokensAndPlayers.put(pt, p);
            board.setPlayerTokensAndPlayers(playerTokensAndPlayers);
            board.setCurrentPlayerToken(pt);
            
            TouristicArea ta = new TouristicArea(1,"paris");
            
            LinkedList<Token> lt = new LinkedList<Token>();
            GeneralKnowledgeToken gkt = new GeneralKnowledgeToken("1","generalKnowledgeToken","red");
            SpecificKnowledgeToken skt = new SpecificKnowledgeToken("1","specificKnowledgeToken","red");
            PointToken ptoken = new PointToken("1","pointToken","red",1);
            lt.add(gkt);
            lt.add(skt);
            lt.add(ptoken);
            ExcavationArea exca = new ExcavationArea(5,"greece","red",lt);
            
         
            Card c1 = new GeneralKnowledgeCard(0, "generalKnowledge", "berlin", 2, 2);
            Card c2 = new SpecificKnowledgeCard(0, "generalKnowledge", "paris", 2, 2, "greece");
            Card c3 = new ShovelCard(1,"pelle","paris",3);
            
            
            assertEquals(LogDisplay.displayAction(board,"move",new TouristicArea(1,"paris")), new SimpleDateFormat("[HH:mm:ss]").format(new Date())+" player se déplace sur paris.");
            assertEquals(LogDisplay.displayAction(board,"pickCard",c2) , new SimpleDateFormat("[HH:mm:ss]").format(new Date())+" player pioche la carte generalKnowledge.");
            assertEquals(LogDisplay.displayAction(board,"move",new TouristicArea(2,"berlin")) , new SimpleDateFormat("[HH:mm:ss]").format(new Date())+" player se déplace sur berlin.");
            assertEquals(LogDisplay.displayAction(board,"pickCard",c1) , new SimpleDateFormat("[HH:mm:ss]").format(new Date())+" player pioche la carte generalKnowledge.");
            assertEquals(LogDisplay.displayAction(board,"pickCard",c3) , new SimpleDateFormat("[HH:mm:ss]").format(new Date())+" player pioche la carte pelle.");
            assertEquals(LogDisplay.displayAction(board,"use",c3) , new SimpleDateFormat("[HH:mm:ss]").format(new Date())+" player utilise la carte pelle.");
            assertEquals(LogDisplay.displayAction(board,"discard",c3) , new SimpleDateFormat("[HH:mm:ss]").format(new Date())+" player se défausse de sa carte pelle.");
            assertEquals(LogDisplay.displayAction(board,"excavate",exca) , new SimpleDateFormat("[HH:mm:ss]").format(new Date())+" player fouille en greece.");
            assertEquals(LogDisplay.displayAction(board,"pickToken",skt) , new SimpleDateFormat("[HH:mm:ss]").format(new Date())+" player pioche un jeton specificKnowledgeToken.");
            assertEquals(LogDisplay.displayAction(board,"pickToken",gkt) , new SimpleDateFormat("[HH:mm:ss]").format(new Date())+" player pioche un jeton generalKnowledgeToken.");
            assertEquals(LogDisplay.displayAction(board,"pickToken",ptoken) , new SimpleDateFormat("[HH:mm:ss]").format(new Date())+" player pioche un jeton pointToken.");

         }
}
