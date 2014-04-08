

package com.miage.cards;

import java.io.Serializable;

/**
 *
 * @author maxime
 */
public class ShovelCard extends Card implements Serializable{

    public ShovelCard(int id, String displayName, String areaName, int weekCost) {
        super(displayName, areaName, id, weekCost);
    }
  

    @Override
    public boolean isDiscardable() {
        return true;
    }
    
}
