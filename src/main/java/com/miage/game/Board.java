package com.miage.game;

import com.miage.areas.*;
import com.miage.cards.*;
import com.miage.config.ConfigManager;
import com.miage.tokens.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 
 * note :
 * action du joueur :   - piocher une carte des quatres cartes
 *                      - piocher une carte expo
 *                      - se déplacer à varsovie pour changer les quatre cartes
 *                      - aller dans une zone de fouille
 * 
 * @author maxime
 */
public class Board {
    
    private final static Logger LOGGER = LogManager.getLogger(Board.class.getName());
    
    /**
     * 
     */
    private final int nbPlayers;

    /**
     * 
     */
    private final Chrono chrono;
    
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
    
    /**
     * défausse 
     */
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
    
    
    
    
    /***********************************************************************************************
     *
     *                                  Methods
     * 
     ***********************************************************************************************/
    
    
    
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
       
        /**
         * Init areas
         * - We get only keys beginning with 'areas'
         * - We set basic informations (name, color, ...)
         * - We set tokens inside each areas
         * - We set distance between each areas
         */
        HashMap<String, String> entries = ConfigManager.getInstance().getConfigEntriesWithKeysBeginningBy( ConfigManager.AREAS_CONFIG_NAME, "area");
        for (Entry<String, String> entry : entries.entrySet()) {
            String string1 = entry.getValue();
  
            // we split the key to get different part
            String[] splittedKey = entry.getKey().split( "\\." );
            String areaName     = splittedKey[1]; // area.(areaname)
            
            // area does not exist in list yet
            if( areas.containsKey( areaName ) == false ){
                
                Area newArea = null;
//                LOGGER.debug("area." + areaName + ".type");
                String categorie    = entries.get("area." + areaName + ".type" );
                
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
                    String color = entries.get("area." + areaName + ".color" );
                    newArea = new ExcavationArea(0, areaName, color);
                    
                    /**
                     * Lets fill tokens
                     */
                    String id; // define the id to retrieve image file
                    int nb; // nb occurence of the current token
                    int nbTokens; // nb different tokens
                    String[] set; // contain the token id or id,value and its occurence
                    
                    // Set empty tokens
                    String[] emptyTokens = ((String)entries.get("area." + areaName + ".emptyTokens" )).split("\\|");
                    nbTokens = emptyTokens.length;
                    for (int i = 0; i < nbTokens; i++) {
                        set = emptyTokens[i].split("\\:");
                        nb = Integer.parseInt(set[1]);
                        id = set[0];
                        for (int j = 0; j < nb; j++) {
                            ((ExcavationArea)newArea).addToken( new PointToken(id, "undefined", ((ExcavationArea)newArea).getCodeColor(), 0)); // assign empty point
                        }
                    }
                    
                    // Set value tokens
                    String[] pointTokens = ((String)entries.get("area." + areaName + ".pointTokens" )).split("\\|");
                    nbTokens = pointTokens.length;
                    for (int i = 0; i < nbTokens; i++) {
                        set = pointTokens[i].split("\\:");
                        String[] subSet = set[0].split("\\,");
                        nb = Integer.parseInt(set[1]);
                        id = subSet[0];
                        int value = Integer.parseInt(subSet[1]);
                        for (int j = 0; j < nb; j++) {
                            ((ExcavationArea)newArea).addToken( new PointToken(id, "undefined", ((ExcavationArea)newArea).getCodeColor(), value ) ); 
                        }
                    }

                    // Set general knowledge tokens (one to each excavation area)
                    String[] generalKnowledgesTokens = ((String)entries.get("area." + areaName + ".generalKnowledgeTokens" )).split("\\|");
                    nbTokens = generalKnowledgesTokens.length;
                    for (int i = 0; i < nbTokens; i++) {
                        set = generalKnowledgesTokens[i].split("\\:");
                        nb = Integer.parseInt(set[1]);
                        id = set[0];
                        for (int j = 0; j < nb; j++) {
                            ((ExcavationArea)newArea).addToken( new GeneralKnowledgeToken(id, "undefined", ((ExcavationArea)newArea).getCodeColor() ) );
                        }
                    }
                    
                    // Set general knowledge tokens (one to each excavation area)
                    String[] specificKnowledgesTokens = ((String)entries.get("area." + areaName + ".specificKnowledgeTokens" )).split("\\|");
                    nbTokens = specificKnowledgesTokens.length;
                    for (int i = 0; i < nbTokens; i++) {
                        set = specificKnowledgesTokens[i].split("\\:");
                        nb = Integer.parseInt(set[1]);
                        id = set[0];
                        for (int j = 0; j < nb; j++) {
                            ((ExcavationArea)newArea).addToken( new SpecificKnowledgeToken(id, "undefined", ((ExcavationArea)newArea).getCodeColor() ) );
                        }
                    }
                }
                
                /**
                 * For all areas we set the distance with others area
                 */
                // We get only keys about the distance of this area and others area
                HashMap<String, String> keysOfDistance = ConfigManager.getInstance().getConfigEntriesWithKeysBeginningBy(ConfigManager.AREAS_CONFIG_NAME, "area." + areaName + ".to");

                // We iterate over each reachable area from this area
                for (Entry<String, String> entry1 : keysOfDistance.entrySet()) {

                    String[] splittedkeyOfDistance = entry1.getKey().split( "\\." );
                    String to   = splittedkeyOfDistance[3];
                    
                    // Get the steps areas of this area and its destination
                    String stepsAreas = keysOfDistance.get( "area." + areaName + ".to." + to );
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
     * - we instantiate new cards and we put these cards inside first deck
     * - we run process to finish deck (cute, mix, etc)
     * @author maxime
     */
    private void initDecks() throws IOException{

        ArrayList<ExpoCard> expoCards = new ArrayList<ExpoCard>(); // used to retain apart expo cards
        Deck firstDeck = new Deck();
                

        // Main loop to init cards, and add to the decks
        HashMap<String, String> cardsEntriesFromConfig = ConfigManager.getInstance().getConfigEntriesWithKeysBeginningBy(ConfigManager.CARDS_CONFIG_NAME, "card");
        for (Entry<String, String> entry : cardsEntriesFromConfig.entrySet()) {

            /**
             * Here we get basic information from the key and the values
             */
//            String cardNumberString = entry.getKey().substring( "card.".length() );  // get only the number of the card (card.x) -> x
            String[] values = entry.getValue().split("\\,"); // split values line
            String area = values[0];
            String type = values[1];
            Integer weekCost = Integer.parseInt( values[2] );
            int id = Integer.parseInt( entry.getKey().substring( "card.".length() ));
                    
            Card newCard = null;

            /**
             * Here we instantiate the card relating to the specified type in config file (the values)
             */
            if( type.equals("excavationAuthorization") ){
                newCard = new ExcavationAuthorizationCard(id, "displayName", area, weekCost);
            }
            else if( type.equals("zeppelin") ){
                newCard = new ZeppelinCard(id, "displayName", area, weekCost);
            }
            else if( type.equals("car") ){
                newCard = new CarCard(id, "displayName", area, weekCost);
            }
            else if( type.equals("congress") ){
                newCard = new CongressCard(id, "displayName", area, weekCost);
            }
            else if( type.equals("assistant") ){
                newCard = new AssistantCard(id, "displayName", area, weekCost);
            }
            else if( type.equals("shovel") ){
                newCard = new ShovelCard(id, "displayName", area, weekCost);
            }
            else if( type.equals("generalKnowledge") ){
                int value = Integer.parseInt(values[3]); // check pattern in .properties
                newCard = new GeneralKnowledgeCard(id, "displayName", area, weekCost, value);
            }
            else if (type.equals("specificKnowledge")){
                int value = Integer.parseInt(values[3]); // check pattern in .properties
                String excavationArea = values[4];
                newCard = new SpecificKnowledgeCard(id, "displayName", area, weekCost, value, excavationArea);
            }
            else if (type.equals("ethnologicalKnowledge")){
                int value = Integer.parseInt(values[3]); // check pattern in .properties
                String excavationArea = values[4];
                newCard = new EthnologicalKnowledgeCard(id, "displayName", area, weekCost, value, excavationArea);
            }
            else if(type.equals("expo")){
                boolean bigExpo = Boolean.parseBoolean(values[3]); // check pattern in .properties
                int value = Integer.parseInt(values[4]);
                newCard = new ExpoCard(id, "displayName", area, weekCost, bigExpo, value);
            }
            
            // We add the card inside deck but we retain expo card
            if( newCard instanceof ExpoCard){
                expoCards.add( (ExpoCard)newCard );
            }
            else{
                firstDeck.add( newCard );
            }
        }
        

        firstDeck.mix(); // We mix the big deck
//        LOGGER.debug("initDecks : sizeof firsDeck : " + firstDeck.size());
        this.fourCurrentCards = firstDeck.pickFourFirstCards(); // We Pick the four first cards from the main deck
        Deck[] dividedDecks;

        if(nbPlayers <= 2){
            dividedDecks = firstDeck.getDividedDeck( 2 ); // only 2 decks
            dividedDecks[1].addAll( expoCards ); // we add all expo in deck 2
            this.sideDeck = new Deck(); // sideDeck is null/empty
        }
        else{
            dividedDecks = firstDeck.getDividedDeck( 3 ); // three equal decks
            for (ExpoCard card : expoCards) {
                if( ! card.isBigExpo()){
                    dividedDecks[1].add( card ); // add small expo in deck 2
                }
                if( card.isBigExpo()){
                    dividedDecks[2].add( card ); // add big expo in deck 3
                }
            }
            this.sideDeck = dividedDecks[2];
            this.sideDeck.mix();
        }

        dividedDecks[1].addAll( dividedDecks[0] ); // we put deck1 on deck2
        this.deck = new Deck( dividedDecks[1] ); // tmp deck 1 & 2 become main deck
    }
    
    /**
     * 
     * @author david
     */
    public void changeFourCurrentCards(){
        //if(this.currentPlayerToken.getPosition().getName() == "warsaw"){
            this.sideDeck.add(this.fourCurrentCards[0]);
            this.sideDeck.add(this.fourCurrentCards[1]);
            this.sideDeck.add(this.fourCurrentCards[2]);
            this.sideDeck.add(this.fourCurrentCards[3]);
            
            for(int i=0;i<4;i++){
                this.fourCurrentCards[i] = this.deck.pick();
                while(this.fourCurrentCards[i] instanceof ExpoCard){
                    this.addExpoCardOnBoard((ExpoCard)this.fourCurrentCards[i]);
                    this.fourCurrentCards[i] = this.deck.pick();
                }
            }
            this.setFourCurrentCards(fourCurrentCards);
       //}
    }
    
   /**
    * Take a number of knowledge points and return the number of tokens related to the week cost
    * @param knowledgePoints (mix of all knowledge points)
    * @param weekCost
    * @return int, the number of tokens the player can pick up
    * @throws IOException 
    */
    public static int getNbTokensFromChronotime( int knowledgePoints, int weekCost) throws IOException{
        String[] values = ConfigManager.getInstance().getConfig( ConfigManager.GENERAL_CONFIG_NAME ).getProperty( "chronotime." + knowledgePoints).split("\\|");
        try{
            return Integer.parseInt(values[ weekCost ]);
        }
        catch( ArrayIndexOutOfBoundsException e){
            throw new ArrayIndexOutOfBoundsException("The knowledge points provided is not setted in configuration file");
        }
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
    
    public PlayerToken getCurrentPlayerToken() {
        return currentPlayerToken;
    }

    public void setCurrentPlayerToken(PlayerToken currentPlayerToken) {
        this.currentPlayerToken = currentPlayerToken;
    }


    public Deck getDeck() {
        return deck;
    }
    
    public void setDeck(Deck deck){
    	this.deck = deck;
    }

    public HashMap<String, Area> getAreas() {
        return areas;
    }

    public Card[] getFourCurrentCards() {
        return fourCurrentCards;
    }

    public void setFourCurrentCards(Card[] fourCurrentCards) {
        this.fourCurrentCards = fourCurrentCards;
    }

    public ExpoCard[] getThreeExpoCards() {
        return threeExpoCards;
    }

    public void setThreeExpoCards(ExpoCard[] threeExpoCards) {
        this.threeExpoCards = threeExpoCards;
    }

    public Area getArea(String areaName){
        return this.getAreas().get(areaName);
    }

    public HashMap<PlayerToken, Player> getPlayerTokensAndPlayers() {
        return playerTokensAndPlayers;
    }

    public void setPlayerTokensAndPlayers( HashMap<PlayerToken, Player> playerTokensAndPlayers ) {
        this.playerTokensAndPlayers = playerTokensAndPlayers;
    }

    public Deck getSideDeck() {
        return sideDeck;
    }

    public void setSideDeck(Deck sideDeck) {
        this.sideDeck = sideDeck;
    }
    
}
