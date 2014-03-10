package cards;

import areas.Area;


public class KnowledgeCard extends Card{
	
	private int value;
	private String color;

	public KnowledgeCard(String name, Area area, int cost, int value, String color) {
		super(name,area,cost);
		this.value = value;
		this.color = color;
		
		
	}

	public int getValue() {
		return value;
	}
	
	public String getColor(){
		return color;
	}
	
	public String toString(){
		return "Knowledge "+ super.toString()+" and worthing "+this.getValue()+" points coloured "+this.getColor();
	}

}
