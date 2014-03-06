package cards;
import areas.Area;


public class CarCard extends Card {

	public CarCard(Area area) {
		super(area);
		this.setCost(1);
	}
	
	public String toString(){
		return "Car "+super.toString();
	}

}
