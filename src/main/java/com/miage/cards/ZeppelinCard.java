

package com.miage.cards; 

import Interface.UsableElement;
import java.io.Serializable;

/**
 * Allow the user to remove cost of one travel
 * @author maxime
 */
public class ZeppelinCard extends Card implements Serializable, UsableElement{

    
    public ZeppelinCard(int id, String displayName, String areaName, int weekCost) {
        super(displayName, areaName, id, weekCost);
    }

    /**
     * 
     * @param id
     * @param areaName
     * @param weekCost 
     * @deprecated 
     */
    public ZeppelinCard(int id, String areaName, int weekCost) {
        this(id, "displayName", areaName, weekCost);
    }

    
    
    @Override
    public boolean isDiscardable() {
        return true;
    }
    
    
    
}
