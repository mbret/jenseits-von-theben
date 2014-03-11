package cards;
import interfaces.Underplayable;
import areas.Area;


public class ZeppelinCard extends Card implements Underplayable {

	public ZeppelinCard(Area area) {
		super(area);
		this.setCost(1);
		
	}
	
	public String toString(){
		return "Zeppelin "+super.toString();
	}

}
