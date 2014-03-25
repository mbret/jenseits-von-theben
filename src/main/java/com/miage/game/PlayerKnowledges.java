package com.miage.game;

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
		
		this.specificKnowledges.put("greece", 0);   // Orange
		this.specificKnowledges.put("crete", 0);	 // Purple
		this.specificKnowledges.put("egypt", 0);	 // Yellow
		this.specificKnowledges.put("palestine", 0);   // Green
		this.specificKnowledges.put("mesopotamia", 0);	 // Blue
		
		this.ethnologicalKnowledges.put("greece", 0);	// Orange
		this.ethnologicalKnowledges.put("crete", 0);	// Purple
		this.ethnologicalKnowledges.put("egypt", 0);	// Yellow
		this.ethnologicalKnowledges.put("palestine", 0);	// Green
		this.ethnologicalKnowledges.put("mesopotamia", 0);	// Blue
		
	}
	
	
	/**
	 * @author Gael
	 * 
	 *  add specific knowledge points in the specified color
	 * 
	 * @param color of the knowledge concerned
	 * @param value to add
	 */
	public void addSpecificKnowledges(String name, int value){
		
		this.specificKnowledges.put(name, this.specificKnowledges.get(name)+value);
	}
	
	
	/**
	 * @author Gael
	 * 
	 *  add ethnological knowledge points in the specified color
	 * 
	 * @param color of the knowledge concerned
	 * @param value to add
	 */
	public void addEthnologicalKnowledges(String name, int value){
		
		this.ethnologicalKnowledges.put(name, this.ethnologicalKnowledges.get(name)+value);
	}
	
	
	/**
	 * @author Gael
	 * 
	 *  add general knowledge points in the specified color
	 *
	 * @param value to add
	 */
	public void addGeneralKnowledges(int value){
		
		this.generalKnowledge += value;
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
