package game;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tokens.Token;

public class Player {
	
	private String name;
	private int points;
	
	private Map<String, Integer> tokens; 
	private Map<String, Integer> competences;
	private PlayerKnowledges playerKnowledges;
	
	
	public Player(String name){
		this.name = name;
		this.points = 0;
		this.tokens = new HashMap<String, Integer>();
		this.competences = new HashMap<String, Integer>();
		this.playerKnowledges = new PlayerKnowledges();
	}



	public String getName() {
		return name;
	}
	
	public int getPoints(){
		return points;
	}

	

	
	

}
