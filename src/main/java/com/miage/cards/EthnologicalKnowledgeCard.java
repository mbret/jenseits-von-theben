package com.miage.cards;

import Interface.KnowledgeElement;
import com.miage.config.ConfigManager;
import java.io.IOException;

/**
 *
 * @author maxime
 */
public class EthnologicalKnowledgeCard extends Card implements KnowledgeElement{
    
    /**
     * 
     */
    private final int value;
    
    /**
     * 
     */
    private final String excavationAreaName;
    
    /**
     * Color relating to the excavation zone
     * @deprecated 
     */
    private String codeColor;

    public EthnologicalKnowledgeCard(int id, String displayName, String areaName, int weekCost, int value, String paramExcavationAreaName) throws IOException {
        super(displayName, areaName, id, weekCost);
        this.value = value;
        this.excavationAreaName = paramExcavationAreaName;
        if(excavationAreaName.equals("greece"))
            this.codeColor = ConfigManager.getInstance().getConfig( ConfigManager.AREAS_CONFIG_NAME ).getProperty("greece.color");
        else if(excavationAreaName.equals("crete"))
            this.codeColor = ConfigManager.getInstance().getConfig( ConfigManager.AREAS_CONFIG_NAME ).getProperty("crete.color");
        else if(excavationAreaName.equals("egypt"))
            this.codeColor = ConfigManager.getInstance().getConfig(ConfigManager.AREAS_CONFIG_NAME ).getProperty("egypt.color");
        else if(excavationAreaName.equals("palestine"))
            this.codeColor = ConfigManager.getInstance().getConfig(ConfigManager.AREAS_CONFIG_NAME ).getProperty("palestine.color");
        else if(excavationAreaName.equals("mesopotamia"))
            this.codeColor = ConfigManager.getInstance().getConfig(ConfigManager.AREAS_CONFIG_NAME ).getProperty("mesopotamia.color");
    }

    /**
     * 
     * @param id
     * @param areaName
     * @param weekCost
     * @param value
     * @param paramExcavationAreaName
     * @throws IOException 
     * @deprecated 
     */
    public EthnologicalKnowledgeCard(int id, String areaName, int weekCost, int value, String paramExcavationAreaName) throws IOException {
        this(id, "displayName", areaName, weekCost, value, paramExcavationAreaName);
    }


    @Override
    public boolean isDiscardable() {
        return true;
    }

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

}
