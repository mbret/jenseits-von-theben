package game;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author maxime
 */
public class PlayerKnowledges {
	
	private int generalKnowledge;
	private Map<String, Integer> specificKnowledges;
	private Map<String, Integer> ethnologicalKnowledges;
	
	public PlayerKnowledges(){
		this.generalKnowledge = 0;
		this.specificKnowledges = new HashMap<String, Integer>();
		this.ethnologicalKnowledges = new HashMap<String, Integer>();
		
		this.specificKnowledges.put("Orange", 0);
		this.specificKnowledges.put("Purple", 0);
		this.specificKnowledges.put("Yellow", 0);
		this.specificKnowledges.put("Green", 0);
		this.specificKnowledges.put("Blue", 0);
		
		this.ethnologicalKnowledges.put("Orange", 0);
		this.ethnologicalKnowledges.put("Purple", 0);
		this.ethnologicalKnowledges.put("Yellow", 0);
		this.ethnologicalKnowledges.put("Green", 0);
		this.ethnologicalKnowledges.put("Blue", 0);
		
	}

	public int getGeneralKnowledge() {
		return generalKnowledge;
	}

	public void setGeneralKnowledge(int generalKnowledge) {
		this.generalKnowledge = generalKnowledge;
	}

	public Map<String, Integer> getSpecificKnowledges() {
		return specificKnowledges;
	}

	public void setSpecificKnowledges(Map<String, Integer> specificKnowledges) {
		this.specificKnowledges = specificKnowledges;
	}

	public Map<String, Integer> getEthnologicalKnowledges() {
		return ethnologicalKnowledges;
	}

	public void setEthnologicalKnowledges(
			Map<String, Integer> ethnologicalKnowledges) {
		this.ethnologicalKnowledges = ethnologicalKnowledges;
	}
	
	
	
	

}
