

package com.miage.cards;

import com.miage.config.ConfigManager;
import java.io.IOException;

/**
 * 
 * @author maxime
 */
public class SpecificKnowledgeCard extends Card{
	
    private int value;
    private String excavationAreaName;
    
    /**
     * Color relating to the excavation area
     * @deprecated 
     */
    private String codeColor;
    

    public SpecificKnowledgeCard(int id, String areaName, int weekCost, int value, String paramExcavationAreaName) throws IOException {
            super(id, "specificKnowledge",areaName,weekCost);
            this.value = value;
            this.excavationAreaName = paramExcavationAreaName;
            
            
            if(excavationAreaName.equals("greece"))
            	this.codeColor = ConfigManager.getInstance().getConfig( ConfigManager.AREAS_CONFIG_NAME ).getProperty("greece.color");
            else if(excavationAreaName.equals("crete"))
            	this.codeColor = ConfigManager.getInstance().getConfig(ConfigManager.AREAS_CONFIG_NAME ).getProperty("crete.color");
            else if(excavationAreaName.equals("egypt"))
            	this.codeColor = ConfigManager.getInstance().getConfig(ConfigManager.AREAS_CONFIG_NAME ).getProperty("egypt.color");
            else if(excavationAreaName.equals("palestine"))
            	this.codeColor = ConfigManager.getInstance().getConfig(ConfigManager.AREAS_CONFIG_NAME ).getProperty("palestine.color");
            else if(excavationAreaName.equals("mesopotamia"))
            	this.codeColor = ConfigManager.getInstance().getConfig(ConfigManager.AREAS_CONFIG_NAME ).getProperty("mesopotamia.color");
    }

    public int getValue() {
            return value;
    }

    public String getCodeColor() {
        return codeColor;
    }

    
    public String getExcavationAreaName() {
		return excavationAreaName;
	}
    
    @Override
    public boolean isDiscardable() {
        return false;
    }
    
    public String toString(){
    	return super.toString()+","+value+","+excavationAreaName;
    }

}
