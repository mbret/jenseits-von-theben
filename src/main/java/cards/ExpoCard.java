package cards;
import java.util.ArrayList;
import java.util.List;

import tokens.PointToken;
import areas.Area;


public class ExpoCard extends Card {
	
	private boolean bigExpo;
	private int value;
	private List<PointToken> tokens;
	
	public ExpoCard(Area area, boolean bigExpo) {
		
		super(area);
		this.bigExpo = bigExpo;
		this.tokens = new ArrayList<PointToken>();
		
		if(bigExpo){
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
