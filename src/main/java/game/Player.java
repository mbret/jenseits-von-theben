package game;


import cards.Card;
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
