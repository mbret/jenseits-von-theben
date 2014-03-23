/*
 * Copyright (C) 2014 maxime
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.miage.game;

import com.miage.tokens.*;
import com.miage.cards.*;
import com.miage.areas.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author maxime
 */
public class Board {
	
    private int nbPlayers;

    private Chrono chrono;
    
    /**
     * Player token stack, define the player's time to play
     */ 
    private PlayerTokenStack piecesStack;

    /**
     *  
     */
    private HashMap<String, Area> areas;

    /**
     * List of player with their game token
     */
    private HashMap<Player, PlayerToken> players;

    private PlayerToken currentPlayerToken;

    /**
     * Distances between each areas
     */
    private static int distances[][] = new int[][]{
        {0,1,1,2,2,2,3,3,3,4,4,4},	// london -> 0
        {1,0,1,1,1,2,3,2,2,3,3,3},	// paris -> 1
        {1,1,0,2,2,1,2,2,3,3,4,4},	// berlin -> 2
        {2,1,2,0,1,2,3,1,1,2,2,2},	// rome -> 3
        {2,1,1,2,0,1,2,2,2,3,3,3},      // vienna -> 4
        {2,2,2,1,1,0,1,1,2,3,3,2},	// warsaw -> 5
        {3,3,3,2,2,1,0,2,3,4,4,3},	// moscow -> 6
        {3,2,1,2,2,1,2,0,1,2,2,1},	// greece -> 7
        {3,2,1,3,2,2,3,1,0,1,1,2},	// crete -> 8
        {4,3,2,4,3,3,4,2,1,0,1,2},	// egypt -> 9
        {4,3,4,4,3,3,4,2,1,1,0,1},	// palestine -> 10	
        {4,3,2,3,3,2,3,1,2,2,1,0},	// mesopotamia -> 11
    };

    private Deck deck;
    private Deck sideDeck;

    private Card fourCurrentCards[];

    /**
     * HashMap used to determine how many token of each points are present in each excavation point
     * Init from extern configuration
     */
    private HashMap<String, HashMap<Integer, Integer>> areasTokensConfiguration;

    
    public Board(int nbPlayers){
        
        this.nbPlayers = nbPlayers;
        this.piecesStack = new PlayerTokenStack();
        this.areas = new HashMap<String, Area>();
        this.chrono = new Chrono();
        this.chrono.initializationValues();
        this.deck = new Deck();
        this.sideDeck = new Deck();
        this.fourCurrentCards = new Card[4];
        this.players = new HashMap<Player, PlayerToken>();
        
        this.initFromConfig();
        
        this.initAreas();
        
        this.initializationDecks();
    }
    
    
    /**
     * @author Gael
     * Returns the card picked
     * @param index of the card picked in the table
     * @return card picked
     */
    public Card pickCardOnBoard(int index){
    	
    	Card card = this.fourCurrentCards[index];
    	this.fourCurrentCards[index] = this.deck.pick();
    	
    	return card;
    }
    
    
    
    
    
    /**
     * Init some resources using extern configuration
     */
    public void initFromConfig(){
        
        // to put inside properties
        this.areasTokensConfiguration = new HashMap<String, HashMap<Integer, Integer>>(){{
            put("greece", new HashMap<Integer, Integer>(){{
                put(1, 4);
                put(2, 3);
                put(3, 2);
                put(4, 1);
                put(5, 2);
                put(6, 1);
            }});
            put("crete", new HashMap<Integer, Integer>(){{
                put(1, 3);
                put(2, 2);
                put(3, 4);
                put(4, 3);
                put(5, 1);
                put(6, 0);
            }});
            put("egypt", new HashMap<Integer, Integer>(){{
                put(1, 4);
                put(2, 2);
                put(3, 3);
                put(4, 2);
                put(5, 1);
                put(6, 1);
            }});
            put("palestine", new HashMap<Integer, Integer>(){{
                put(1, 5);
                put(2, 3);
                put(3, 1);
                put(4, 1);
                put(5, 1);
                put(6, 1);
                put(7, 1);
            }});
            put("mesopotamia", new HashMap<Integer, Integer>(){{
                put(1, 5);
                put(3, 3);
                put(4, 3);
                put(5, 2);
                put(6, 1);
                put(7, 1);
            }});
        }};
    }

    /**
     * Initialization of areas
     */
    public void initAreas(){

        Integer nbEmptyTokenPoint = 16; // put inside properties

        areas.put( "london", new TouristicArea(0,"london"));
        areas.put( "paris", new TouristicArea(1,"paris"));
        areas.put( "berlin", new TouristicArea(2,"berlin"));
        areas.put( "roma", new TouristicArea(3,"roma"));
        areas.put( "vienna", new TouristicArea(4,"vienna"));
        areas.put( "warsaw", new TouristicArea(5,"warsaw"));
        areas.put( "moscow", new TouristicArea(6,"moscow"));
        areas.put( "greece", new ExcavationArea(7,"greece","#ff5b2b")); // orange
        areas.put( "crete", new ExcavationArea(8,"crete","#895959")); // purple
        areas.put( "egypt", new ExcavationArea(9,"egypt","#fff168")); // yello
        areas.put( "palestine", new ExcavationArea(10,"palestine","#b7ca79")); // green
        areas.put( "mesopotamia", new ExcavationArea(11,"mesopotamia","#375d81")); // blue

        // Init tokens
        for (Area area : areas.values()) {
            if(area instanceof ExcavationArea){
                ExcavationArea areaTmp = ((ExcavationArea)area);

                 // Set empty tokens
                for (int i = 0; i < nbEmptyTokenPoint; i++) {
                    areaTmp.addToken( new PointToken("empty", areaTmp.getCodeColor(), 0)); // assign empty point
                }

                // Set knowledge (one to each excavation area)
                areaTmp.addToken( new GeneralKnowledgeToken("GeneralKnowledge", areaTmp.getCodeColor() ) );
                areaTmp.addToken( new SpecificKnowledgeToken("SpecificKnowledge", areaTmp.getCodeColor() ) );

                // Set point tokens
                HashMap<Integer, Integer> pointsToAssign = this.areasTokensConfiguration.get( areaTmp.getName() ); // get points to assign according to this city
                for (Map.Entry<Integer, Integer> pointValue : pointsToAssign.entrySet()) { // loop on each point value
                    Integer value = pointValue.getKey();
                    Integer nbTokenOfThisValue = pointValue.getValue();
                    for (int i = 0; i < nbTokenOfThisValue; i++) {
                        areaTmp.addToken( new PointToken("point", areaTmp.getCodeColor(), value ) ); // assign one token of this value
                    }
                }
            }
        }
    }
    
	
 
	
	
    /**
     * Initialization of decks depending on the number of players
     */
    public void initializationDecks(){


        Deck firstDeck = new Deck();


        /*
         * Creation of cards
         */


        /*
         * GameCard
         */

        // Excavation authorization
        firstDeck.addCard(new ExcavationAuthorizationCard("london", 3));
        firstDeck.addCard(new ExcavationAuthorizationCard("moscow", 3));

        // Zeppelin
        firstDeck.addCard(new ZeppelinCard("london", 1));
        firstDeck.addCard(new ZeppelinCard("london", 1));

        // Car
        firstDeck.addCard(new CarCard("moscow", 1));
        firstDeck.addCard(new CarCard("roma", 1));

        // Congress
        firstDeck.addCard(new CongressCard("london", 2));
        firstDeck.addCard(new CongressCard("paris", 2));
        firstDeck.addCard(new CongressCard("paris", 2));
        firstDeck.addCard(new CongressCard("berlin", 2));
        firstDeck.addCard(new CongressCard("berlin", 2));
        firstDeck.addCard(new CongressCard("vienna", 2));
        firstDeck.addCard(new CongressCard("vienna", 2));
        firstDeck.addCard(new CongressCard("moscow", 2));
        firstDeck.addCard(new CongressCard("moscow", 2));

        //Assistant
        firstDeck.addCard(new AssistantCard("paris", 2));
        firstDeck.addCard(new AssistantCard("paris", 2));
        firstDeck.addCard(new AssistantCard("rome", 2));
        firstDeck.addCard(new AssistantCard("berlin", 2));
        firstDeck.addCard(new AssistantCard("vienna", 2));
        firstDeck.addCard(new AssistantCard("vienna", 2));

        //Shovel
        firstDeck.addCard(new ShovelCard("london", 3));
        firstDeck.addCard(new ShovelCard("london", 3));
        firstDeck.addCard(new ShovelCard("rome", 3));
        firstDeck.addCard(new ShovelCard("rome", 3));
        firstDeck.addCard(new ShovelCard("moscow", 3));
        firstDeck.addCard(new ShovelCard("moscow", 3));

        /*
         * KnowledgeCards
         */

        // General Knowledge
        firstDeck.addCard( new GeneralKnowledgeCard("paris", 3, 1) );
        firstDeck.addCard( new GeneralKnowledgeCard("rome", 3, 1));
        firstDeck.addCard( new GeneralKnowledgeCard("berlin", 3, 1));
        firstDeck.addCard( new GeneralKnowledgeCard("vienna", 3, 1));
        firstDeck.addCard( new GeneralKnowledgeCard("Londre", 6, 2));
        firstDeck.addCard( new GeneralKnowledgeCard("paris", 6, 2));
        firstDeck.addCard( new GeneralKnowledgeCard("berlin", 6, 2));
        firstDeck.addCard( new GeneralKnowledgeCard("moscow", 6, 2));

        // SpecificKnowledgeCarde

        //greece

        firstDeck.addCard(new SpecificKnowledgeCard("rome", 1, 1, "Orange"));
        firstDeck.addCard(new SpecificKnowledgeCard("berlin", 1, 1, "Orange"));
        firstDeck.addCard(new SpecificKnowledgeCard("vienna", 1, 1, "Orange"));
        firstDeck.addCard(new SpecificKnowledgeCard("moscow", 1, 1, "Orange"));

        firstDeck.addCard(new SpecificKnowledgeCard("london", 2, 2, "Orange"));
        firstDeck.addCard(new SpecificKnowledgeCard("rome", 2, 2, "Orange"));
        firstDeck.addCard(new SpecificKnowledgeCard("rome", 2, 2, "Orange"));

        firstDeck.addCard(new SpecificKnowledgeCard("london", 4, 3, "Orange"));
        firstDeck.addCard(new SpecificKnowledgeCard("berlin", 4, 3, "Orange"));

        //crete
        
        firstDeck.addCard(new SpecificKnowledgeCard("berlin", 1, 1, "Purple"));
        firstDeck.addCard(new SpecificKnowledgeCard("berlin", 1, 1, "Purple"));
        firstDeck.addCard(new SpecificKnowledgeCard("paris", 1, 1, "Purple"));
        firstDeck.addCard(new SpecificKnowledgeCard("vienna", 1, 1, "Purple"));

        firstDeck.addCard(new SpecificKnowledgeCard("rome", 2, 2, "Purple"));
        firstDeck.addCard(new SpecificKnowledgeCard("paris", 2, 2, "Purple"));
        firstDeck.addCard(new SpecificKnowledgeCard("vienna", 2, 2, "Purple"));

        firstDeck.addCard(new SpecificKnowledgeCard("moscow", 4, 3, "Purple"));
        firstDeck.addCard(new SpecificKnowledgeCard("moscow", 4, 3, "Purple"));



        //egypt

        firstDeck.addCard(new SpecificKnowledgeCard("rome", 1, 1, "Yellow"));
        firstDeck.addCard(new SpecificKnowledgeCard("paris", 1, 1, "Yellow"));
        firstDeck.addCard(new SpecificKnowledgeCard("paris", 1, 1, "Yellow"));
        firstDeck.addCard(new SpecificKnowledgeCard("moscow", 1, 1, "Yellow"));

        firstDeck.addCard(new SpecificKnowledgeCard("berlin", 2, 2, "Yellow"));
        firstDeck.addCard(new SpecificKnowledgeCard("berlin", 2, 2, "Yellow"));
        firstDeck.addCard(new SpecificKnowledgeCard("london", 2, 2, "Yellow"));

        firstDeck.addCard(new SpecificKnowledgeCard("moscow", 4, 3, "Yellow"));
        firstDeck.addCard(new SpecificKnowledgeCard("london", 4, 3, "Yellow"));



        //palestine

        firstDeck.addCard(new SpecificKnowledgeCard("vienna", 1, 1, "Green"));
        firstDeck.addCard(new SpecificKnowledgeCard("vienna", 1, 1, "Green"));
        firstDeck.addCard(new SpecificKnowledgeCard("vienna", 1, 1, "Green"));
        firstDeck.addCard(new SpecificKnowledgeCard("rome", 1, 1, "Green"));

        firstDeck.addCard(new SpecificKnowledgeCard("paris", 2, 2, "Green"));
        firstDeck.addCard(new SpecificKnowledgeCard("berlin", 2, 2, "Green"));
        firstDeck.addCard(new SpecificKnowledgeCard("london", 2, 2, "Green"));

        firstDeck.addCard(new SpecificKnowledgeCard("paris", 4, 3, "Green"));
        firstDeck.addCard(new SpecificKnowledgeCard("london", 4, 3, "Green"));
        
        
        //mesopotamia

        firstDeck.addCard(new SpecificKnowledgeCard("paris", 1, 1, "Blue"));
        firstDeck.addCard(new SpecificKnowledgeCard("rome", 1, 1, "Blue"));
        firstDeck.addCard(new SpecificKnowledgeCard("moscow", 1, 1, "Blue"));
        firstDeck.addCard(new SpecificKnowledgeCard("moscow", 1, 1, "Blue"));

        firstDeck.addCard(new SpecificKnowledgeCard("vienna", 2, 2, "Blue"));
        firstDeck.addCard(new SpecificKnowledgeCard("vienna", 2, 2, "Blue"));
        firstDeck.addCard(new SpecificKnowledgeCard("london", 2, 2, "Blue"));

        firstDeck.addCard(new SpecificKnowledgeCard("moscow", 4, 3, "Blue"));
        firstDeck.addCard(new SpecificKnowledgeCard("london", 4, 3, "Blue"));


        //Ethnological knowledge
        firstDeck.addCard(new EthnologicalKnowledgeCard("moscow", 1, 2, "Orange"));
        firstDeck.addCard(new EthnologicalKnowledgeCard("paris", 1, 2, "Purple"));
        firstDeck.addCard(new EthnologicalKnowledgeCard("rome", 1, 2, "Yellow"));
        firstDeck.addCard(new EthnologicalKnowledgeCard("vienna", 1, 2, "Green"));
        firstDeck.addCard(new EthnologicalKnowledgeCard("berlin", 1, 2, "Blue"));


        /*
         * Mix of the first deck and initialization of the four cards on the board
         */

        firstDeck.mix();
        this.fourCurrentCards[0] = firstDeck.pick();
        this.fourCurrentCards[1] = firstDeck.pick();
        this.fourCurrentCards[2] = firstDeck.pick();
        this.fourCurrentCards[3] = firstDeck.pick();

        Deck deck1 = new Deck();
        Deck deck2 = new Deck();

        if(this.nbPlayers > 2){

                /*
                 * 3 decks, same size, insert little expo in the 2nd deck, mix the 2nd deck, union with first deck
                 * Big Expo in the 3rd deck
                 */

                deck1 = (Deck) firstDeck.divideDeck(0, (firstDeck.size()/3));
                deck2 = (Deck) firstDeck.divideDeck((firstDeck.size()/3), ((firstDeck.size()/3)+(firstDeck.size()/3)));

                this.sideDeck = (Deck) firstDeck.divideDeck(((firstDeck.size()/3)+(firstDeck.size()/3)), firstDeck.size()-1);

                deck2.add(new ExpoCard("london", 3, false));
                deck2.add(new ExpoCard("paris", 3, false));
                deck2.add(new ExpoCard("berlin", 3, false));
                deck2.add(new ExpoCard("vienna", 3, false));
                deck2.add(new ExpoCard("moscow", 3, false));

                this.sideDeck.add(new ExpoCard("london", 4, true));
                this.sideDeck.add(new ExpoCard("paris", 4, true));
                this.sideDeck.add(new ExpoCard("berlin", 4, true));
                this.sideDeck.add(new ExpoCard("vienna", 4, true));
                this.sideDeck.add(new ExpoCard("moscow", 4, true));

                deck2.mix();
                this.deck.addAll(deck2);
                this.deck.addAll(deck1);


        }
        else{

                /*
                 * 2 decks, same size, insert little expo & big expo in the 2nd deck, mix the 2nd deck, union with first deck
                 */
                deck1 = (Deck) firstDeck.divideDeck(0, (firstDeck.size()/3));
                deck2 = (Deck) firstDeck.divideDeck((firstDeck.size()/3), ((firstDeck.size()/3)+(firstDeck.size()/3)));

                this.sideDeck = (Deck) firstDeck.divideDeck(((firstDeck.size()/3)+(firstDeck.size()/3)), firstDeck.size()-1);

                deck2.add(new ExpoCard("london", 3, false));
                deck2.add(new ExpoCard("paris", 3, false));
                deck2.add(new ExpoCard("berlin", 3, false));
                deck2.add(new ExpoCard("vienna", 3, false));
                deck2.add(new ExpoCard("moscow", 3, false));

                deck2.add(new ExpoCard("london", 4, true));
                deck2.add(new ExpoCard("paris", 4, true));
                deck2.add(new ExpoCard("berlin", 4, true));
                deck2.add(new ExpoCard("vienna", 4, true));
                deck2.add(new ExpoCard("moscow", 4, true));

                deck2.mix();
                this.deck.addAll(deck2);
                this.deck.addAll(deck1);

                this.sideDeck = new Deck();

        }

    }

    
    /***********************************************************************************************
     *
     *                                  Getter & Setter
     * 
     *  
    ************************************************************************************************/
    
    /**
     * 
     * @return 
     */
    public PlayerToken getCurrentPlayerToken() {
        return currentPlayerToken;
    }

    /**
     * 
     * @param currentPlayerToken 
     */
    public void setCurrentPlayerToken(PlayerToken currentPlayerToken) {
        this.currentPlayerToken = currentPlayerToken;
    }


    /**
     * 
     * @return 
     */
    public Deck getDeck() {
        return deck;
    }
    
    /**
     * 
     * @param deck
     */
    public void setDeck(Deck deck){
    	this.deck = deck;
    }

    /**
     * 
     * @return 
     */
    public HashMap<String, Area> getAreas() {
        return areas;
    }

    /**
     * 
     * @return
     */
	public Card[] getFourCurrentCards() {
		return fourCurrentCards;
	}

	/**
	 * 
	 * @param fourCurrentCards
	 */
	public void setFourCurrentCards(Card[] fourCurrentCards) {
		this.fourCurrentCards = fourCurrentCards;
	}
	
	
	
    
    

    
    
    
	
}
