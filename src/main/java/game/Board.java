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

package game;

import tokens.Token;
import cards.Card;
import cards.ExpoCard;
import cards.GameCard;
import cards.KnowledgeCard;
import areas.Area;
import areas.ExcavationArea;
import areas.TouristicArea;
import java.util.HashMap;

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
    private Area areas[];

    /**
     * List of player with their game token
     */
    private HashMap<Player, PlayerToken> players = new HashMap<Player, PlayerToken>();

    private PlayerToken currentPlayerToken;

    /**
     * Distances between each areas
     */
    private static int distances[][] = new int[][]{
        {0,1,1,2,2,2,3,3,3,4,4,4},	// londres -> 0
        {1,0,1,1,1,2,3,2,2,3,3,3},	// paris -> 1
        {1,1,0,2,2,1,2,2,3,3,4,4},	// berlin -> 2
        {2,1,2,0,1,2,3,1,1,2,2,2},	// rome -> 3
        {2,1,1,2,0,1,2,2,2,3,3,3},  // vienne -> 4
        {2,2,2,1,1,0,1,1,2,3,3,2},	// varsovie -> 5
        {3,3,3,2,2,1,0,2,3,4,4,3},	// moscou -> 6
        {3,2,1,2,2,1,2,0,1,2,2,1},	// grece -> 7
        {3,2,1,3,2,2,3,1,0,1,1,2},	// crete -> 8
        {4,3,2,4,3,3,4,2,1,0,1,2},	// egypte -> 9
        {4,3,4,4,3,3,4,2,1,1,0,1},	// palestine -> 10	
        {4,3,2,3,3,2,3,1,2,2,1,0},	// mesopotamie -> 11
    };

    private Deck deck;
    private Deck sideDeck;

    private Card fourCurrentCards[];


    public Board(int nbPlayers){
        this.nbPlayers = nbPlayers;
        this.piecesStack = new PlayerTokenStack();
        this.areas = new Area[12];
        this.chrono = new Chrono();
        this.chrono.initializationValues();
        this.deck = new Deck();
        this.sideDeck = new Deck();
        this.fourCurrentCards = new Card[4];

    }
	
    /**
     * Print the content of the deck
     * @param deck Deck to print
     * @return the content of the deck
     */
    public String printDeck(Deck deck){
        return deck.toString();
    }


    /**
     * Initialization of areas
     */
    public void initAreas(){

            TouristicArea londres = new TouristicArea(0,"Londres");
            TouristicArea paris = new TouristicArea(1,"Paris");
            TouristicArea berlin = new TouristicArea(2,"Berlin");
            TouristicArea rome = new TouristicArea(3,"Rome");
            TouristicArea vienne = new TouristicArea(4,"Vienne");
            TouristicArea varsovie = new TouristicArea(5,"Varsovie");
            TouristicArea moscou = new TouristicArea(6,"Moscou");

            ExcavationArea grece = new ExcavationArea(7,"Grece","Orange");
            ExcavationArea crete = new ExcavationArea(8,"Crete","Violet");
            ExcavationArea egypte = new ExcavationArea(9,"Egypte","Jaune");
            ExcavationArea palestine = new ExcavationArea(10,"Palestine","Vert");
            ExcavationArea mesopotamie = new ExcavationArea(11,"Mesopotamie","Bleu");


            /*
             * Empty tokens
             */
            for(int i = 0; i < 16; i++){

                    grece.addToken(new Token("Empty", "Orange", 0));
                    crete.addToken(new Token("Empty", "Purple", 0));
                    egypte.addToken(new Token("Empty", "Yellow", 0));
                    palestine.addToken(new Token("Empty", "Green", 0));
                    mesopotamie.addToken(new Token("Empty", "Blue", 0));
            }

            /*
             * Tokens Grece
             */
            grece.addToken(new Token("Points", "Orange", 1));
            grece.addToken(new Token("Points", "Orange", 1));
            grece.addToken(new Token("Points", "Orange", 1));
            grece.addToken(new Token("Points", "Orange", 1));
            grece.addToken(new Token("Points", "Orange", 2));
            grece.addToken(new Token("Points", "Orange", 2));
            grece.addToken(new Token("Points", "Orange", 2));
            grece.addToken(new Token("Points", "Orange", 3));
            grece.addToken(new Token("Points", "Orange", 3));
            grece.addToken(new Token("Points", "Orange", 4));
            grece.addToken(new Token("Points", "Orange", 5));
            grece.addToken(new Token("Points", "Orange", 5));
            grece.addToken(new Token("Points", "Orange", 6));
            grece.addToken(new Token("GeneralKnowledge", "All", 1));
            grece.addToken(new Token("SpecificKnowledge", "Purple", 1));


            /*
             * Tokens Crete
             */
            crete.addToken(new Token("Points", "Purple", 1));
            crete.addToken(new Token("Points", "Purple", 1));
            crete.addToken(new Token("Points", "Purple", 1));
            crete.addToken(new Token("Points", "Purple", 2));
            crete.addToken(new Token("Points", "Purple", 2));
            crete.addToken(new Token("Points", "Purple", 3));
            crete.addToken(new Token("Points", "Purple", 3));
            crete.addToken(new Token("Points", "Purple", 3));
            crete.addToken(new Token("Points", "Purple", 3));
            crete.addToken(new Token("Points", "Purple", 4));
            crete.addToken(new Token("Points", "Purple", 4));
            crete.addToken(new Token("Points", "Purple", 4));
            crete.addToken(new Token("Points", "Purple", 5));
            crete.addToken(new Token("GeneralKnowledge", "All", 1));
            crete.addToken(new Token("SpecificKnowledge", "Orange", 1));


            /*
             * Tokens Egypte
             */
            egypte.addToken(new Token("Points", "Yellow", 1));
            egypte.addToken(new Token("Points", "Yellow", 1));
            egypte.addToken(new Token("Points", "Yellow", 1));
            egypte.addToken(new Token("Points", "Yellow", 1));
            egypte.addToken(new Token("Points", "Yellow", 2));
            egypte.addToken(new Token("Points", "Yellow", 2));
            egypte.addToken(new Token("Points", "Yellow", 3));
            egypte.addToken(new Token("Points", "Yellow", 3));
            egypte.addToken(new Token("Points", "Yellow", 3));
            egypte.addToken(new Token("Points", "Yellow", 4));
            egypte.addToken(new Token("Points", "Yellow", 4));
            egypte.addToken(new Token("Points", "Yellow", 5));
            egypte.addToken(new Token("Points", "Yellow", 6));
            egypte.addToken(new Token("GeneralKnowledge", "All", 1));
            egypte.addToken(new Token("SpecificKnowledge", "Blue", 1));


            /*
             * Tokens Palestine
             */
            palestine.addToken(new Token("Points", "Green", 1));
            palestine.addToken(new Token("Points", "Green", 1));
            palestine.addToken(new Token("Points", "Green", 1));
            palestine.addToken(new Token("Points", "Green", 1));
            palestine.addToken(new Token("Points", "Green", 1));
            palestine.addToken(new Token("Points", "Green", 2));
            palestine.addToken(new Token("Points", "Green", 2));
            palestine.addToken(new Token("Points", "Green", 2));
            palestine.addToken(new Token("Points", "Green", 3));
            palestine.addToken(new Token("Points", "Green", 4));
            palestine.addToken(new Token("Points", "Green", 5));
            palestine.addToken(new Token("Points", "Green", 6));
            palestine.addToken(new Token("Points", "Green", 7));
            palestine.addToken(new Token("GeneralKnowledge", "All", 1));
            palestine.addToken(new Token("SpecificKnowledge", "Yellow", 1));


            /*
             * Tokens Mesopotamie
             */
            mesopotamie.addToken(new Token("Points", "Blue", 1));
            mesopotamie.addToken(new Token("Points", "Blue", 1));
            mesopotamie.addToken(new Token("Points", "Blue", 1));
            mesopotamie.addToken(new Token("Points", "Blue", 1));
            mesopotamie.addToken(new Token("Points", "Blue", 1));
            mesopotamie.addToken(new Token("Points", "Blue", 3));
            mesopotamie.addToken(new Token("Points", "Blue", 3));
            mesopotamie.addToken(new Token("Points", "Blue", 3));
            mesopotamie.addToken(new Token("Points", "Blue", 4));
            mesopotamie.addToken(new Token("Points", "Blue", 4));
            mesopotamie.addToken(new Token("Points", "Blue", 4));
            mesopotamie.addToken(new Token("Points", "Blue", 5));
            mesopotamie.addToken(new Token("Points", "Blue", 5));
            mesopotamie.addToken(new Token("GeneralKnowledge", "All", 1));
            mesopotamie.addToken(new Token("SpecificKnowledge", "Green", 1));






            areas[0] = londres; areas[1] = paris; areas[2] = berlin; areas[3] = rome; areas[4] = vienne; areas[5] = varsovie;
            areas[6] = moscou; areas[7] = grece; areas[8] = crete; areas[9] = egypte; areas[10] = palestine; areas[11] = mesopotamie;

    }
	
    /**
     * Return the distance between 2 areas
     * @param area1 first area
     * @param area2 second area
     * @return the distance between area1 & area2
     */
    public static int distance(Area area1, Area area2){
        return(Board.distances[area1.getNum()][area2.getNum()]);
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
        firstDeck.addCard(new GameCard("ExcavationAuthorization", "Londres", 3));
        firstDeck.addCard(new GameCard("ExcavationAuthorization", "Moscou", 3));

        // Zeppelin
        firstDeck.addCard(new GameCard("Zeppelin", "Londres", 1));
        firstDeck.addCard(new GameCard("Zeppelin", "Londres", 1));

        // Car
        firstDeck.addCard(new GameCard("Car", "Moscou", 1));
        firstDeck.addCard(new GameCard("Car", "Rome", 1));

        // Congress
        firstDeck.addCard(new GameCard("Congress", "London", 2));
        firstDeck.addCard(new GameCard("Congress", "Paris", 2));
        firstDeck.addCard(new GameCard("Congress", "Paris", 2));
        firstDeck.addCard(new GameCard("Congress", "Berlin", 2));
        firstDeck.addCard(new GameCard("Congress", "Berlin", 2));
        firstDeck.addCard(new GameCard("Congress", "Vienne", 2));
        firstDeck.addCard(new GameCard("Congress", "Vienne", 2));
        firstDeck.addCard(new GameCard("Congress", "Moscou", 2));
        firstDeck.addCard(new GameCard("Congress", "Moscou", 2));

        //Assistant
        firstDeck.addCard(new GameCard("Assistant", "Paris", 2));
        firstDeck.addCard(new GameCard("Assistant", "Paris", 2));
        firstDeck.addCard(new GameCard("Assistant", "Rome", 2));
        firstDeck.addCard(new GameCard("Assistant", "Berlin", 2));
        firstDeck.addCard(new GameCard("Assistant", "Vienne", 2));
        firstDeck.addCard(new GameCard("Assistant", "Vienne", 2));

        //Shovel
        firstDeck.addCard(new GameCard("Shovel", "Londres", 3));
        firstDeck.addCard(new GameCard("Shovel", "Londres", 3));
        firstDeck.addCard(new GameCard("Shovel", "Rome", 3));
        firstDeck.addCard(new GameCard("Shovel", "Rome", 3));
        firstDeck.addCard(new GameCard("Shovel", "Moscou", 3));
        firstDeck.addCard(new GameCard("Shovel", "Moscou", 3));

        /*
         * KnowledgeCards
         */

        // General Knowledge
        firstDeck.addCard(new KnowledgeCard("GeneralKnowledge", "Paris", 3, 1, "All"));
        firstDeck.addCard(new KnowledgeCard("GeneralKnowledge", "Rome", 3, 1, "All"));
        firstDeck.addCard(new KnowledgeCard("GeneralKnowledge", "Berlin", 3, 1, "All"));
        firstDeck.addCard(new KnowledgeCard("GeneralKnowledge", "Vienne", 3, 1, "All"));

        firstDeck.addCard(new KnowledgeCard("GeneralKnowledge", "Londre", 6, 2, "All"));
        firstDeck.addCard(new KnowledgeCard("GeneralKnowledge", "Paris", 6, 2, "All"));
        firstDeck.addCard(new KnowledgeCard("GeneralKnowledge", "Berlin", 6, 2, "All"));
        firstDeck.addCard(new KnowledgeCard("GeneralKnowledge", "Moscou", 6, 2, "All"));

        // Specific knowledge

        //Grece

        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Rome", 1, 1, "Orange"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Berlin", 1, 1, "Orange"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Vienne", 1, 1, "Orange"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Moscou", 1, 1, "Orange"));

        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Londres", 2, 2, "Orange"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Rome", 2, 2, "Orange"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Rome", 2, 2, "Orange"));

        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Londres", 4, 3, "Orange"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Berlin", 4, 4, "Orange"));


        //Crete

        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Paris", 1, 1, "Purple"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Berlin", 1, 1, "Purple"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Berlin", 1, 1, "Purple"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Vienne", 1, 1, "Purple"));

        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Paris", 2, 2, "Purple"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Rome", 2, 2, "Purple"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Vienne", 2, 2, "Purple"));

        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Moscou", 4, 3, "Purple"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Moscou", 4, 4, "Purple"));	



        //Egypte

        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Paris", 1, 1, "Yellow"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Paris", 1, 1, "Yellow"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Rome", 1, 1, "Yellow"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Moscou", 1, 1, "Yellow"));

        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Londres", 2, 2, "Yellow"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Berlin", 2, 2, "Yellow"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Berlin", 2, 2, "Yellow"));

        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Londres", 4, 3, "Yellow"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Moscou", 4, 4, "Yellow"));



        //Palestine

        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Rome", 1, 1, "Green"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Vienne", 1, 1, "Green"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Vienne", 1, 1, "Green"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Vienne", 1, 1, "Green"));

        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Londres", 2, 2, "Green"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Paris", 2, 2, "Green"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Berlin", 2, 2, "Green"));

        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Londres", 4, 3, "Green"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Paris", 4, 4, "Green"));


        //Mesopotamie

        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Paris", 1, 1, "Blue"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Rome", 1, 1, "Blue"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Moscou", 1, 1, "Blue"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Moscou", 1, 1, "Blue"));

        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Londres", 2, 2, "Blue"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Vienne", 2, 2, "Blue"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Vienne", 2, 2, "Blue"));

        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Londres", 4, 3, "Blue"));
        firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", "Moscou", 4, 4, "Blue"));


        //Ethnological knowledge
        firstDeck.addCard(new KnowledgeCard("EthnologicalKnowledge", "Moscou", 1, 2, "Orange"));
        firstDeck.addCard(new KnowledgeCard("EthnologicalKnowledge", "Paris", 1, 2, "Purple"));
        firstDeck.addCard(new KnowledgeCard("EthnologicalKnowledge", "Rome", 1, 2, "Yellow"));
        firstDeck.addCard(new KnowledgeCard("EthnologicalKnowledge", "Vienne", 1, 2, "Green"));
        firstDeck.addCard(new KnowledgeCard("EthnologicalKnowledge", "Berlin", 1, 2, "Blue"));


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

                deck2.add(new ExpoCard("LittleExpo", "London", 3, false));
                deck2.add(new ExpoCard("LittleExpo", "Paris", 3, false));
                deck2.add(new ExpoCard("LittleExpo", "Berlin", 3, false));
                deck2.add(new ExpoCard("LittleExpo", "Vienne", 3, false));
                deck2.add(new ExpoCard("LittleExpo", "Moscou", 3, false));

                this.sideDeck.add(new ExpoCard("BigExpo", "London", 4, false));
                this.sideDeck.add(new ExpoCard("BigExpo", "Paris", 4, false));
                this.sideDeck.add(new ExpoCard("BigExpo", "Berlin", 4, false));
                this.sideDeck.add(new ExpoCard("BigExpo", "Vienne", 4, false));
                this.sideDeck.add(new ExpoCard("BigExpo", "Moscou", 4, false));

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

                deck2.add(new ExpoCard("LittleExpo", "London", 3, false));
                deck2.add(new ExpoCard("LittleExpo", "Paris", 3, false));
                deck2.add(new ExpoCard("LittleExpo", "Berlin", 3, false));
                deck2.add(new ExpoCard("LittleExpo", "Vienne", 3, false));
                deck2.add(new ExpoCard("LittleExpo", "Moscou", 3, false));

                deck2.add(new ExpoCard("BigExpo", "London", 4, false));
                deck2.add(new ExpoCard("BigExpo", "Paris", 4, false));
                deck2.add(new ExpoCard("BigExpo", "Berlin", 4, false));
                deck2.add(new ExpoCard("BigExpo", "Vienne", 4, false));
                deck2.add(new ExpoCard("BigExpo", "Moscou", 4, false));

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
    
    public PlayerToken getCurrentPlayerToken() {
        return currentPlayerToken;
    }

    public void setCurrentPlayerToken(PlayerToken currentPlayerToken) {
        this.currentPlayerToken = currentPlayerToken;
    }


    
	
}
