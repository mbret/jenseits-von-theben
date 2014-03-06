package tokens;
import interfaces.SpecificKnowledge;


public class SpecificKnowledgeToken extends Token implements SpecificKnowledge {

	public SpecificKnowledgeToken(String color) {
		super(color);
		
	}
	
	public String toString(){
		return "Specific knowledge and "+super.toString();
	}

}
