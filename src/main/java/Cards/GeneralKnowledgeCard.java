package Cards;
import Areas.Area;
import Interfaces.GeneralKnowledge;


public class GeneralKnowledgeCard extends Card implements GeneralKnowledge{
	
	private int value;

	public GeneralKnowledgeCard(Area a, int v) {
		super(a);
		this.value = v;
		
		if(v == 1)
			this.setCost(3);
		if(v == 2)
			this.setCost(6);
		
	}

	public int getValue() {
		return value;
	}
	
	public String toString(){
		return "General knowledge "+ super.toString()+" and worthing "+this.getValue()+" points";
	}

}
