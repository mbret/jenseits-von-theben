package com.miage.game;

import Interface.DiscardableElement;
import Interface.KnowledgeElement;
import Interface.UsableElement;
import com.miage.areas.*;
import com.miage.cards.*;
import com.miage.config.ConfigManager;
import com.miage.main.Main;
import com.miage.tokens.*;
import java.io.IOException;
import java.time.LocalDate;
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

    /**
     * Normal and first used deck
     */
    private Deck deck;
    
    /**
     * Side deck used in case of the deck become empty
     */
    private Deck sideDeck;
    
    /**
     * Deck used to discard card
     */
    private Deck discardingDeck;

    private List<Card> fourCurrentCards;
    
    private List<ExpoCard> expoCards;

    private Chronotime chronotime;
    
    List<Player> playersWhoFinished = new ArrayList();
    
    
    private List<ExpoCard> expoCards;

    public Board(int nbPlayers, Set<Player> players) throws IOException{
        this.nbPlayers = nbPlayers;
        this.areas = new HashMap<>();
        this._initAreas();
        this.expoCards = new LinkedList();
        this.deck = new Deck();
        this.sideDeck = new Deck();
        this.discardingDeck = new Deck();
        this.fourCurrentCards = new LinkedList();
        this._initDecks();
        this.playerTokenStack = new PlayerTokenStack();
        this.playerTokensAndPlayers = new HashMap();
        this.chronotime = new Chronotime();
        
        // Init date of end game
        String[] tmp = ConfigManager.getInstance().getConfig(ConfigManager.GENERAL_CONFIG_NAME).getProperty("game.endGameDatePosition").split("\\|")[0].split("\\-");
        this.endGameDatePosition = LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));
        tmp = ConfigManager.getInstance().getConfig(ConfigManager.GENERAL_CONFIG_NAME).getProperty("game.startGameDatePosition").split("\\|")[this.nbPlayers-1].split("\\-");
        this.startGameDatePosition = LocalDate.of(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));
        
        // Init all about players and player tokens
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
     * <br/>Effect:
     * <br/>- pick desired card on the board
     * <br/>- replace new card from deck on the same place
     * <br/>- update expoCard in case of we pick this kind of card untill we get a non expo card
     * @author maxime
     * @param indexOf
     * @return card picked
     */
    public Card pickCardOnBoard( int indexOf ){
    	Card cardToReturn = this.fourCurrentCards.remove( indexOf );
    	
    	Card cardToAddOnTheBoard = null;
    	do{
            // If deck is empty we mix discardingDeck and sideDeck together
            // and we add tese two deck inside the main deck
            if( this.deck.isEmpty() ){
                this.discardingDeck.addAll( this.sideDeck );
                this.sideDeck.clear();
                this.discardingDeck.mix();
                this.deck.addAll( this.discardingDeck );
                this.discardingDeck.clear();
            }
            cardToAddOnTheBoard = this.deck.pick();
            if( cardToAddOnTheBoard instanceof ExpoCard ){
                addExpoCardOnBoard( (ExpoCard) cardToAddOnTheBoard );
            }
        }
    	while(cardToAddOnTheBoard instanceof ExpoCard);
    	
    	this.fourCurrentCards.add( indexOf , cardToAddOnTheBoard); // here we got card or null
    	
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
     * Change the fourth current card on the board
     * <br/>Effect:
     * <br/>- put the fourth current card on the deck
     * <br/>- replace 4 new cards on the board
     * <br/>- update expo in case of we pick this kind of card
     * @author david
     */
    public void changeFourCurrentCards(){
        for(int i=0;i<4;i++) this.discardingDeck.add( this.pickCardOnBoard( i ) );
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
     * Return the player who must play on this current round.
     * <br/>- If the player is on the list of players who finished the game : the game is over
     * <br/>- If the player is on the end game position : the game is over
     * @return 
     */
    public Player getUpcomingPlayer(){
        Player playerWhoShouldPlayFirst = this.playerTokensAndPlayers.get( this.playerTokenStack.getFirstPlayerToken() );
        if( this.playersWhoFinished.contains( playerWhoShouldPlayFirst )
                || this.isPlayerOnTheEndGamePosition( playerWhoShouldPlayFirst )){
            return null;
        }
        else{
            return playerWhoShouldPlayFirst;
        }
    }
    
    /**
     * Check if a player is on the last position, the end game. He is not able to play anymore
     * @param player
     * @return 
     */
    public boolean isPlayerOnTheEndGamePosition( Player player ){
        return player.getPlayerToken().getTimeState().equals( this.endGameDatePosition );
    }
    
    /**
     * Check if there are still some players which are able to play (not end game)
     * <br/>Effect:
     * <br/>- check if the last player still have some movements possible
     * @return 
     */
    public boolean hasUpcomingPlayer(){
        throw new UnsupportedOperationException("not implemented yet");
    }
    
    /**
     * Move a player to the endGame position, typically when a player cannot do any more actions
     */
    public void movePlayerToEndGamePosition( Player player ){
        player.getPlayerToken().setTimeState( this.endGameDatePosition );
    }
    
    /**
     * Do a player round action
     * - update the state of the game
     * - check and discard used cards
     * - increment the round a player is still playing
     * @param actionPattern 
     * @param player 
     * @param usedCards 
     * @param areaToExcavate 
     * @param cardToPickUp 
     * @param expoCardToDo 
     * @param usedKnowledgePointElements 
     */
    public void doPlayerRoundAction( int actionPattern, Player player, List<UsableElement> usedElements, ExcavationArea areaToExcavate, Card cardToPickUp, ExpoCard expoCardToDo, Integer nbWeeksForExcavation){
        
        boolean useZeppelin = false;                                // Does the player is using zeppelin cards ?
        boolean useCarCard = player.hasCarCard();                   // Does the player is using car cards ?
        List<KnowledgeElement> knowledgeElements = new ArrayList(); // list of used Knowledge elements
        List<ShovelCard> shovelCards = new ArrayList();             // list of ised shovel cards
        
        // We iterate over all used elements to get some informations and make more precise list
        for (UsableElement element : usedElements){
            
            if( element instanceof ZeppelinCard ){
                useZeppelin = true;
            }
            
            if( element instanceof KnowledgeElement ){
                knowledgeElements.add( (KnowledgeElement)element );
            }
            
            if( element instanceof ShovelCard ){
                shovelCards.add( (ShovelCard)element );
            }
        }
        
        // Do the main action 
        switch(actionPattern){
            
            case Player.ACTION_CHANGE_FOUR_CARDS:
                this._actionPlayerDoChangeFourCards( player, useZeppelin, useCarCard );
                break;
                
            case Player.ACTION_EXCAVATE:
                this._actionPlayerDoExcavateArea( player, areaToExcavate, knowledgeElements, nbWeeksForExcavation, useZeppelin, useCarCard, shovelCards);
                break;
                
            case Player.ACTION_ORGANIZE_EXPO:
                this._actionPlayerDoOrganizeExpo( player, expoCardToDo, useZeppelin, useCarCard );
                break;
                
            case Player.ACTION_PICK_CARD:
                this._actionPlayerDoPickCard( player, cardToPickUp, useZeppelin, useCarCard );
                break;
        }
        
        // Check all elements to eventually discard them
        for (UsableElement element : usedElements){
            
            // Case of card
            if( element instanceof DiscardableElement && element instanceof Card){
                if( ((DiscardableElement)element).isDiscardable() ){
                    this.discardingDeck.add( (Card)element );
                }
            }
        }
        
        // We increment the number of round this player is still playing
        player.setNbRoundStillPlaying( player.getNbRoundStillPlaying() + 1);
    }
    
    public void salut(){}
    /**
     * Check if the player is able to do the demanded action. Use player action Constant to provide an action key
     * @param actionPattern player constant (exemple: player.ACTION...)
     * @param player
     * @param areaToExcavate
     * @param indexOfCardToPickUp
     * @param expoCardToDo
     * @return 
     */
    public boolean isPlayerAbleToMakeRoundAction( int actionPattern, Player player, ExcavationArea areaToExcavate, Integer indexOfCardToPickUp, ExpoCard expoCardToDo ){
        switch(actionPattern){
            case Player.ACTION_CHANGE_FOUR_CARDS:
                return this._actionPlayerAbleToChangeFourCards( player );
            case Player.ACTION_EXCAVATE:
                return this._actionPlayerAbleToExcavateArea( player, areaToExcavate );
            case Player.ACTION_ORGANIZE_EXPO:
                return this._actionPlayerAbleToOrganizeExpo( player, expoCardToDo );
            case Player.ACTION_PICK_CARD:
                return this._actionPlayerAbleToPickCard(player , indexOfCardToPickUp);
        }
        throw new UnsupportedOperationException();
    }
    
    /**
     * Check if the playerToken has enough time to go in the asked place before the end of game
     * @param area
     * @param playerToken
     * @return 
     */
    public boolean hasEnoughTimeToGoInThisArea( Area area, PlayerToken playerToken ){
       int weekCost = playerToken.getPosition().getDistanceWeekCostTo( area.getName() ); // weekcost from current place to area
       return Board.hasEnoughTimeBeforeEndGame( playerToken.getTimeState(), weekCost, this.endGameDatePosition);
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
     * Check if the player is able to move inside the specified area
     * Conditions :
     *  - must have enough time to go there (move)
     *  - must have authorization to excavate in this area
     *  - must have at least 1 specific knowledge point about this area (dev, token)
     * 
     * @param player
     * @param playerToken 
     * @return  
     */
    private boolean _actionPlayerAbleToExcavateArea( Player player, ExcavationArea areaToExcavate ){
        if ( player.isAuthorizedToExcavateArea( areaToExcavate ) // 
                && this.hasEnoughTimeToGoInThisArea( areaToExcavate, player.getPlayerToken() )
                && (player.hasSpecificKnowledgeCardForThisExcavationArea( areaToExcavate.getName() ) || player.hasSpecificKnowledgeTokenForThisExcavationArea( areaToExcavate.getName() )) ){
            return true;
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
    private boolean _actionPlayerAbleToOrganizeExpo( Player player, ExpoCard expoCard ){
        List<PointToken> playerTokens = player.getSpecificTokens( PointToken.class );
        
        // iterate over all expo token to check if the user has one token 
        for (Token token : expoCard.getTokens()) {
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
                player.getPlayerToken().getPosition().getDistanceWeekCostTo( expoCard.getAreaName() ) + expoCard.getWeekCost(), 
                endGameDatePosition) // check if enought time to go there + week cost of the card
                ){
            return true;
        }
        return false;
    }
    
    /**
     * Check if the player is able to pick up the speciefied card
     * Condition:
     *  - must have enough time to go there (move + cost of card)
     * @return 
     */
    private boolean _actionPlayerAbleToPickCard( Player player, int indexOfCardToPickTup ){
        Card card = this.fourCurrentCards.get( indexOfCardToPickTup );
        if( Board.hasEnoughTimeBeforeEndGame( 
                    player.getPlayerToken().getTimeState(),
                    player.getPlayerToken().getPosition().getDistanceWeekCostTo( card.getAreaName() ) + card.getWeekCost(), 
                    endGameDatePosition) ){
            return true;
        }
        return false;
    }
    
    /**
     * Do a round action: change four current cards
     * <br/>- move the player to warsaw
     * <br/>- change the four current cards on boards
     * @param player 
     * @param useZeppelinCard 
     * @param useCarCard 
     */
    private void _actionPlayerDoChangeFourCards( Player player, boolean useZeppelinCard, boolean useCarCard ) {
        player.getPlayerToken().movePlayerToken( this.areas.get("warsaw"), useZeppelinCard, useCarCard);
        this.changeFourCurrentCards();
    }
    
    /**
     * Do a round action: excavate area
     * <br/>Effect:
     * <br/>- move the player to the area
     * <br/>- get all knowledge point available
     * <br/>- get the number of token the chronotime gives
     * <br/>- Pick the tokens and put in player's hand
     * <br/>- Add excavation cost to the player token
     * <br/><b>Use player.getTokensJustPickedUp() to know which tokens has been picked up</b>
     * @param player
     * @param areaToExcavate
     * @param knowledgePointElements 
     */
    private void _actionPlayerDoExcavateArea( Player player, ExcavationArea areaToExcavate, List<KnowledgeElement> usedKnowledgeElements, int nbWeeks, boolean useZeppelinCard, boolean useCarCard, List<ShovelCard> shovelCards  ) {
        // Moving process
        player.getPlayerToken().movePlayerToken( areaToExcavate , useZeppelinCard, useCarCard);
        
        // Get all the knowledge points the user want AND is able to use
        int nbKnowledge = player.getTotalAskedKnowledgePoint(areaToExcavate, usedKnowledgeElements);
        
        // Picking token process
        int nbTokenToPickUp = this.chronotime.getNbTokensToPickUp( nbKnowledge, nbWeeks); // nb tokens the player can Pick Up
        nbTokenToPickUp += ShovelCard.getTokensPointsWhenCombinated( shovelCards.size() ); // get supplementary tokens thanks to the used shovels
        
        player.getTokensJustPickedUp().clear(); // clear the previous round picked tokens
        for (int i = 0; i < nbTokenToPickUp; i++){
            Token pickedToken = areaToExcavate.getTokenList().get(0);
            // If the player pick a none blank token we add it and we remove it from the area token list
            if( ! (pickedToken instanceof BlankToken) ){
                player.getTokensJustPickedUp().add( pickedToken ); // add to player
                areaToExcavate.getTokenList().remove( pickedToken ); // remove from area
            }
        }
        throw new UnsupportedOperationException("not implemented yet");
    }
    
    /**
     * Do player round action: organize an exposition
     * <br/>Effect :
     * <br/>- move the player to expo area
     * <br/>- add weekcost from expocard to player
     * <br/>- put the expo in the player hand
     * @param expoCards 
     */
    private void _actionPlayerDoOrganizeExpo( Player player, ExpoCard expoCardToDo, boolean useZeppelinCard, boolean useCarCard ){
        player.getPlayerToken().movePlayerToken( this.areas.get(expoCardToDo.getAreaName()) , useZeppelinCard, useCarCard);
        player.getPlayerToken().addWeeks( expoCardToDo.getWeekCost() );
        player.getCards().add( expoCardToDo );
        this.getExpoCards().remove( expoCardToDo );
    }
    
    /**
     * Do Player round action pick up
     * <br/>Effect:
     * <br/>- move to the area written on the card
     * <br/>- pick the wanted card on the fourCards
     * <br/>- add weekcost from expocard to player
     * <br/>- add the card to the player hand
     * @param player 
     */
    private void _actionPlayerDoPickCard( Player player, Card cardToPickUp, boolean useZeppelinCard, boolean useCarCard ){
        player.getPlayerToken().movePlayerToken( this.getArea( cardToPickUp.getAreaName() ) , useZeppelinCard, useCarCard); // move
        Card pickedCard = this.pickCardOnBoard( this.fourCurrentCards.indexOf( cardToPickUp ) );
        player.getPlayerToken().addWeeks( pickedCard.getWeekCost() );
        player.getCards().add( pickedCard ); // update player hand
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
                    
                    // Set blank tokens
                    String[] blankTokens = ((String)entries.get("area." + areaName + ".emptyTokens" )).split("\\|");
                    nbTokens = blankTokens.length;
                    for (int i = 0; i < nbTokens; i++) {
                        set = blankTokens[i].split("\\:");
                        nb = Integer.parseInt(set[1]);
                        id = set[0];
                        for (int j = 0; j < nb; j++) {
                            ((ExcavationArea)newArea).addToken( new BlankToken(id, newArea.getName(), ((ExcavationArea)newArea).getCodeColor())); // assign empty point
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
                            ((ExcavationArea)newArea).addToken( new GeneralKnowledgeToken(id, newArea.getName(), ((ExcavationArea)newArea).getCodeColor(), 1 ) );
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
                            ((ExcavationArea)newArea).addToken( new SpecificKnowledgeToken(id, newArea.getName(), ((ExcavationArea)newArea).getCodeColor(), 1 ) );
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
            switch (type){
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
        this.fourCurrentCards.add( firstDeck.pick() ); // We Pick the four first cards from the main deck
        this.fourCurrentCards.add( firstDeck.pick() );
        this.fourCurrentCards.add( firstDeck.pick() );
        this.fourCurrentCards.add( firstDeck.pick() );
        
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
    
    public <T extends Area> HashMap<String, T> getAreas( Class<T> typeOfArea ) {
        HashMap<String, T> areasToReturn = new HashMap();
        for (Area area : this.areas.values()) {
            if( area.getClass() == typeOfArea ){
                areasToReturn.put( area.getName(), typeOfArea.cast( area ) );
            }
        }
        return areasToReturn;
    }

    public List<Card> getFourCurrentCards() {
        return fourCurrentCards;
    }

    public void setFourCurrentCards(List<Card> fourCurrentCards) {
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

    public List<Player> getPlayersWhoFinished() {
        return playersWhoFinished;
    }
    
    
    
}
