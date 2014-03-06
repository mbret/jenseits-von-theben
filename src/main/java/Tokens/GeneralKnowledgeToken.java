package Tokens;
import Interfaces.GeneralKnowledge;


public class GeneralKnowledgeToken extends Token implements GeneralKnowledge {

	public GeneralKnowledgeToken(String c) {
		super(c);
	}
	
	public String toString(){
		return "General knowledge and "+super.toString();
	}

}
