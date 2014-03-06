package Cards;
import java.util.ArrayList;
import java.util.List;

import Areas.Area;
import Tokens.PointToken;


public class ExpoCard extends Card {
	
	private boolean big;
	private int value;
	private List<PointToken> tokens;
	
	public ExpoCard(Area a, boolean b) {
		
		super(a);
		this.big = b;
		this.tokens = new ArrayList<PointToken>();
		
		if(b){
			this.setCost(4);
			this.value = 5;
		}
		else{
			this.setCost(3);
			this.value = 4;
		}
		
		
	}
	
	public int getValue(){
		return value;
	}
	
	
	public String toString(){
		return "Exposition "+super.toString();
	}
	
	
	

}
