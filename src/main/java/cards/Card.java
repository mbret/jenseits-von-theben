package cards;
import areas.Area;


public abstract class Card {
	
	private Area area;
	private int cost = 0;
	
	public Card(Area area){
		this.area = area;

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
