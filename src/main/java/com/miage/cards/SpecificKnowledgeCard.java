package com.miage.cards;

import com.miage.utils.ConfigManager;
import com.miage.interfaces.ActiveElement;
import com.miage.interfaces.KnowledgeElement;
import java.io.IOException;
import java.io.Serializable;

/**
 * 
 * @author maxime
 */
public class SpecificKnowledgeCard extends Card implements KnowledgeElement, ActiveElement{
	
    /**
     * 
     */
    private final int value;
    
    /**
     * 
     */
    private final String excavationAreaName;
    
    /**
     * Color relating to the excavation area
     * @deprecated 
     */
    private String codeColor;
    

    public SpecificKnowledgeCard(int id, String displayName, String areaName, int weekCost, int value, String paramExcavationAreaName) throws IOException {
            super(displayName, areaName, id, weekCost);
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
    
    
    @Override
    public boolean isDiscardable() {
        return false;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString(){
    	return super.toString()+","+value+","+excavationAreaName;
    }
    
    /***********************************************************************************************
     *
     *                                  Getter & Setter
     * 
     ***********************************************************************************************/
    
    /**
     * 
     * @return
     */
    public String getCodeColor() {
        return codeColor;
    }

    public void setCodeColor(String codeColor) {
        this.codeColor = codeColor;
    }

    public int getValue() {
        return value;
    }

    public String getExcavationAreaName() {
        return excavationAreaName;
    }

    @Override
    public int getKnowledgePoints() {
        return value;
    }

}
