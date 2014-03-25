
package com.miage.game;

import com.miage.areas.*;

import com.miage.cards.*;
import com.miage.config.ConfigManager;
import com.miage.tokens.*;
import java.io.IOException;
import java.util.ArrayList;
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
     * Stack of player's token
     * The stack allow to define which player should play before others
     */
    private PlayerTokenStack playerTokenStack;

    /**
     *  List of areas composing the board
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

    
    public Board(int nbPlayers) throws IOException{
        
        this.nbPlayers = nbPlayers;
        this.playerTokenStack = new PlayerTokenStack();
        this.areas = new HashMap<String, Area>();
        this.chrono = new Chrono();
        this.chrono.initializationValues();
        this.deck = new Deck();
        this.sideDeck = new Deck();
        this.fourCurrentCards = new Card[4];
        this.players = new HashMap<Player, PlayerToken>();
        
        this.initAreas();
        
        this.initializationDecks();
    }
    
    
    /**
     * Returns the card picked
     * 
     * @author Gael
     * @param index of the card picked in the table
     * @return card picked
     */
    public Card pickCardOnBoard(int index){
    	
    	Card card = this.fourCurrentCards[index];
    	this.fourCurrentCards[index] = this.deck.pick();
    	
    	return card;
    }
    

    /**
     * Initialization of areas
     * - use configManager to get some informations
     */
    private void initAreas() throws IOException{
        
        // Get the number of empty tokens inside each excavation areas
        int nbEmptyTokenPoint = Integer.parseInt(ConfigManager.getInstance().getConfig().getProperty("nbEmptyTokenPoint") );
        
        /**
         * Init areas
         * - We get only keys beginning with 'areas'
         * - We set basic informations (name, color, ...)
         * - We set tokens inside each areas
         * - We set distance between each areas
         */
        ArrayList<String> keys = ConfigManager.getInstance().getConfigKeysBeginningBy("areas");
        for (String key : keys){
            
            // we split the key to get different part
            String[] splittedKey = key.split( "\\." );
            String categorie    = splittedKey[1];
            String areaName     = splittedKey[2];
            
            // area does not exist
            if( areas.containsKey( areaName ) == false ){
                
                Area newArea = null;
                
                /**
                 * Case of touristic area
                 */
                if( categorie.equals( "touristic" )){
                    newArea = new TouristicArea(0, areaName);
                }
                /**
                 * Case of excavation area
                 */
                else{
                    String color = ConfigManager.getInstance().getConfig().getProperty( "areas." + categorie + "." + areaName + ".color" );
                    newArea = new ExcavationArea(0, areaName, color);
                    
                    // Set empty tokens
                    for (int i = 0; i < nbEmptyTokenPoint; i++) {
                        ((ExcavationArea)newArea).addToken( new PointToken("empty", ((ExcavationArea)newArea).getCodeColor(), 0)); // assign empty point
                    }
                    
                    // Set knowledge tokens (one to each excavation area)
                    ((ExcavationArea)newArea).addToken( new GeneralKnowledgeToken("GeneralKnowledge", ((ExcavationArea)newArea).getCodeColor() ) );
                    ((ExcavationArea)newArea).addToken( new SpecificKnowledgeToken("SpecificKnowledge", ((ExcavationArea)newArea).getCodeColor() ) );
                    
                    // Set point tokens
                    String pointsTokenString = ConfigManager.getInstance().getConfig().getProperty("areas." + categorie + "." + areaName + ".pointTokens"); // get string liek 1:2,2:4
                    String[] sections = pointsTokenString.split("\\,"); // split each 1:2,2:4 => (get [1:2] [2:4])
                    for (String section : sections) {
                        Integer value = Integer.parseInt( section.substring(0, section.indexOf(":")) ); // 1:2 => (get 1)
                        Integer nbTokenOfThisValue = Integer.parseInt( section.substring(section.indexOf(":") + 1) ); // 1:2 => (get 2)
                        for (int i = 0; i < nbTokenOfThisValue; i++) {
                            ((ExcavationArea)newArea).addToken( new PointToken("point", ((ExcavationArea)newArea).getCodeColor(), value ) ); // assign one token of this value
                        }
                    }
                }
                
                /**
                 * For all areas
                 */
                // We get only keys about the distance of this area and others area
                ArrayList<String> keysOfDistance = ConfigManager.getInstance().getConfigKeysBeginningBy("areas." + categorie + "." + areaName + ".to");

                // We iterate over each reachable area from this area
                for (String keyOfDistance : keysOfDistance){
                    String[] splittedkeyOfDistance = keyOfDistance.split( "\\." );
                    String to   = splittedkeyOfDistance[4];
                    
                    // Get the steps areas of this area and its destination
                    String stepsAreas = ConfigManager.getInstance().getConfig().getProperty( "areas." + categorie + "." + areaName + ".to." + to );
                    String[] stepsAreasSplitted;
                    if(stepsAreas.equals("")){
                        stepsAreasSplitted = new String[0]; // no steps
                    }
                    else{
                        stepsAreasSplitted = stepsAreas.split("\\,"); // some steps
                    }
                    
                    newArea.getDistances().put( to, stepsAreasSplitted ); // We set the distance
                }
                
                areas.put( areaName, newArea ); // We put this area inside list of areas
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
