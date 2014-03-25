package com.miage.game;


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


    public Player(String name){
            this.name = name;
            this.points = 0;
            
            this.tokens = new HashMap<String, Integer>();
            this.competences = new HashMap<String, Integer>(); 
            this.playerKnowledges = new PlayerKnowledges();
            this.cards = new ArrayList<Card>();
            
            /*
             * Initialization of competences
             */
            
            this.competences.put("assistant", 0);
            this.competences.put("shovel", 0);
            this.competences.put("car", 0);
            this.competences.put("zeppelin", 0);
            this.competences.put("congress", 0);
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
    	
    	this.cards.add(board.pickCardOnBoard(index).downCastCard());
    	
    }
    
    
    
    public void addCompetencesPointsOrKnowledge(Card card){
    	
    	if(card instanceof AssistantCard){
    		this.competences.put("assistant", this.competences.get("assistant")+1);
    	
    	}
    	else if(card instanceof CarCard){
    		this.competences.put("car", this.competences.get("car")+1);
    		
    	}
    	else if(card instanceof CongressCard){
    		this.competences.put("congress", this.competences.get("congress")+1);
    		
    		switch(this.competences.get("congress")){
    			case 1 : 
    				this.competences.put("congress", 1);
    				break;
    			
    			case 2 : 
    				this.competences.put("congress", 3);
    				break;
    			
    			case 3 : 
    				this.competences.put("congress", 6);
    				break;
    			
    			case 4 : 
    				this.competences.put("congress", 10);
    				break;
    			
    			case 5 : 
    				this.competences.put("congress", 15);
    				break;
    			
    			case 6 : 
    				this.competences.put("congress", 21);
    				break;
    			
    			case 7 : 
    				this.competences.put("congress", 28);
    				break;
    				
    			default :
    				break;
    			
    			
    				
    		}
    		
    		
    	}
    	else if(card instanceof EthnologicalKnowledgeCard){
    		
    			EthnologicalKnowledgeCard ethnologicalKnowledgeCard = (EthnologicalKnowledgeCard) card;
    			this.playerKnowledges.addEthnologicalKnowledges(ethnologicalKnowledgeCard.getCodeColor(), ethnologicalKnowledgeCard.getValue());
    		
    	}
    	else if(card instanceof ExcavationAuthorizationCard){
    		ExcavationAuthorizationCard cardReturned = (ExcavationAuthorizationCard) card;
    		
    	}
    	else if(card instanceof ExpoCard){
    		ExpoCard cardReturned = (ExpoCard) card;
    		
    	}
    	else if(card instanceof GeneralKnowledgeCard){
    		GeneralKnowledgeCard cardReturned = (GeneralKnowledgeCard) card;
    		
    	}
    	else if(card instanceof ShovelCard){
    		ShovelCard cardReturned = (ShovelCard) card;
    		
    	}
    	else if(card instanceof SpecificKnowledgeCard){
    		SpecificKnowledgeCard cardReturned = (SpecificKnowledgeCard) card;
    		
    	}
    	else{
    		ZeppelinCard cardReturned = (ZeppelinCard) card;
    		
    	}
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



}
