package Cards;
import Areas.Area;


public class CongressCard extends Card {

	public CongressCard(Area a) {
		super(a);
		this.setCost(2);
	}
	
	public String toString(){
		return "Congress "+super.toString();
	}

}
