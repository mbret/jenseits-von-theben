

package com.miage.cards;

import java.io.Serializable;

/**
 *
 * @author maxime
 */
public class CongressCard extends Card{

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

    
    public int getPoints( int nbCongressCards ){
        if( nbCongressCards < 1 ) throw new IndexOutOfBoundsException("Please provide a number >= 1");
        if( nbCongressCards == 1 ) return 1;
        else{
            return nbCongressCards + this.getPoints( nbCongressCards - 1 );
        }
    }
    
    @Override
    public boolean isDiscardable() {
        return false;
    }
    
}
