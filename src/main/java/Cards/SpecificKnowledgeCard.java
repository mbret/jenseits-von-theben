package Cards;
import Areas.Area;
import Interfaces.SpecificKnowledge;


public class SpecificKnowledgeCard extends Card implements SpecificKnowledge{
	
	private int value;
	private String color;

	public SpecificKnowledgeCard(Area a, int v, String c) {
		super(a);
		this.value = v;
		this.color = c;
		
		if(v == 1)
			this.setCost(1);
		if(v == 2)
			this.setCost(2);
		if(v == 3){
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
