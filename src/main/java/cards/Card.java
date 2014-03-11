package cards;
import areas.Area;


public abstract class Card {
	
	private String name;
	private Area area;
	private int cost = 0;
	
	public Card(String name, Area area, int cost){
		this.name = name;
		this.area = area;
		this.cost = cost;
	}
	
	

	public String getName() {
		return name;
	}



	public Area getArea() {
		return area;
	}

	public int getCost() {
		return cost;
	}
	
	public void setCost(int c){
		this.cost = c;
	}
	
	public String toString(){
		return "card costing "+this.getCost()+" weeks, destination : "+this.getArea(); 
	}

}
