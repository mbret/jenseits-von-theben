package com.miage.game;

import com.miage.areas.*;
import com.miage.cards.*;
import com.miage.config.ConfigManager;
import com.miage.main.Main;
import com.miage.tokens.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 
 * note :
 * 
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
     * Represent the end date position for all player (when all player arrive at this date the game is over)
     */
    private  LocalDate endGameDatePosition;
    
    /**
     * Represent the start date position for all players depending of their numbers
     */
    private  LocalDate startGameDatePosition;
    
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
    private List<ExpoCard> expoCards;

    /**
     * HashMap used to determine how many token of each points are present in each excavation point
     * Init from extern configuration
     */
    private HashMap<String, HashMap<Integer, Integer>> areasTokensConfiguration;

    
    public Board(int nbPlayers, Set<Player> players) throws IOException{
        this.nbPlayers = nbPlayers;
        this.areas = new HashMap<>();
        this._initAreas();
        this.chrono = new Chrono();
        this.chrono.initializationValues();
        this.expoCards = new LinkedList();
        this.deck = new Deck();
        this.sideDeck = new Deck();
        this.fourCurrentCards = new Card[4];
        this._initDecks();
        this.playerTokenStack = new PlayerTokenStack();
        this.playerTokensAndPlayers = new HashMap();

        // Init date of end game
        String[] tmp = ConfigManager.getInstance().getConfig(ConfigManager.GENERAL_CONFIG_NAME).getProperty("game.endGameDatePosition").split("\\|")[0].split("\\-");
        this.endGameDatePosition = LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));
        tmp = ConfigManager.getInstance().getConfig(ConfigManager.GENERAL_CONFIG_NAME).getProperty("game.startGameDatePosition").split("\\|")[this.nbPlayers-1].split("\\-");
        this.startGameDatePosition = LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));
        
        for (Player player : players){
            player.getPlayerToken().setPosition( this.areas.get("warsaw")); // set started area for playertoken
            player.getPlayerToken().setTimeState( this.startGameDatePosition ); // init date of token
            this.playerTokenStack.addPlayerToken(player.getPlayerToken() ); // set stack of playertoken
            this.playerTokensAndPlayers.put( player.getPlayerToken(), player); // set the link between player and playerTokens
        }
    }
    
    /**
     * Still here for compatibilities
     * @param nbPlayers
     * @deprecated  
     */
    public Board(int nbPlayers) throws IOException{
        this(nbPlayers, new HashSet<Player>());

    }
    
    
    
    
    /***********************************************************************************************
     *
     *                                  Public Methods
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
    	this.expoCards.add(0, expoCard); // insert new
        if( this.expoCards.size() > 3 ){
            this.expoCards.remove( 3 ); // remove the old third element
        }
    }
    
    /**
     * 
     * @author david
     */
    public void changeFourCurrentCards(){
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

    /**
     * Check if a player token has enough time before end game
     * @param currentDatePosition
     * @param weekCost
     * @param endGameDatePosition
     * @return 
     */
    public static boolean hasEnoughTimeBeforeEndGame( LocalDate currentDatePosition, int weekCost, LocalDate endGameDatePosition){
        LocalDate dateAfterTravel = currentDatePosition.plusWeeks( weekCost );
        return ( ( Main.getWeek( dateAfterTravel )) <= Main.getWeek( endGameDatePosition ) && Main.getYear( dateAfterTravel ) <= Main.getYear( endGameDatePosition ) ); // test if enough time
    }
    
    /**
     * Return the player who must play on this current round
     * @return 
     */
    public Player getCurrentPlayer(){
        return this.playerTokensAndPlayers.get( this.playerTokenStack.getFirstPlayerToken() );
    }
    
    /**
     * Check if there are still some players which are able to play (not end game)
     * @return 
     */
    public boolean hasUpcomingPlayer(){
        throw new UnsupportedOperationException("not implemented yet");
    }
    
    /**
     * Calculate and set the point of each player
     */
    public void calculatePoint(){
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Do a player round action
     * - update the state of the game
     * @param actionPattern 
     */
    public void doPlayerRoundAction( int actionPattern, Player player ){
        switch(actionPattern){
            case Player.ACTION_CHANGE_FOUR_CARDS:
                this._actionPlayerDoChangeFourCards( player );
            case Player.ACTION_EXCAVATE:
                this._actionPlayerDoExcavateArea( player );
            case Player.ACTION_ORGANIZE_EXPO:
                this._actionPlayerDoOrganizeExpo( player );
            case Player.ACTION_PICK_CARD:
                this._actionPlayerDoPickCard( player );
        }
    }
    
    /**
     * Check if the player is able to do the demanded action. Use player action Constant to provide an action key
     * @param actionPattern player constant (exemple: player.ACTION...)
     * @return 
     */
    public boolean isPlayerAbleToMakeRoundAction( int actionPattern, Player player ){
        switch(actionPattern){
            case Player.ACTION_CHANGE_FOUR_CARDS:
                return this._actionPlayerAbleToChangeFourCards( player );
            case Player.ACTION_EXCAVATE:
                return this._actionPlayerAbleToExcavateArea( player );
            case Player.ACTION_ORGANIZE_EXPO:
                return this._actionPlayerAbleToOrganizeExpo(player );
            case Player.ACTION_PICK_CARD:
                return this._actionPlayerAbleToPickCard(player );
        }
        throw new UnsupportedOperationException();
    }
    
    
    
    
    /***********************************************************************************************
     *
     *                                  Private Methods
     * 
     ***********************************************************************************************/
    
    
    
    /**
     * Check if the player is able now to move in Warschau and change all four current cards
     * Conditions :
     *  - must have enough time to go there (move + cost of operation)
     * 
     * @param endGameDatePosition 
     * @param playerToken 
     * @return  
     */
    private boolean _actionPlayerAbleToChangeFourCards( Player player ){
        int costOfOperation = player.getNbRoundStillPlaying(); // the cost of this operation is depending of how long time the player is still playing (only him)
        return Board.hasEnoughTimeBeforeEndGame( player.getPlayerToken().getTimeState(), player.getPlayerToken().getPosition().getDistanceWeekCostTo( "warsaw" ) + costOfOperation, endGameDatePosition);
    }
    
    /**
     * Check if the player is able to move inside an excavation area
     * Conditions :
     *  - must have enough time to go there (move)
     *  - must have authorization to excavate in this area
     *  - must have at least 1 specific knowledge point about this area (dev, token)
     * 
     * @param player
     * @param playerToken 
     * @return  
     */
    private boolean _actionPlayerAbleToExcavateArea( Player player  ){
        // We check for all area (if one is able then we break the loop (no need to test others))
        for (Area area : this.areas.values()) {
            if( area instanceof ExcavationArea ){
                if ( player.isAbleToExcavateArea( area.getName() ) // 
                        && player.hasEnoughTimeToGoInThisArea( area.getName(), endGameDatePosition)
                        && (player.hasKnowledgeCardForThisExcavationArea( area.getName() ) || player.hasKnowledgeTokenForThisExcavationArea( area.getName() )) ){
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Check if the player is able to organize an exposition
     * Conditions :
     *  - must have enough time to go there (mve + weekcost of card)
     *  - must have the required number of tokens and these tokens must be matchable with the colot (area)
     * @param expoCards
     * @return 
     */
    private boolean _actionPlayerAbleToOrganizeExpo( Player player ){
        Set<PointToken> playerTokens = player.getSpecificTokens( PointToken.class );
        
        // iterate over all expo card (if one is possible then we break the loop)
        for (ExpoCard expoCard : expoCards){

            // iterate over all expo token to check if the user has one token 
            for (Token token : ((ExpoCard)expoCard).getTokens()) {
                int valueNeededForThisKindOfToken = ((PointToken)token).getValue();
                
                for (PointToken playerToken : playerTokens) {
                    if( playerToken.getAreaName().equals( token.getAreaName() )){
                        valueNeededForThisKindOfToken--; // if the player has one token of this area we reduce this variable 
                    }
                }
                
                // if the player didn't has enough point token about this current token then he is not able to continue
                if( valueNeededForThisKindOfToken > 0){
                    return false;
                }
            }
            
            // We test if the player has enough time to do all this operation
            if( Board.hasEnoughTimeBeforeEndGame(  
                    player.getPlayerToken().getTimeState(), 
                    player.getPlayerToken().getPosition().getDistanceWeekCostTo( expoCard.getAreaName() ) + ((ExpoCard)expoCard).getWeekCost(), 
                    endGameDatePosition) // check if enought time to go there + week cost of the card
                    ){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check if the player is able to pick up a card
     * Condition:
     *  - must have enough time to go there (move + cost of card)
     * @return 
     */
    private boolean _actionPlayerAbleToPickCard( Player player ){
        for (Card card : this.fourCurrentCards) {
            if( Board.hasEnoughTimeBeforeEndGame( 
                        player.getPlayerToken().getTimeState(),
                        player.getPlayerToken().getPosition().getDistanceWeekCostTo( card.getAreaName() ) + card.getWeekCost(), 
                        endGameDatePosition) ){
                return true;
            }
        }
        return false;
    }
    
    private void _actionPlayerDoChangeFourCards( Player player ) {
        throw new UnsupportedOperationException("not implemented yet");
    }
    
    private void _actionPlayerDoExcavateArea( Player player  ) {
        throw new UnsupportedOperationException("not implemented yet");
    }
    
    private void _actionPlayerDoOrganizeExpo( Player player ){
        throw new UnsupportedOperationException("not implemented yet");
    }
    
    private void _actionPlayerDoPickCard( Player player ){
        throw new UnsupportedOperationException("not implemented yet");
    }
    
    /**
     * Initialization of areas
     * - use configManager to get some informations
     * @author maxime
     */
    private void _initAreas() throws IOException{
        
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
                            ((ExcavationArea)newArea).addToken( new PointToken(id, newArea.getName(), ((ExcavationArea)newArea).getCodeColor(), 0)); // assign empty point
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
                            ((ExcavationArea)newArea).addToken( new PointToken(id, newArea.getName(), ((ExcavationArea)newArea).getCodeColor(), value ) ); 
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
                            ((ExcavationArea)newArea).addToken( new GeneralKnowledgeToken(id, newArea.getName(), ((ExcavationArea)newArea).getCodeColor() ) );
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
                            ((ExcavationArea)newArea).addToken( new SpecificKnowledgeToken(id, newArea.getName(), ((ExcavationArea)newArea).getCodeColor() ) );
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
    private void _initDecks() throws IOException{

        ArrayList<ExpoCard> expoCards = new ArrayList<>(); // used to retain apart expo cards
        Deck firstDeck = new Deck();

        // Main loop to init cards, and add to the decks
        HashMap<String, String> cardsEntriesFromConfig = ConfigManager.getInstance().getConfigEntriesWithKeysBeginningBy(ConfigManager.CARDS_CONFIG_NAME, "card");
        for (Entry<String, String> entry : cardsEntriesFromConfig.entrySet()) {

            /**
             * Here we get basic information from the key and the values
             */
//            String cardNumberString = entry.getKey().substring( "card.".length() );  // get only the number of the card (card.x) -> x
            String[] values = entry.getValue().split("\\|")[0].split("\\,"); // split values line
            String area = values[0];
            String type = values[1];
            Integer weekCost = Integer.parseInt( values[2] );
            int id = Integer.parseInt( entry.getKey().substring( "card.".length() ));
                    
            Card newCard = null;

            /**
             * Here we instantiate the card relating to the specified type in config file (the values)
             */
            switch (type) {
                case "excavationAuthorization":
                    newCard = new ExcavationAuthorizationCard(id, "displayName", area, weekCost);
                    break;
                case "zeppelin":
                    newCard = new ZeppelinCard(id, "displayName", area, weekCost);
                    break;
                case "car":
                    newCard = new CarCard(id, "displayName", area, weekCost);
                    break;
                case "congress":
                    newCard = new CongressCard(id, "displayName", area, weekCost);
                    break;
                case "assistant":
                    newCard = new AssistantCard(id, "displayName", area, weekCost);
                    break;
                case "shovel":
                    newCard = new ShovelCard(id, "displayName", area, weekCost);
                    break;
                case "generalKnowledge":
                    {
                        int value = Integer.parseInt(values[3]); // check pattern in .properties
                        newCard = new GeneralKnowledgeCard(id, "displayName", area, weekCost, value);
                        break;
                    }
                case "specificKnowledge":
                    {
                        int value = Integer.parseInt(values[3]); // check pattern in .properties
                        String excavationArea = values[4];
                        newCard = new SpecificKnowledgeCard(id, "displayName", area, weekCost, value, excavationArea);
                        break;
                    }
                case "ethnologicalKnowledge":
                    {
                        int value = Integer.parseInt(values[3]); // check pattern in .properties
                        String excavationArea = values[4];
                        newCard = new EthnologicalKnowledgeCard(id, "displayName", area, weekCost, value, excavationArea);
                        break;
                    }
                case "expo":
                    {
                        boolean bigExpo = Boolean.parseBoolean(values[3]); // check pattern in .properties
                        int value = Integer.parseInt(values[4]);
                        newCard = new ExpoCard(id, "displayName", area, weekCost, bigExpo, value);
                        String[] secondValuePart = entry.getValue().split("\\|")[1].split("\\,"); // crete:1,greece:2,egypt:3
                        
                        // we add the token point
                        for (int i = 0; i < secondValuePart.length; i++) {
                            String expoTokenAreaName    = secondValuePart[i].split("\\:")[0];
                            int expoTokenValue          = Integer.parseInt( secondValuePart[i].split("\\:")[1] );
                            String expoTokenId          = ConfigManager.getInstance().getConfig(ConfigManager.GENERAL_CONFIG_NAME).getProperty("expoCardTokens.id");
                            String expoTokenColor       = ConfigManager.getInstance().getConfig(ConfigManager.AREAS_CONFIG_NAME).getProperty("area."+expoTokenAreaName+".color");
                            ((ExpoCard)newCard).getTokens().add( new PointToken( expoTokenId,  expoTokenAreaName, expoTokenColor, expoTokenValue) );
                        }
                        break;
                    }
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

    public List<ExpoCard> getExpoCards() {
        return expoCards;
    }

    public void setExpoCards(List<ExpoCard> expoCards) {
        this.expoCards = expoCards;
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

    public LocalDate getEndGameDatePosition() {
        return endGameDatePosition;
    }

    public LocalDate getStartGameDatePosition() {
        return startGameDatePosition;
    }
    
    
    
}
