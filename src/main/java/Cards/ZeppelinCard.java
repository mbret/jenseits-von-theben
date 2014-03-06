package Cards;
import Areas.Area;
import Interfaces.Underplayable;


public class ZeppelinCard extends Card implements Underplayable {

	public ZeppelinCard(Area a) {
		super(a);
		this.setCost(1);
		
	}
	
	public String toString(){
		return "Zeppelin "+super.toString();
	}

}
