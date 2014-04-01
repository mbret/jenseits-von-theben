

package com.miage.cards; 

/**
 * Allow the user to remove cost of one travel
 * @author maxime
 */
public class ZeppelinCard extends Card{

    
    public ZeppelinCard(int id, String areaName, int weekCost) {
        super(id, "zeppelin", areaName, weekCost);
    }

    @Override
    public boolean isDiscardable() {
        return true;
    }
    
    
    
}
