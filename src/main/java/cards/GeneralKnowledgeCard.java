package cards;
import interfaces.GeneralKnowledge;
import areas.Area;


public class GeneralKnowledgeCard extends Card implements GeneralKnowledge{
	
	private int value;

	public GeneralKnowledgeCard(Area area, int value) {
		super(area);
		this.value = value;
		
		if(value == 1)
			this.setCost(3);
		if(value == 2)
			this.setCost(6);
		
	}

	public int getValue() {
		return value;
	}
	
	public String toString(){
		return "General knowledge "+ super.toString()+" and worthing "+this.getValue()+" points";
	}

}
