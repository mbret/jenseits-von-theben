package Cards;
import Areas.Area;


public abstract class Card {
	
	private Area area;
	private int cost = 0;
	
	public Card(Area a){
		this.area = a;

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
