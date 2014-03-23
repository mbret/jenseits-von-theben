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
        
        /*
         * Creation of the distance hashmap for each area 
         */
        
        HashMap<String, String[]> londonDistances = new HashMap<String, String[]>();
        HashMap<String, String[]> parisDistances = new HashMap<String, String[]>();
        HashMap<String, String[]> romaDistances = new HashMap<String, String[]>();
        HashMap<String, String[]> berlinDistances = new HashMap<String, String[]>();
        HashMap<String, String[]> viennaDistances = new HashMap<String, String[]>();
        HashMap<String, String[]> warsawDistances = new HashMap<String, String[]>();
        HashMap<String, String[]> moscowDistances = new HashMap<String, String[]>();
        HashMap<String, String[]> greeceDistances = new HashMap<String, String[]>();
        HashMap<String, String[]> creteDistances = new HashMap<String, String[]>();
        HashMap<String, String[]> egyptDistances = new HashMap<String, String[]>();
        HashMap<String, String[]> palestineDistances = new HashMap<String, String[]>();
        HashMap<String, String[]> mesopotamiaDistances = new HashMap<String, String[]>();
        
        
        /*
         * 
         *************************** INITIALIZATION OF AREAS DISTANCES ***************************** 
         * 
         */
        
        
        /*
         * LONDON
         */
        londonDistances.put("paris", new String[0]);
        String[] londonRoma = {"paris"};
        londonDistances.put("roma", londonRoma);
        londonDistances.put("berlin", new String[0]);
        String[] londonVienna = {"paris"};
        londonDistances.put("vienna", londonVienna);
        String[] londonWarsaw = {"berlin"};
        londonDistances.put("warsaw", londonWarsaw);
        String[] londonMoscow = {"berlin","warsaw"};
        londonDistances.put("moscow", londonMoscow);
        String[] londonGreece = {"paris","roma"};
        londonDistances.put("greece", londonGreece);
        String[] londonCrete = {"paris","roma"};
        londonDistances.put("crete",londonCrete);
        String[] londonEgypt = {"paris","roma","crete"};
        londonDistances.put("egypt",londonEgypt);
        String[] londonPalestine = {"paris","roma","crete"};
        londonDistances.put("palestine", londonPalestine);
        String[] londonMesopotamia = {"paris","roma","greece"};
        londonDistances.put("mesopotamia", londonMesopotamia);
        
        
        /*
         * PARIS
         */
        parisDistances.put("london", new String[0]);
        parisDistances.put("roma", new String[0]);
        parisDistances.put("berlin", new String[0]);
        parisDistances.put("vienna",  new String[0]);
        String[] parisWarsaw = {"berlin"};
        parisDistances.put("warsaw", parisWarsaw);
        String[] parisMoscow = {"berlin","warsaw"};
        parisDistances.put("moscow", parisMoscow);
        String[] parisGreece = {"roma"};
        parisDistances.put("greece", parisGreece);
        String[] parisCrete = {"roma"};
        parisDistances.put("crete",parisCrete);
        String[] parisEgypt = {"roma","crete"};
        parisDistances.put("egypt",parisEgypt);
        String[] parisPalestine = {"roma","crete"};
        parisDistances.put("palestine", parisPalestine);
        String[] parisMesopotamia = {"roma","greece"};
        parisDistances.put("mesopotamia", parisMesopotamia);
        
        
        
        
        /*
         * ROMA
         */
        String[] romaLondon = {"paris"};
        romaDistances.put("london", romaLondon);
        romaDistances.put("paris", new String[0]);
        String[] romaBerlin = {"paris"};
        romaDistances.put("berlin", romaBerlin);
        romaDistances.put("vienna",  new String[0]);
        String[] romaWarsaw = {"vienna"};
        romaDistances.put("warsaw", romaWarsaw);
        String[] romaMoscow = {"vienna","warsaw"};
        romaDistances.put("moscow", romaMoscow);
        romaDistances.put("greece", new String[0]);
        romaDistances.put("crete",new String[0]);
        String[] romaEgypt = {"crete"};
        romaDistances.put("egypt",romaEgypt);
        String[] romaPalestine = {"crete"};
        romaDistances.put("palestine", romaPalestine);
        String[] romaMesopotamia = {"greece"};
        romaDistances.put("mesopotamia", romaMesopotamia);
        
        
        
        /*
         * BERLIN
         */
      
        berlinDistances.put("london", new String[0]);
        berlinDistances.put("paris", new String[0]);
        String[] berlinRoma = {"paris"};
        berlinDistances.put("roma", berlinRoma);
        String[] berlinVienna = {"warsaw"};
        berlinDistances.put("vienna",  berlinVienna);
        berlinDistances.put("warsaw", new String[0]);
        String[] berlinMoscow = {"warsaw"};
        berlinDistances.put("moscow", berlinMoscow);
        String[] berlinGreece = {"warsaw"};
        berlinDistances.put("greece", berlinGreece);
        String[] berlinCrete = {"warsaw","greece"};
        berlinDistances.put("crete", berlinCrete);
        String[] berlinEgypt = {"warsaw","crete"};
        berlinDistances.put("egypt",berlinEgypt);
        String[] berlinPalestine = {"warsaw","crete"};
        berlinDistances.put("palestine", berlinPalestine);
        String[] berlinMesopotamia = {"warsaw","greece"};
        berlinDistances.put("mesopotamia", berlinMesopotamia);
        
        
        /*
         * VIENNA
         */
      
        String[] viennaLondon = {"paris"};
        viennaDistances.put("london", viennaLondon);
        viennaDistances.put("paris", new String[0]);
        viennaDistances.put("roma", new String[0]);
        viennaDistances.put("berlin",  new String[0]);
        viennaDistances.put("warsaw", new String[0]);
        String[] viennaMoscow = {"warsaw"};
        viennaDistances.put("moscow", viennaMoscow);
        String[] viennaGreece = {"roma"};
        viennaDistances.put("greece", viennaGreece);
        String[] viennaCrete = {"roma"};
        viennaDistances.put("crete", viennaCrete);
        String[] viennaEgypt = {"roma","crete"};
        viennaDistances.put("egypt",viennaEgypt);
        String[] viennaPalestine = {"roma","crete"};
        viennaDistances.put("palestine", viennaPalestine);
        String[] viennaMesopotamia = {"roma","greece"};
        viennaDistances.put("mesopotamia", viennaMesopotamia);
        
        
        
        /*
         * WARSAW
         */
      
        String[] warsawLondon = {"berlin"};
        warsawDistances.put("london", warsawLondon);
        String[] warsawParis = {"berlin"};
        warsawDistances.put("paris", warsawParis);
        String[] warsawRoma = {"vienna"};
        warsawDistances.put("roma", warsawRoma);
        warsawDistances.put("vienna", new String[0]);
        warsawDistances.put("moscow", new String[0]);
        warsawDistances.put("greece", new String[0]);
        String[] warsawCrete = {"greece"};
        warsawDistances.put("crete", warsawCrete);
        String[] warsawEgypt = {"greece","crete"};
        warsawDistances.put("egypt",warsawEgypt);
        String[] warsawPalestine = {"greece","crete"};
        warsawDistances.put("palestine", warsawPalestine);
        String[] warsawMesopotamia = {"greece"};
        warsawDistances.put("mesopotamia", warsawMesopotamia);
        
        
        
        /*
         * MOSCOW
         */
      
        String[] moscowLondon = {"warsaw","berlin"};
        moscowDistances.put("london", moscowLondon);
        String[] moscowParis = {"warsaw","berlin"};
        moscowDistances.put("paris", moscowParis);
        String[] moscowRoma = {"warsaw","vienna"};
        moscowDistances.put("roma", moscowRoma);
        String[] moscowVienna = {"warsaw"};
        moscowDistances.put("vienna", moscowVienna);
        moscowDistances.put("warsaw", new String[0]);
        String[] moscowGreece = {"warsaw"};
        moscowDistances.put("greece", moscowGreece);
        String[] moscowCrete = {"warsaw","greece"};
        moscowDistances.put("crete", moscowCrete);
        String[] moscowEgypt = {"warsaw","greece","crete"};
        moscowDistances.put("egypt",moscowEgypt);
        String[] moscowPalestine = {"warsaw","greece","crete"};
        moscowDistances.put("palestine", moscowPalestine);
        String[] moscowMesopotamia = {"warsaw","greece"};
        moscowDistances.put("mesopotamia", moscowMesopotamia);
        
        
        
        
        /*
         * GREECE
         */
      
        String[] greeceLondon = {"warsaw","berlin"};
        greeceDistances.put("london", greeceLondon);
        String[] greeceParis = {"roma"};
        greeceDistances.put("paris", greeceParis);
        greeceDistances.put("roma", new String[0]);
        String[] greeceVienna = {"roma"};
        greeceDistances.put("vienna", greeceVienna);
        greeceDistances.put("warsaw", new String[0]);
        String[] greeceMoscow = {"warsaw"};
        greeceDistances.put("moscow", greeceMoscow);
        greeceDistances.put("crete", new String[0]);
        String[] greeceEgypt = {"crete"};
        greeceDistances.put("egypt",greeceEgypt);
        String[] greecePalestine = {"crete"};
        greeceDistances.put("palestine", greecePalestine);
        greeceDistances.put("mesopotamia", new String[0]);
        
        
        
        
        /*
         * CRETE
         */
      
        String[] creteLondon = {"roma","paris"};
        creteDistances.put("london", creteLondon);
        String[] creteParis = {"roma"};
        creteDistances.put("paris", creteParis);
        creteDistances.put("roma", new String[0]);
        String[] creteVienna = {"roma"};
        creteDistances.put("vienna", creteVienna);
        String[] creteWarsaw = {"greece"};
        creteDistances.put("warsaw", creteWarsaw);
        String[] creteMoscow = {"greece","warsaw"};
        creteDistances.put("moscow", creteMoscow);
        creteDistances.put("greece", new String[0]);
        creteDistances.put("egypt",new String[0]);  
        creteDistances.put("palestine", new String[0]);
        String[] creteMesopotamia = {"palestine"};
        creteDistances.put("mesopotamia", creteMesopotamia);
        
        
        /*
         * EGYPT
         */
      
        String[] egyptLondon = {"crete","roma","paris"};
        egyptDistances.put("london", egyptLondon);
        String[] egyptParis = {"crete","roma"};
        egyptDistances.put("paris", egyptParis);
        String[] egyptRoma = {"crete","roma"};
        egyptDistances.put("roma", egyptRoma);
        String[] egyptVienna = {"crete","roma"};
        egyptDistances.put("vienna", egyptVienna);
        String[] egyptWarsaw = {"crete","greece"};
        egyptDistances.put("warsaw", egyptWarsaw);
        String[] egyptMoscow = {"crete","greece","warsaw"};
        egyptDistances.put("moscow", egyptMoscow);
        String[] egyptGreece = {"crete"};
        egyptDistances.put("greece", egyptGreece);
        egyptDistances.put("crete",new String[0]);  
        egyptDistances.put("palestine", new String[0]);
        String[] egyptMesopotamia = {"palestine"};
        egyptDistances.put("mesopotamia", egyptMesopotamia);
        
        
        
        /*
         * PALESTINE
         */
      
        String[] palestineLondon = {"crete","roma","paris"};
        palestineDistances.put("london", palestineLondon);
        String[] palestineParis = {"crete","roma"};
        palestineDistances.put("paris", palestineParis);
        String[] palestineRoma = {"crete","roma"};
        palestineDistances.put("roma", palestineRoma);
        String[] palestineVienna = {"crete","roma"};
        palestineDistances.put("vienna", palestineVienna);
        String[] palestineWarsaw = {"crete","greece"};
        palestineDistances.put("warsaw", palestineWarsaw);
        String[] palestineMoscow = {"crete","greece","warsaw"};
        palestineDistances.put("moscow", palestineMoscow);
        String[] palestineGreece = {"crete"};
        palestineDistances.put("greece", palestineGreece);
        palestineDistances.put("crete",new String[0]);  
        palestineDistances.put("egypt", new String[0]);
        palestineDistances.put("mesopotamia", new String[0]);
        
        
        
        /*
         * MESOPOTAMIA
         */
      
        String[] mesopotamiaLondon = {"greece","roma","paris"};
        mesopotamiaDistances.put("london", mesopotamiaLondon);
        String[] mesopotamiaParis = {"greece","roma"};
        mesopotamiaDistances.put("paris", mesopotamiaParis);
        String[] mesopotamiaRoma = {"greece","roma"};
        mesopotamiaDistances.put("roma", mesopotamiaRoma);
        String[] mesopotamiaVienna = {"greece","roma"};
        mesopotamiaDistances.put("vienna", mesopotamiaVienna);
        String[] mesopotamiaWarsaw = {"greece"};
        mesopotamiaDistances.put("warsaw", mesopotamiaWarsaw);
        String[] mesopotamiaMoscow = {"greece","warsaw"};
        mesopotamiaDistances.put("moscow", mesopotamiaMoscow);
        mesopotamiaDistances.put("greece", new String[0]);
        String[] mesopotamiaCrete = {"palestine"};
        mesopotamiaDistances.put("crete",mesopotamiaCrete);  
        String[] mesopotamiaEgypt = {"palestine"};
        mesopotamiaDistances.put("egypt", mesopotamiaEgypt);
        mesopotamiaDistances.put("mesopotamia", new String[0]);
        
        
        
        
        TouristicArea london  = new TouristicArea(0,"london");
        TouristicArea paris  = new TouristicArea(1,"paris");
        TouristicArea roma  = new TouristicArea(2,"roma");
        TouristicArea berlin  = new TouristicArea(3,"berlin");
        TouristicArea vienna  = new TouristicArea(4,"vienna");
        TouristicArea warsaw  = new TouristicArea(5,"warsaw");
        TouristicArea moscow  = new TouristicArea(6,"moscow");
        ExcavationArea greece  = new ExcavationArea(7,"greece","#ff5b2b");
        ExcavationArea crete  = new ExcavationArea(8,"crete","#895959");
        ExcavationArea egypt  = new ExcavationArea(9,"egypt","#fff168");
        ExcavationArea palestine  = new ExcavationArea(10,"palestine","#b7ca79");
        ExcavationArea mesopotamia  = new ExcavationArea(11,"mesopotamia","#375d81");

        areas.put( "london", london);
        areas.put( "paris", paris);
        areas.put( "roma", roma);
        areas.put( "berlin", berlin);
        areas.put( "vienna", vienna);
        areas.put( "warsaw", warsaw);
        areas.put( "moscow", moscow);
        areas.put( "greece", greece); // orange
        areas.put( "crete", crete); // purple
        areas.put( "egypt", egypt); // yellow
        areas.put( "palestine", palestine); // green
        areas.put( "mesopotamia", mesopotamia); // blue

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
