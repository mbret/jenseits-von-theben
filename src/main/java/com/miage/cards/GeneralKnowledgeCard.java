

package com.miage.cards;

import Interface.KnowledgeElement;
import Interface.UsableElement;
import java.io.Serializable;

/**
 * 
 * @author maxime
 */
public class GeneralKnowledgeCard extends Card implements Serializable, KnowledgeElement{
	
    /**
     * 
     */
    private final int value;

    public GeneralKnowledgeCard(int id, String displayName, String areaName, int weekCost, int value) {
            super(id, displayName, areaName, weekCost);
            this.value = value;
    }
    
    
    

    /***********************************************************************************************
     *
     *                                  Methods
     * 
     ***********************************************************************************************/
    
    /**
     * 
     * @return 
     */
    @Override
    public boolean isDiscardable() {
        return false;
    }
    
    @Override
    public String toString(){
    	return super.toString()+","+value;
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
    public int getValue() {
        return value;
    }

    @Override
    public int getKnowledgePoints() {
        return this.value;
    }


    
}
