
package com.miage.game;

import com.miage.SAMPLE.SAMPLECLASS;
import com.miage.areas.*;

import com.miage.cards.*;
import com.miage.config.ConfigManager;
import com.miage.tokens.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 
 * 
 * action du joueur :   - piocher une carte des quatres cartes
 *                      - piocher une carte expo
 *                      - se déplacer à varsovie pour changer les quatre cartes
 *                      - aller dans une zone de fouille
 * 
 * @author maxime
 */
public class Board {
    
    private final static Logger LOGGER = LogManager.getLogger(SAMPLECLASS.class.getName());
    
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
    private HashMap<PlayerToken, Player> playerTokensAndPlayers;
    
    private PlayerToken currentPlayerToken;

    private Deck deck;
    private Deck sideDeck;

    private Card fourCurrentCards[];
    private ExpoCard threeExpoCards[];

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
        this.threeExpoCards = new ExpoCard[3];
        this.playerTokensAndPlayers = new HashMap<PlayerToken, Player>();
        this.initAreas();
        this.initDecks();
    }
    
    
    /**
     * Returns the card picked
     * 
     * @author Gael
     * @param index of the card picked in the table
     * @return card picked
     */
    public Card pickCardOnBoard(int index){
    	
    	Card cardToReturn = this.fourCurrentCards[index];
    	
    	Card cardToAddOnTheBoard = this.deck.pick();
    	
    	while(cardToAddOnTheBoard instanceof ExpoCard){
    		
    		ExpoCard expoCard = (ExpoCard) cardToAddOnTheBoard;
    		addExpoCardOnBoard(expoCard);
    		cardToAddOnTheBoard = this.deck.pick();
    	}
    	
    	this.fourCurrentCards[index] = cardToAddOnTheBoard;
    	
    	
    	return cardToReturn;
    }
    
    /**
     * @author Gael
     * 
     * when an expo card is picked, this card goes on the expo cards place on the board
     * 
     * @param expoCard
     */
    public void addExpoCardOnBoard(ExpoCard expoCard){
    	
    	for(int i = 2; i > 0; i--){
    		this.getThreeExpoCards()[i] = this.getThreeExpoCards()[i-1];
    	}
    	
    	this.getThreeExpoCards()[0] = expoCard;
    	
    }
    

    /**
     * Initialization of areas
     * - use configManager to get some informations
     * @author maxime
     */
    private void initAreas() throws IOException{
        
        // Get the number of empty tokens inside each excavation areas
        int nbEmptyTokenPoint = Integer.parseInt(ConfigManager.getInstance().getConfig( ConfigManager.GENERAL_CONFIG_NAME ).getProperty("nbEmptyTokenPoint") );

        /**
         * Init areas
         * - We get only keys beginning with 'areas'
         * - We set basic informations (name, color, ...)
         * - We set tokens inside each areas
         * - We set distance between each areas
         */
        Set<String> keys = ConfigManager.getInstance().getConfig( ConfigManager.AREAS_CONFIG_NAME).stringPropertyNames();
        for (String key : keys){
            
            // we split the key to get different part
            String[] splittedKey = key.split( "\\." );
            String areaName     = splittedKey[0];
            String categorie    = ConfigManager.getInstance().getConfig( ConfigManager.AREAS_CONFIG_NAME ).getProperty( areaName + ".type" );
            
            // area does not exist in list yet
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
                    String color = ConfigManager.getInstance().getConfig( ConfigManager.AREAS_CONFIG_NAME ).getProperty( areaName + ".color" );
                    newArea = new ExcavationArea(0, areaName, color);
                    
                    // Set empty tokens
                    for (int i = 0; i < nbEmptyTokenPoint; i++) {
                        ((ExcavationArea)newArea).addToken( new PointToken("empty", ((ExcavationArea)newArea).getCodeColor(), 0)); // assign empty point
                    }
                    
                    // Set knowledge tokens (one to each excavation area)
                    ((ExcavationArea)newArea).addToken( new GeneralKnowledgeToken("GeneralKnowledge", ((ExcavationArea)newArea).getCodeColor() ) );
                    ((ExcavationArea)newArea).addToken( new SpecificKnowledgeToken("SpecificKnowledge", ((ExcavationArea)newArea).getCodeColor() ) );
                    
                    // Set point tokens
                    String pointsTokenString = ConfigManager.getInstance().getConfig( ConfigManager.AREAS_CONFIG_NAME ).getProperty(areaName + ".pointTokens"); // get string liek 1:2,2:4
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
                ArrayList<String> keysOfDistance = ConfigManager.getInstance().getConfigKeysBeginningBy(ConfigManager.AREAS_CONFIG_NAME, areaName + ".to");

                // We iterate over each reachable area from this area
                for (String keyOfDistance : keysOfDistance){
                    String[] splittedkeyOfDistance = keyOfDistance.split( "\\." );
                    String to   = splittedkeyOfDistance[2];
                    
                    // Get the steps areas of this area and its destination
                    String stepsAreas = ConfigManager.getInstance().getConfig( ConfigManager.AREAS_CONFIG_NAME ).getProperty( areaName + ".to." + to );
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
     * Initialization of the decks
     * - we get all cards from the config
     * - we get all number of cards present in each decks
     * - we instantiate new cards and we put in the decks which contains these cards
     * - we run process to finish deck (cute, mix, etc)
     * @author maxime
     */
    private void initDecks() throws IOException{

        Deck firstDeck = new Deck();
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        List<String> cardsInsideFirstDeckUnsorted = new ArrayList();  // list of cards number present in this deck (if a card is present x times there are x time the number of this card inside this list)
        List<String> cardsInsideDeck2Unsorted = new ArrayList();      // same as above for deck 2
        List<String> cardsInsideSideDeckUnsorted = new ArrayList();   // same as above for side deck
        HashMap<Object, Integer> cardsInsideFirstDeck;  // hashmap with the cards numbers inside the deck and for each number the number of occurance
        HashMap<Object, Integer> cardsInsideDeck2;      // above ..
        HashMap<Object, Integer> cardsInsideSideDeck;   // above ..
                

        // We get all card's numbers inside this deck (these cards can be in mutliple occurance)
        cardsInsideFirstDeckUnsorted.addAll( Arrays.asList( ConfigManager.getInstance().getConfig( ConfigManager.CARDS_CONFIG_NAME ).getProperty( "deck.firstDeck.cards" ).split("\\,") ));
        cardsInsideDeck2Unsorted.addAll( Arrays.asList( ConfigManager.getInstance().getConfig( ConfigManager.CARDS_CONFIG_NAME ).getProperty( "deck.2.cards" ).split("\\,") ));
        cardsInsideDeck2Unsorted.addAll( Arrays.asList( ConfigManager.getInstance().getConfig( ConfigManager.CARDS_CONFIG_NAME ).getProperty( "deck.2.cards.players." + nbPlayers ).split("\\,"))); // we add the cards relating to how many players
        cardsInsideSideDeckUnsorted.addAll( Arrays.asList( ConfigManager.getInstance().getConfig( ConfigManager.CARDS_CONFIG_NAME ).getProperty( "deck.sideDeck.cards" ).split("\\,")) );
        cardsInsideSideDeckUnsorted.addAll( Arrays.asList( ConfigManager.getInstance().getConfig( ConfigManager.CARDS_CONFIG_NAME ).getProperty( "deck.sideDeck.cards.players." + nbPlayers ).split("\\,") )); // we add the cards relating to how many players
        
        // We transform the list of cards to an hashmap with their occurance
        cardsInsideFirstDeck = Deck.transformListOfCard( cardsInsideFirstDeckUnsorted, String.class );
        cardsInsideDeck2 = Deck.transformListOfCard( cardsInsideDeck2Unsorted, String.class );
        cardsInsideSideDeck = Deck.transformListOfCard( cardsInsideSideDeckUnsorted, String.class );
        
        // Main loop to init cards, and add to the decks
        HashMap<String, String> cardsEntriesFromConfig = ConfigManager.getInstance().getConfigEntriesWithKeysBeginningBy(ConfigManager.CARDS_CONFIG_NAME, "card");
        for (Entry<String, String> entry : cardsEntriesFromConfig.entrySet()) {

            /**
             * Here we get basic information from the key and the values
             */
            String cardNumberString = entry.getKey().substring( "card.".length() );  // get only the number of the card (card.x) -> x
            String[] values = entry.getValue().split("\\,"); // split values line
            String area = values[0];
            String type = values[1];
            Integer weekCost = Integer.parseInt( values[2] );
            
            Card newCard = null;

            /**
             * Here we instantiate the card relating to the specified type in config file (the values)
             */
            if( type.equals("excavationAuthorization") ){
                newCard = new ExcavationAuthorizationCard(area, weekCost);
            }
            else if( type.equals("zeppelin") ){
                newCard = new ZeppelinCard(area, weekCost);
            }
            else if( type.equals("car") ){
                newCard = new CarCard(area, weekCost);
            }
            else if( type.equals("congress") ){
                newCard = new CongressCard(area, weekCost);
            }
            else if( type.equals("assistant") ){
                newCard = new AssistantCard(area, weekCost);
            }
            else if( type.equals("shovel") ){
                newCard = new ShovelCard(area, weekCost);
            }
            else if( type.equals("generalKnowledge") ){
                int value = Integer.parseInt(values[3]); // check pattern in .properties
                newCard = new GeneralKnowledgeCard(area, weekCost, value);
            }
            else if (type.equals("specificKnowledge")){
                int value = Integer.parseInt(values[3]); // check pattern in .properties
                String excavationArea = values[4];
                newCard = new SpecificKnowledgeCard(area, weekCost, value, excavationArea);
            }
            else if (type.equals("ethnologicalKnowledge")){
                int value = Integer.parseInt(values[3]); // check pattern in .properties
                String excavationArea = values[4];
                newCard = new EthnologicalKnowledgeCard(area, weekCost, value, excavationArea);
            }
            else if(type.equals("expo")){
                boolean bigExpo = Boolean.parseBoolean(values[3]); // check pattern in .properties
                newCard = new ExpoCard(area, weekCost, bigExpo);
            }
            
            LOGGER.debug("current card : " + newCard);
            /**
             * Here we check if the card should be added to the different deck and how many time
             */
            // Deck 1
            if(cardsInsideFirstDeck.containsKey( cardNumberString )){
                for (int i = 0; i < cardsInsideFirstDeck.get( cardNumberString ); i++) {
                    firstDeck.add( newCard );
                }
            }
            // Deck 2
            if(cardsInsideDeck2.containsKey( cardNumberString )){
                for (int i = 0; i < cardsInsideDeck2.get( cardNumberString ); i++) {
                    deck2.add( newCard );
                }
            }
            // sidedeck
            if(cardsInsideSideDeck.containsKey( cardNumberString )){
                for (int i = 0; i < cardsInsideSideDeck.get( cardNumberString ); i++) {
                    this.sideDeck.add( newCard );
                }
            }
        }

        /*
         * Mix of the first deck and initialization of the four cards on the board
         */
        firstDeck.mix();
        int indexOfPartOneOfThree = (firstDeck.size()/3);
        deck1           = firstDeck.divideDeck( 0, indexOfPartOneOfThree ); // deck 1 contain the part 1/3
        deck2           = firstDeck.divideDeck( indexOfPartOneOfThree, indexOfPartOneOfThree + indexOfPartOneOfThree ); // deck 2 contain part 2/3
        this.sideDeck   = firstDeck.divideDeck( ( indexOfPartOneOfThree + indexOfPartOneOfThree ), firstDeck.size()-1 ); // side deck contain part 3/3
        
        if(this.nbPlayers <= 2){
            this.sideDeck.clear();
        }
        
        deck2.mix();
        this.deck.addAll(deck2);
        this.deck.addAll(deck1);

        // Pick four card inside main deck and put on the board
        this.fourCurrentCards[0] = this.deck.pick();
        this.fourCurrentCards[1] = this.deck.pick();
        this.fourCurrentCards[2] = this.deck.pick();
        this.fourCurrentCards[3] = this.deck.pick();
    }
    
   

    
    /***********************************************************************************************
     *
     *                                  Getter & Setter
     * 
     *  
    ************************************************************************************************/
    
    /**
     * Return the player relating to the given token.
     * @param token
     * @return 
     */
    public Player getPlayerByToken( PlayerToken token ){
        return this.playerTokensAndPlayers.get( token );
    }
    
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

    /**
     * 
     * @return
     */
    public ExpoCard[] getThreeExpoCards() {
            return threeExpoCards;
    }

    /**
     * 
     * @param threeExpoCards
     */
    public void setThreeExpoCards(ExpoCard[] threeExpoCards) {
            this.threeExpoCards = threeExpoCards;
    }


    public Area getArea(String areaName){

            return this.getAreas().get(areaName);
    }


    public HashMap<PlayerToken, Player> getPlayerTokensAndPlayers() {
            return playerTokensAndPlayers;
    }


    public void setPlayerTokensAndPlayers(
                    HashMap<PlayerToken, Player> playerTokensAndPlayers) {
            this.playerTokensAndPlayers = playerTokensAndPlayers;
    }
	
	
	
	
	

	
}
