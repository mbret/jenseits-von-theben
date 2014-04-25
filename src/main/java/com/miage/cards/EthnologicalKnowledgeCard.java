package com.miage.cards;

import com.miage.interfaces.CombinableElement;
import com.miage.interfaces.DiscardableElement;
import com.miage.interfaces.KnowledgeElement;
import com.miage.interfaces.ActivableElement;
import com.miage.utils.ConfigManager;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author maxime
 */
public class EthnologicalKnowledgeCard extends Card implements KnowledgeElement, ActivableElement{
    
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
    	return "EthnologicalKnowledgeCard" + super.toString()+","+value+","+excavationAreaName;
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
