

package com.miage.cards;

import Interface.UsableElement;
import java.io.Serializable;

/**
 * Allow user to reduce travel cost of 1 week if the travel is longer than 2 weeks
 * @author maxime
 */
public class CarCard extends Card implements Serializable{

    /**
     * 
     * @param id
     * @param displayName
     * @param areaName
     * @param weekCost 
     */
    public CarCard(int id, String displayName, String areaName, int weekCost) {
        super(displayName, areaName, id, weekCost);
    }

    
    
    @Override
    public boolean isDiscardable() {
        return false;
    }
    
}
