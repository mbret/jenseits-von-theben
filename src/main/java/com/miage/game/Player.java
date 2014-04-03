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
import com.miage.cards.ZeppelinCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class Player {
	
    private String name;
    private int points;

    /**
     * 
     */
    private ArrayList<Card> cards;


    private Map<String, Integer> tokens; 

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


    public Player(String name){
            this.name = name;
            this.points = 0;
            
            this.tokens = new HashMap<String, Integer>();
            this.competences = new HashMap<String, Integer>(); 
            this.playerKnowledges = new PlayerKnowledges();
            this.cards = new ArrayList<Card>();
            this.areasAlreadyExcavate = new ArrayList<String>();
            
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
     * @author Gael
     * 
     * Return true if the player has already excavate the area named nameOfArea, else return false
     * 
     * @param nameOfArea
     * @return
     */
    public boolean hasAlreadyExcavateArea(String nameOfArea){
    	
    	if(this.areasAlreadyExcavate.contains(nameOfArea))
    		return true;
    	else
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
     * @author Gael
     * 
     * pick a card on the board
     * 
     * @param board board of the game
     * @param index index of the table which corresponds to the card
     */
    public void pickCard(Board board, int index){
    	
    	Card cardPicked = board.pickCardOnBoard(index).downCastCard();
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
     * 	add competence points using the card in parameters
     * @author Gael
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
         * @author david
         * return a boolean means if the player can excavate in this area
         */
        public boolean canExcavate(Area a){
            boolean allowed = false;
            if(a instanceof ExcavationArea){
                if(this.hasAlreadyExcavateArea(a.getName())){
                        if(this.competences.get("excavationAuthorization")>0){
                             if(this.playerKnowledges.getSpecificKnowledges().get(a.getName())>0){
                                 allowed = true;
                            }
                        }
                }else{
                    if(this.playerKnowledges.getSpecificKnowledges().get(a.getName())>0){
                        allowed = true;
                    }
                }
            }
            return allowed;
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
        		numberOfSpecificKnowledgePoints = this.playerKnowledges.getEthnologicalKnowledges().get(area.getName());
        	}
        	
        	if(numberOfSpecificKnowledgePoints > numberOfGeneralKnowledgePoints)
        		numberOfPoints = numberOfGeneralKnowledgePoints*2;
        	else
        		numberOfPoints = numberOfGeneralKnowledgePoints + numberOfSpecificKnowledgePoints;
        		
        	return numberOfPoints;
        	
        }
        
        
       /**
        * 
        * method which returns the number of tokens that a player can pick in a Area
        * 
        * @param area where excavate
        * @param ethnologicKnowledge if the player use his ethnologic knowledge
        * @param uniqueShovel if the player use an unique shovel
        * @param uniqueAssistant if the player use an unique assistant
        * @return
        */
        public int numberOfTokensPickable(Area area, boolean ethnologicKnowledge, boolean uniqueShovel, boolean uniqueAssistant){
        	
        	int numberOfTokens = 0;
        	
        	
        	
        	
        }
    
    
    


    
    
    //************************************ GETTERS & SETTERS ****************************************************

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

	public Map<String, Integer> getCompetences() {
		return competences;
	}


	public PlayerKnowledges getPlayerKnowledges() {
		return playerKnowledges;
	}
    
    
    
    



}
