package Cards;
import Areas.Area;


public class CarCard extends Card {

	public CarCard(Area a) {
		super(a);
		this.setCost(1);
	}
	
	public String toString(){
		return "Car "+super.toString();
	}

}
