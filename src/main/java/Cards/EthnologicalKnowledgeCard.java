package Cards;
import Areas.Area;
import Interfaces.EthnologicalKnowledge;
import Interfaces.Underplayable;


public class EthnologicalKnowledgeCard extends Card implements EthnologicalKnowledge, Underplayable {
	
	private int value;
	private String color;

	public EthnologicalKnowledgeCard(Area a, String c) {
		super(a);
		this.setCost(1);
		this.color = c;
		this.value = 2;
	}

	public int getValue() {
		return value;
	}
	
	public String toString(){
		return "Ethnological knowledge "+ super.toString()+" and worthing "+this.getValue()+" points";
	}

}
