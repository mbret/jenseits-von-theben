package cards;
import interfaces.Underplayable;
import areas.Area;



public class AssistantCard extends Card implements Underplayable{

	public AssistantCard(Area area) {
		super(area);
		this.setCost(2);
		
	}
	
	public String toString(){
		return "Assistant "+super.toString();
	}

}
