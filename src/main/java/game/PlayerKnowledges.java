package game;

public class PlayerKnowledges {
	
	public int specificKnowledge;
	public int ethnologicalKnowledge;
	public boolean excavationAuthorisation;
	
	public PlayerKnowledges(){
		
		this.specificKnowledge = 0;
		this.ethnologicalKnowledge = 0;
		this.excavationAuthorisation = true;
		
	}

	public int getEthnologicalKnowledge() {
		return ethnologicalKnowledge;
	}

	public void setEthnologicalKnowledge(int ethnologicalKnowledge) {
		this.ethnologicalKnowledge = ethnologicalKnowledge;
	}

	public boolean isExcavationAuthorisation() {
		return excavationAuthorisation;
	}

	public void setExcavationAuthorisation(boolean excavationAuthorisation) {
		this.excavationAuthorisation = excavationAuthorisation;
	}

	public int getSpecificKnowledge() {
		return specificKnowledge;
	}
	
	
	public void setSpecificKnowledge(int specificKnowledge) {
		this.specificKnowledge = specificKnowledge;
	}

	public String toString(){
		return this.getSpecificKnowledge()+" specific knowledge points and "+this.getEthnologicalKnowledge()+" ethnological knowledge";
	}
	
	

}
