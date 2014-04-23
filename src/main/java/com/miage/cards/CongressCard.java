

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

    
    public static int getPoints( int nbCongressCards ){
        if( nbCongressCards < 0 || nbCongressCards > 7 ) throw new IndexOutOfBoundsException("Please provide a number >= 1 && <= 7");
        else if( nbCongressCards == 0 ) return 0;
        else if( nbCongressCards == 1 ) return 1;
        else{
            return nbCongressCards + getPoints( nbCongressCards - 1 );
        }
    }
    
    @Override
    public boolean isDiscardable() {
        return false;
    }
    
}
