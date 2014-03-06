package Game;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Cards.Card;
import Tokens.PointToken;

public class Player {
	
	private String name;
	
	private Map<String, PlayerKnowledges> specialKnowledges; 
	private List<PointToken> tokens;
	private List<Card> cards;
	
	private int generalKnowledgePoints;
	
	
	public Player(String n){
		this.name = n;
		
		this.specialKnowledges = new HashMap<String, PlayerKnowledges>();
		this.tokens = new ArrayList<PointToken>();
		this.cards = new ArrayList<Card>();
		this.generalKnowledgePoints = 0;
	}


	


	public int getGeneralKnowledgePoints() {
		return generalKnowledgePoints;
	}


	public void setGeneralKnowledgePoints(int generalKnowledgePoints) {
		this.generalKnowledgePoints = generalKnowledgePoints;
	}


	public String getName() {
		return name;
	}


	


	public Map<String, PlayerKnowledges> getSpecialKnowledges() {
		return specialKnowledges;
	}


	public List<PointToken> getTokens() {
		return tokens;
	}


	public List<Card> getCards() {
		return cards;
	}
	
	/**
	 * return the number of specific knowledge points of the specified color
	 * @param color Color of the concerned area
	 * @return the number of specific knowledge points
	 */
	public int getSpecificKnowledge(String color){
		
		int points = 0;
		
		if(this.specialKnowledges.containsKey(color))
			points = this.getSpecialKnowledges().get(color).getSpecificKnowledge();
		
		return points;
		
	}
	
	/**
	 * return the number of ethnological knowledge points of the specified color
	 * @param color Color of the concerned area
	 * @return the number of ethnological knowledge points
	 */
	public int getEthnologicalKnowledge(String color){
		
		int points = 0;
		
		if(this.specialKnowledges.containsKey(color))
			points = this.getSpecialKnowledges().get(color).getEthnologicalKnowledge();
		
		return points;
	}
	
	

}
