package com.miage.game;


import com.miage.areas.Area;
import com.miage.areas.ExcavationArea;
import com.miage.cards.AssistantCard;
import com.miage.cards.CarCard;
import com.miage.cards.Card;
import com.miage.cards.CongressCard;
import com.miage.cards.EthnologicalKnowledgeCard;
import com.miage.cards.ExcavationAuthorizationCard;
import com.miage.cards.ExpoCard;
import com.miage.cards.GeneralKnowledgeCard;
import com.miage.cards.ShovelCard;
import com.miage.cards.SpecificKnowledgeCard;
import com.miage.interfaces.ActivableElement;
import com.miage.interfaces.ActiveElement;
import com.miage.interfaces.KnowledgeElement;
import com.miage.interfaces.UsableElement;
import com.miage.tokens.GeneralKnowledgeToken;
import com.miage.tokens.SpecificKnowledgeToken;
import com.miage.tokens.Token;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;



public class Player implements Serializable {
	
    private final static Logger LOGGER = LogManager.getLogger(Board.class.getName());
    
    /**
     * Name of player
     */
    private final String name;
    
    /**
     * Number of point the player has
     * <br/>Some of these points need to be calculated (they are not automatically added)
     */
    private int points;
    
    /**
     * determine one of the four action a player can do
     * <br/>See Action method in Board for how to use
     */
    public final static int ACTION_CHANGE_FOUR_CARDS = 0;
    public final static int ACTION_EXCAVATE = 1;
    public final static int ACTION_ORGANIZE_EXPO = 2;
    public final static int ACTION_PICK_CARD = 3;

    
    private final PlayerToken playerToken;
    
    /**
     * Player's cards
     */
    private ArrayList<Card> cards;

    /**
     * Player's token
     */
    private ArrayList<Token> tokens; 

    /**
     * Structure stocking competences :
     *  "car"
     *  "zeppelin"
     *  "assistant"
     *  "shovel"
     * @deprecated 
     */
    private Map<String, Integer> competences;
    
    /**
     * @deprecated 
     */
    private PlayerKnowledges playerKnowledges;
    
    
    /*
     * Contains the name of areas already excavate
     */
    private ArrayList<String> areasAlreadyExcavate; 

    /**
     * Define for how many round the player is playing (because we can play again after played depending of the position)
     * <br/>Must count the current round as well
     */
    private int nbRoundStillPlaying;
    
    public Player(String name, PlayerToken playerToken){
            this.name = name;
            this.points = 0;
            this.playerToken = playerToken;
            this.tokens = new ArrayList();
            this.competences = new HashMap(); 
            this.playerKnowledges = new PlayerKnowledges();
            this.cards = new ArrayList();
            this.areasAlreadyExcavate = new ArrayList();
            this.nbRoundStillPlaying = 0;
            
            /*
             * Initialization of competences
             */
            
            this.competences.put("assistant", 0);
            this.competences.put("shovel", 0);
            this.competences.put("car", 0);
            this.competences.put("zeppelin", 0);
            this.competences.put("congress", 0);
            this.competences.put("excavationAuthorization", 0);
    }
    
    /**
     * @deprecated 
     * @param name 
     */
    public Player(String name){
        this(name, new PlayerToken("color"));
    }
    
    
    
    
    /***********************************************************************************************
     *
     *                                  Public Methods
     * 
     ***********************************************************************************************/
    
        
    /**
     * @author Gael
     * 
     * Return true if the player has already excavate the area named nameOfArea, else return false
     * 
     * @param nameOfArea
     * @return
     */
    public boolean hasAlreadyExcavateArea(String nameOfArea){
        return this.areasAlreadyExcavate.contains(nameOfArea);
    }
    
    /**
     * Check if the player is authorized to excavated the provided area
     * <br/>Conditions:
     * <br/>- has a specific knowledge about this area
     * <br/>- has a specific knowledge token about this area
     * <br/>- is authorized to excavate
     * @author maxime
     * @param excavationArea
     * @return boolean
     */
    public boolean isAuthorizedToExcavateArea( Area excavationArea ){
        
        boolean hasExcavationAuthorization = ! this.getSpecificCards( ExcavationAuthorizationCard.class ).isEmpty(); // check authorizations
        
        if( (! this.hasAlreadyExcavateArea( excavationArea.getName() ) || hasExcavationAuthorization ) // authorized ro unexcavated yet
                && ( this.hasSpecificKnowledgeCardForThisExcavationArea( excavationArea.getName() )  // enough knowledge
                     || this.hasSpecificKnowledgeTokenForThisExcavationArea( excavationArea.getName() ) // enough tokens
                ) 
          ){ 
            return true;
        }
        return false;
    }
    
    /**
     * Check if the user is authorized to excavate at least one area (any of them)
     * Conditions:
     *  - is authorized to excavate one area
     *  - has specific knowledge for the area selected
     *  - has a specific knowledge token about this area
     * @author maxime
     * @param excavationAreas (we need this set to avoid dependencies with any extern information)
     * @return 
     */
    public boolean isAuthorizedToExcavateOneArea( Collection<ExcavationArea> excavationAreas ){
        boolean hasExcavationAuthorization = ! this.getSpecificCards( ExcavationAuthorizationCard.class ).isEmpty();
        // we iterate and check all area (we break the loop when we find the first area which is okay to excavate)
        for (Area area : excavationAreas ) {
            if( area instanceof ExcavationArea ){
               if( (! this.hasAlreadyExcavateArea( area.getName() ) || hasExcavationAuthorization ) // authorized
                    && ( this.hasSpecificKnowledgeCardForThisExcavationArea( area.getName() ) 
                         || this.hasSpecificKnowledgeTokenForThisExcavationArea( area.getName() ) ) ){ // enough knowledge
                    return true;
                } 
            }
        }
        return false;
    }
    
    public ArrayList<ActivableElement> getAllActivableElements(){
        ArrayList<ActivableElement> allUsableElms = new ArrayList();
        for (Card card : this.getCards()) {
            if( card instanceof ActivableElement) allUsableElms.add( (ActivableElement)card );
        }
        for (Token token : this.getTokens()) {
            if( token instanceof ActivableElement) allUsableElms.add( (ActivableElement)token );
        }
        return allUsableElms;
    }
    
    public ArrayList<UsableElement> getAllUsableElements(){
        ArrayList<UsableElement> allUsableElms = new ArrayList();
        for (Card card : this.getCards()) {
            if( card instanceof UsableElement) allUsableElms.add( (UsableElement)card );
        }
        for (Token token : this.getTokens()) {
            if( token instanceof UsableElement) allUsableElms.add( (UsableElement)token );
        }
        return allUsableElms;
    }
    
    public ArrayList<ActiveElement> getAllActiveElements(){
        ArrayList<ActiveElement> allUsableElms = new ArrayList();
        for (Card card : this.getCards()) {
            if( card instanceof ActiveElement) allUsableElms.add( (ActiveElement)card );
        }
        for (Token token : this.getTokens()) {
            if( token instanceof ActiveElement) allUsableElms.add( (ActiveElement)token );
        }
        return allUsableElms;
    }
    
    /**
     * @author Gael
     * 
     * add the area named nameOfArea in the list of areas already excavate
     * 
     * @param nameOfArea
     */
    public void addAreaAlreadyExcavate(String nameOfArea){
    	this.areasAlreadyExcavate.add(nameOfArea);
    }
    
    /**
     * Check if the player has the given type of card inside his hand
     * @author maxime
     * @param <T>
     * @param typeOfCard
     * @return 
     */
    public <T extends Card> boolean hasSpecificCard( Class<T> typeOfCard ){
        return ! this.getSpecificCards(typeOfCard).isEmpty();
    }
    
    /**
     * Retrieve the asked type of cards
     * @author
     * @param <T>
     * @param typeOfCard
     * @return 
     */
    public <T extends Card> List<T> getSpecificCards( Class<T> typeOfCard ){
        List cardsToReturn = new ArrayList();
        for (Card card : this.cards) {
            if( card.getClass() == typeOfCard ){
                cardsToReturn.add( typeOfCard.cast( card ) );
            }
        }
        return cardsToReturn;
    }
    
    /**
     * Retrieve the asked type of tokens 
     * @author
     * @param <T>
     * @param typeOfToken
     * @return 
     */
    public <T extends Token> List<T> getSpecificTokens( Class<T> typeOfToken ){
        List tokensToReturn = new ArrayList();
        for (Token token : this.tokens) {
            if( token.getClass() == typeOfToken ){
                tokensToReturn.add( typeOfToken.cast( token ) );
            }
        }
        return tokensToReturn;
    }
    
    public <T extends Card> T getFirstOccurOfSpecificCard( Class<T> typeOfCard ){
        List<T> l = this.getSpecificCards( typeOfCard );
        if( l.size() > 0) return l.get(0);
        return null;
    }
    
    /**
     * Check if the player has at least one SpecificKnowledgeCard about the given area
     * @author maxime
     * @param areaName
     * @return 
     */
    public boolean hasSpecificKnowledgeCardForThisExcavationArea( String areaName ){
        for (SpecificKnowledgeCard card : this.getSpecificCards( SpecificKnowledgeCard.class )) {
            if( ((SpecificKnowledgeCard)card).getExcavationAreaName().equals( areaName )  ){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check if the player has at least one SpecificKnowledgeCard about the given area or any generalKnowledgeCard 
     * @author maxime
     * @param areaName
     * @return 
     */
    public boolean hasKnowledgeCardForThisExcavationArea( String areaName ){
        for (Card card : this.cards) {
            if( card instanceof GeneralKnowledgeCard){
                return true;
            }
            if( card instanceof SpecificKnowledgeCard){
                if( ((SpecificKnowledgeCard)card).getExcavationAreaName().equals( areaName )  ){
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Check if the player has at least one SpecificKnowledgeToken about the given area
     * @param areaName
     * @return 
     */
    public boolean hasSpecificKnowledgeTokenForThisExcavationArea( String areaName ){
        for (Token token : this.getSpecificTokens( SpecificKnowledgeToken.class )) {
            if( ((SpecificKnowledgeToken)token).getAreaName().equals( areaName )  ){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check if the player has at least one SpecificKnowledgeToken about the given area or any generalKnowledgeToken
     * @author maxime
     * @param areaName
     * @return 
     */
    public boolean hasKnowledgeTokenForThisExcavationArea( String areaName ){
        for (Token token : this.tokens) {
            if( token instanceof GeneralKnowledgeToken){
                return true;
            }
            if( token instanceof SpecificKnowledgeToken){
                if( ((SpecificKnowledgeToken)token).getAreaName().equals( areaName )  ){
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 
     *  add competences points depending on the card in param
     * @deprecated 
     * @author Gael
     * @param card
     */
    public void updateCompetencesPointsOrKnowledge(Card card, int plusOrMinus){
    	
    	if(card instanceof AssistantCard){
    		this.competences.put("assistant", this.competences.get("assistant")+1*plusOrMinus);
    	
    	}
    	else if(card instanceof CarCard){
    		this.competences.put("car", this.competences.get("car")+1);
    		
    	}
    	else if(card instanceof CongressCard){
    		this.competences.put("congress", this.competences.get("congress")+1);
    		
    		switch(this.competences.get("congress")){
    			case 1 : 
    				this.points += 1;
    				break;
    			
    			case 2 : 
    				this.points += 2;
    				break;
    			
    			case 3 : 
    				this.points += 3;
    				break;
    			
    			case 4 : 
    				this.points += 4;
    				break;
    			
    			case 5 : 
    				this.points += 5;
    				break;
    			
    			case 6 : 
    				this.points += 6;
    				break;
    			
    			case 7 : 
    				this.points += 7;
    				break;
    				
    			default :
    				break;
    			
    			
    				
    		}
    		
    		
    	}
    	else if(card instanceof EthnologicalKnowledgeCard){
    		
    			/*
    			 * Add or subsract the value of cards into the ethnological knowledge color corresponding 
    			 */
    		
    			EthnologicalKnowledgeCard ethnologicalKnowledgeCard = (EthnologicalKnowledgeCard) card;
    			this.playerKnowledges.addEthnologicalKnowledges(ethnologicalKnowledgeCard.getExcavationAreaName(), 
    			ethnologicalKnowledgeCard.getValue()*plusOrMinus);
    		
    	}
    	else if(card instanceof ExcavationAuthorizationCard){
    		
    		this.competences.put("excavationAuthorization", this.competences.get("excavationAuthorization")+1*plusOrMinus);
    		
    	}
    	else if(card instanceof ExpoCard){
    		
    		ExpoCard expoCard = (ExpoCard) card;
    		this.points += expoCard.getValue();
    		
    		
    	}
    	else if(card instanceof GeneralKnowledgeCard){
    		
    		GeneralKnowledgeCard generalKnowledgeCard = (GeneralKnowledgeCard) card;
    		this.playerKnowledges.addGeneralKnowledges(generalKnowledgeCard.getValue());
    		
    		
    	}
    	else if(card instanceof ShovelCard){
    		
    		this.competences.put("shovel", this.competences.get("shovel")+1*plusOrMinus);
    		
    	}
    	else if(card instanceof SpecificKnowledgeCard){
    		
    		SpecificKnowledgeCard specificKnowledgeCard = (SpecificKnowledgeCard) card;
    		this.playerKnowledges.addSpecificKnowledges(specificKnowledgeCard.getExcavationAreaName(), 
    		specificKnowledgeCard.getValue());
    		
    	}
    	else{
    		this.competences.put("zeppelin", this.competences.get("zeppelin")+1*plusOrMinus);
    		
    	}
    }
    
    /**
     * Add competence points using the card in parameters
     * @author Gael
     * @deprecated 
     * @param card
     */
    public void addCompetencesPointsOrKnowledge(Card card){
    	updateCompetencesPointsOrKnowledge(card, 1);
    }
    
    /**
     * 
     * 	remove competence points using the card in parameters
     * @author Gael
     * @param card
     * @deprecated 
     */
    public void removeCompetencesPointsOrKnowledge(Card card){
    	updateCompetencesPointsOrKnowledge(card, -1);
    }
    
    /**
     * When a player use a zeppelin, unique assistant or unique shovel or ethnological knowledge, the card is discard
     * 
     * @author Gael
     * @param card
     * @param sideDeck
     * @deprecated Do this operation in Board
     */
    public void useCard(Card card, Deck sideDeck){


            if(card.isDiscardable()){
                    this.cards.remove(card);
                    card.discardCard(sideDeck);
                    this.updateCompetencesPointsOrKnowledge(card, -1);
            }
    }
    
    /**
     * Return the maximum of available knowledge point a player can use to excavate the given area
     *  - count 
     *  - can't use more general knowledge than specific knowledge for this area
     * @param area 
     * @param ethnologicalKnowledge
     * @return the number of knowledge points
     * @deprecated 
     */
    public int totalAvailableKnowledgePoints(Area area, boolean ethnologicalKnowledge){

            int numberOfPoints = 0;

            int numberOfGeneralKnowledgePoints = this.playerKnowledges.getGeneralKnowledge();
            int numberOfSpecificKnowledgePoints = this.playerKnowledges.getSpecificKnowledges().get(area.getName());

            if(ethnologicalKnowledge){
                    numberOfSpecificKnowledgePoints += this.playerKnowledges.getEthnologicalKnowledges().get(area.getName());
            }

            if(numberOfGeneralKnowledgePoints > numberOfSpecificKnowledgePoints)
                    numberOfPoints = numberOfSpecificKnowledgePoints*2;
            else
                    numberOfPoints = numberOfGeneralKnowledgePoints + numberOfSpecificKnowledgePoints;

            return numberOfPoints;
    }
    
    /**
     * Return the maximum of knowledge point the player can use to excavate this area.
     * <br/>Effect:
     * <br/>- get all knowledge point from specific knowledge card and tokens ( intern to player )
     * <br/>- get all knowledge point from used elements
     * @param areaToExcavate
     * @param usedKnowledgeElements List<KnowledgeElement>. List of all knowledge and activable elements the player is using
     * @return 
     */
    public int getTotalAskedKnowledgePoint( Area areaToExcavate, List<KnowledgeElement> usedKnowledgeElements){
        
        int nbAssistantCards = 0;
        int pointsForExcavation = 0;
        
        for (KnowledgeElement element : usedKnowledgeElements){
            
            if( element instanceof AssistantCard ){
                nbAssistantCards++;
//                usedKnowledgeElements.remove( element );
            }
            
        }
        
        // GET POINT FROM ACTIVE ELEMENTS
        for (ActiveElement element : this.getAllActiveElements()) {
            
            if( element instanceof KnowledgeElement ){
                
                if(element instanceof Card){
                    if( ((SpecificKnowledgeCard)element).getExcavationAreaName().equals( areaToExcavate.getName() )){
                        pointsForExcavation += ((KnowledgeElement)element).getKnowledgePoints();
                    }
                }
                else if(element instanceof Token){
                    if( ((SpecificKnowledgeToken)element).getAreaName().equals( areaToExcavate.getName() )){
                        pointsForExcavation += ((KnowledgeElement)element).getKnowledgePoints();
                    }
                }
            }
        }
        
        // GET POINT FROM USED ELEMENTS
        for (KnowledgeElement element : usedKnowledgeElements){
            
            // Get general knowledge card
            if( element instanceof GeneralKnowledgeCard){

            }
            // Get ethnological knowledge token
            if( element instanceof EthnologicalKnowledgeCard){

            }
            // ...
        }
        
        // Get all assistant points
        if(nbAssistantCards > 0){
            pointsForExcavation += AssistantCard.getKnowLedgePointsWhenCombinated( nbAssistantCards );
        }
        
//        throw new UnsupportedOperationException("not implemented yet");
        return pointsForExcavation;
    }
         
    /**
     * Check if a player has a car card in his hand
     * @return 
     */
    public boolean hasCarCard(){
        return ! this.getSpecificCards( CarCard.class ).isEmpty();
    }
    
//    public void addPoints(int points){
//        this.points += points;
//    }
    
    /**
     * Calculate and set the point of each player
     * <br/>Effect:
     * <br/>- get setted actual point
     * <br/>- add point from congress 
     * <br/>- add point from PointToken
     * <br/>- add point from expo
     */
    public int calculatePoint(){
        throw new UnsupportedOperationException("not implemented yet");
    }
    
    
    /***********************************************************************************************
     *
     *                                  Private Methods
     * 
     ***********************************************************************************************/
    
    
    
    
    
    
    /***********************************************************************************************
     *
     *                                  Getter & Setter
     * 
     ***********************************************************************************************/

    public String getName() {
        return name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    /**
     * @deprecated 
     * @return 
     */
    public Map<String, Integer> getCompetences() {
        return competences;
    }

    public PlayerKnowledges getPlayerKnowledges() {
        return playerKnowledges;
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public PlayerToken getPlayerToken() {
        return playerToken;
    }

    public int getNbRoundStillPlaying() {
        return nbRoundStillPlaying;
    }

    public void setNbRoundStillPlaying(int nbRoundStillPlaying) {
        this.nbRoundStillPlaying = nbRoundStillPlaying;
    }

    public int getPoints() {
        return points;
    }
	
    
    
    
    
    



}
