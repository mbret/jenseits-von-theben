

package com.miage.cards;

import java.io.Serializable;

/**
 * Allow user to make a supplementary excavation action during the current year
 * @author maxime
 */
public class ExcavationAuthorizationCard extends Card implements Serializable{

    public ExcavationAuthorizationCard(int id, String displayName, String areaName, int weekCost) {
        super(displayName, areaName, id, weekCost);
    }

    /**
     * 
     * @param id
     * @param areaName
     * @param weekCost 
     * @deprecated 
     */
    public ExcavationAuthorizationCard(int id, String areaName, int weekCost) {
        this(id, "displayName", areaName, weekCost);
    }

    
    @Override
    public boolean isDiscardable() {
        return true;
    }
    
    
}
