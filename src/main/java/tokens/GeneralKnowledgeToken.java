package tokens;
import interfaces.GeneralKnowledge;


public class GeneralKnowledgeToken extends Token implements GeneralKnowledge {

	public GeneralKnowledgeToken(String color) {
		super(color);
	}
	
	public String toString(){
		return "General knowledge and "+super.toString();
	}

}
