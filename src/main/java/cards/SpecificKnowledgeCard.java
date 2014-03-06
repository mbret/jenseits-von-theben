package cards;
import interfaces.SpecificKnowledge;
import areas.Area;


public class SpecificKnowledgeCard extends Card implements SpecificKnowledge{
	
	private int value;
	private String color;

	public SpecificKnowledgeCard(Area area, int value, String color) {
		super(area);
		this.value = value;
		this.color = color;
		
		if(value == 1)
			this.setCost(1);
		if(value == 2)
			this.setCost(2);
		if(value == 3){
			this.setCost(4);
		}
		
	}

	public int getValue() {
		return value;
	}
	
	public String toString(){
		return "Specific knowledge "+ super.toString()+" and worthing "+this.getValue()+" points";
	}

}
