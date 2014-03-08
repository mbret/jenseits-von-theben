package cards;
import java.util.ArrayList;
import java.util.List;


import tokens.Token;
import areas.Area;


public class ExpoCard extends Card {
	
	private boolean bigExpo;
	private int value;
	private List<Token> tokens;
	
	public ExpoCard(String name, Area area,int cost,  boolean bigExpo) {
		
		super(name, area, cost);
		this.bigExpo = bigExpo;
		this.tokens = new ArrayList<Token>();
				
		
	}
	
	public int getValue(){
		return value;
	}
	
	public boolean getBigExpo(){
		return bigExpo;
	}
	
	
	public String toString(){
		return "Exposition "+super.toString();
	}
	
	
	

}
