package com.miage.game;


import com.miage.cards.Card;
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


    private Map<String, Integer> competences;
    private PlayerKnowledges playerKnowledges;


    public Player(String name){
            this.name = name;
            this.points = 0;
            this.tokens = new HashMap<String, Integer>();
            this.competences = new HashMap<String, Integer>();
            this.playerKnowledges = new PlayerKnowledges();
            this.cards = new ArrayList<Card>();
    }
    
    /**
     * The player pick a card in the deck, then the card is added in the Card List
     * @author Gael
     * @param deck where player will pick a card
     */
    public void pickCard(Deck deck){
    	
    	this.cards.add(deck.pick().downCastCard());
    	
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
