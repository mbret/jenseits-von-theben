

package com.miage.cards;

/**
 *
 * @author maxime
 */
public class ShovelCard extends Card{

    public ShovelCard(int id, String displayName, String areaName, int weekCost) {
        super(displayName, areaName, id, weekCost);
    }
  

    @Override
    public boolean isDiscardable() {
        return true;
    }
    
}
