

package com.miage.cards;

import com.miage.config.ConfigManager;
import java.io.IOException;

/**
 *
 * @author maxime
 */
public class EthnologicalKnowledgeCard extends Card{
    
    private int value;
    private String excavationAreaName;
    
    /**
     * Color relating to the excavation zone
     * @deprecated 
     */
    private String codeColor;

    public EthnologicalKnowledgeCard(String areaName, int weekCost, int value, String paramExcavationAreaName) throws IOException {
            super("ethnologicalKnowledge",areaName,weekCost);
            this.value = value;
            this.excavationAreaName = paramExcavationAreaName;
            
            
            if(excavationAreaName.equals("greece"))
            	this.codeColor = ConfigManager.getInstance().getConfig().getProperty("areas.greece.color");
            else if(excavationAreaName.equals("crete"))
            	this.codeColor = ConfigManager.getInstance().getConfig().getProperty("areas.crete.color");
            else if(excavationAreaName.equals("egypt"))
            	this.codeColor = ConfigManager.getInstance().getConfig().getProperty("areas.egypt.color");
            else if(excavationAreaName.equals("palestine"))
            	this.codeColor = ConfigManager.getInstance().getConfig().getProperty("areas.palestine.color");
            else if(excavationAreaName.equals("mesopotamia"))
            	this.codeColor = ConfigManager.getInstance().getConfig().getProperty("areas.mesopotamia.color");
            	
            
    }

    public int getValue() {
            return value;
    }

    public String getCodeColor() {
        return codeColor;
    }

    @Override
    public boolean isDiscardable() {
        return false;
    }
    
    
    public String getExcavationAreaName() {
        return excavationAreaName;
    }

    public String toString(){
    	return super.toString()+","+value+","+excavationAreaName;
    }
}
