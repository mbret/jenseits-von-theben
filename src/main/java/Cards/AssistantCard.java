package Cards;
import Areas.Area;
import Interfaces.Underplayable;


public class AssistantCard extends Card implements Underplayable{

	public AssistantCard(Area a) {
		super(a);
		this.setCost(2);
		
	}
	
	public String toString(){
		return "Assistant "+super.toString();
	}

}
