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
import com.miage.tokens.GeneralKnowledgeToken;
import com.miage.tokens.SpecificKnowledgeToken;
import com.miage.tokens.Token;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;



public class Player implements Serializable {
	
    private final static Logger LOGGER = LogManager.getLogger(Board.class.getName());
    
    private final String name;
    
    private int points;
    
    public final static int ACTION_CHANGE_FOUR_CARDS = 0;
    public final static int ACTION_EXCAVATE = 1;
    public final static int ACTION_ORGANIZE_EXPO = 2;
    public final static int ACTION_PICK_CARD = 3;

    private final PlayerToken playerToken;
    
    /**
     * 
     */
    private ArrayList<Card> cards;


    private ArrayList<Token> tokens; 

    /*
     * Structure stocking competences :
     *  "car"
     *  "zeppelin"
     *  "assistant"
     *  "shovel"
     */
    private Map<String, Integer> competences;
    
    private PlayerKnowledges playerKnowledges;
    
    
    /*
     * Contains the name of areas already excavate
     */
    private ArrayList<String> areasAlreadyExcavate; 

    /**
     * Define for how many round the player is playing (because we can play again after played depending of the position)
     * Must count the current round
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
     * Conditions:
     *  - has a specific knowledge about this area
     *  - has a specific knowledge token about this area
     *  - is authorized to excavate
     * @author maxime
     * @param excavationArea
     * @return 
     */
    public boolean isAuthorizedToExcavateArea( Area excavationArea ){
        boolean hasExcavationAuthorization = ! this.getSpecificCards( ExcavationAuthorizationCard.class ).isEmpty();
        if( (! this.hasAlreadyExcavateArea( excavationArea.getName() ) || hasExcavationAuthorization ) // authorized
                && ( this.hasSpecificKnowledgeCardForThisExcavationArea( excavationArea.getName() )  // enough knowledge
                     || this.hasSpecificKnowledgeTokenForThisExcavationArea( excavationArea.getName() ) 
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
    public boolean isAuthorizedToExcavateOneArea( Collection<Area> excavationAreas ){
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
    public <T extends Card> Set<T> getSpecificCards( Class<T> typeOfCard ){
        Set cardsToReturn = new HashSet<>();
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
    public <T extends Token> Set<T> getSpecificTokens( Class<T> typeOfToken ){
        Set tokensToReturn = new HashSet<>();
        for (Token token : this.tokens) {
            if( token.getClass() == typeOfToken ){
                tokensToReturn.add( typeOfToken.cast( token ) );
            }
        }
        return tokensToReturn;
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
     * @author Gael
     * 
     * pick a card on the board
     * @deprecated 
     * @param board board of the game
     * @param index index of the table which corresponds to the card
     */
    public void pickCard(Board board, int index){
    	
    	Card cardPicked = board.pickCardOnBoard(index);
    	this.cards.add(cardPicked);
    	updateCompetencesPointsOrKnowledge(cardPicked, 1);
    	board.getCurrentPlayerToken().addWeeksPlayerToken(cardPicked);
    	
    }
    
    /**
     * 
     *  add competences points depending on the card in param
     * 
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
     * 
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
     */
    public void useCard(Card card, Deck sideDeck){


            if(card.isDiscardable()){
                    this.cards.remove(card);
                    card.discardCard(sideDeck);
                    this.updateCompetencesPointsOrKnowledge(card, -1);
            }
    }
        
    /**
     * 
     * Return the total of knowledge points for excavate in the area
     * 
     * @param area 
     * @param ethnologicalKnowledge
     * @return the number of knowledge points
     */
    public int totalKnowledgePoints(Area area, boolean ethnologicalKnowledge){

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

    public int getPoints(){
        return points;
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
	
	
    
    
    
    



}
