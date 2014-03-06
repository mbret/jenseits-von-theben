package Cards;
import Areas.Area;
import Interfaces.Underplayable;


public class ShovelCard extends Card implements Underplayable{

	public ShovelCard(Area a) {
		super(a);
		this.setCost(3);
		
	}
	
	public String toString(){
		return "Shovel "+super.toString();
	}

}
