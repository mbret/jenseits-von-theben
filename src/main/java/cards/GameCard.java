package cards;

import areas.Area;

public class GameCard extends Card{

	public GameCard(String name, Area area, int cost) {
		super(name, area, cost);
		
	}
	
	public String toString(){
		
		return this.getName()+super.toString();
		
	}

}
