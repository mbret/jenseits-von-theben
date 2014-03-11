package cards;
import areas.Area;


public class CongressCard extends Card {

	public CongressCard(Area area) {
		super(area);
		this.setCost(2);
	}
	
	public String toString(){
		return "Congress "+super.toString();
	}

}
