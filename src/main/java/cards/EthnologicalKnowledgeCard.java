package cards;
import interfaces.EthnologicalKnowledge;
import interfaces.Underplayable;
import areas.Area;


public class EthnologicalKnowledgeCard extends Card implements EthnologicalKnowledge, Underplayable {
	
	private int value;
	private String color;

	public EthnologicalKnowledgeCard(Area a, String color) {
		super(a);
		this.setCost(1);
		this.color = color;
		this.value = 2;
	}

	public int getValue() {
		return value;
	}
	
	public String getColor(){
		return color;
	}
	
	public String toString(){
		return "Ethnological knowledge "+ super.toString()+" and worthing "+this.getValue()+" points";
	}

}
