package com.miage.cards;

import Interface.KnowledgeElement;
import Interface.UsableElement;
import com.miage.config.ConfigManager;
import java.io.IOException;

/**
 *
 * @author maxime
 */
public class EthnologicalKnowledgeCard extends Card implements KnowledgeElement, UsableElement{
    
    /**
     * 
     */
    private final int value;
    
    /**
     * 
     */
    private final String excavationAreaName;

    public EthnologicalKnowledgeCard(int id, String displayName, String areaName, int weekCost, int value, String paramExcavationAreaName) throws IOException {
        super(displayName, areaName, id, weekCost);
        this.value = value;
        this.excavationAreaName = paramExcavationAreaName;
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

    public int getValue() {
        return value;
    }

    public String getExcavationAreaName() {
        return excavationAreaName;
    }

    @Override
    public int getKnowledgePoints() {
        return this.value;
    }

}
