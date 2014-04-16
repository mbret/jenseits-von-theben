

package com.miage.cards;

import java.io.Serializable;

/**
 *
 * @author maxime
 */
public class CongressCard extends Card implements Serializable{

    public CongressCard(int id, String displayName, String areaName, int weekCost) {
        super(displayName, areaName, id, weekCost);
    }

    /**
     * 
     * @param id
     * @param areaName
     * @param weekCost 
     * @deprecated 
     */
    public CongressCard(int id, String areaName, int weekCost) {
        this(id, "displayName", areaName, weekCost);
    }

    
    @Override
    public boolean isDiscardable() {
        return false;
    }
    
}
