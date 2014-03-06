package Tokens;
import Interfaces.SpecificKnowledge;


public class SpecificKnowledgeToken extends Token implements SpecificKnowledge {

	public SpecificKnowledgeToken(String c) {
		super(c);
		
	}
	
	public String toString(){
		return "Specific knowledge and "+super.toString();
	}

}
