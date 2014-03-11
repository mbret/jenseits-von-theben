package cards;
import interfaces.Underplayable;
import areas.Area;


public class ShovelCard extends Card implements Underplayable{

	public ShovelCard(Area area) {
		super(area);
		this.setCost(3);
		
	}
	
	public String toString(){
		return "Shovel "+super.toString();
	}

}
