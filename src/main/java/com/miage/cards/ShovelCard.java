

package com.miage.cards;

import Interface.CombinableElement;
import Interface.DiscardableElement;
import Interface.UsableElement;
import java.util.HashMap;

/**
 *
 * @author maxime
 */
public class ShovelCard extends Card implements UsableElement, CombinableElement, DiscardableElement{

    
    private static HashMap<Integer, Integer> values = new HashMap(){{
        this.put(2, 1); // two assistants
        this.put(3, 2); // three assistants
    }};
    
    
    public ShovelCard(int id, String displayName, String areaName, int weekCost) {
        super(displayName, areaName, id, weekCost); 
    }
  

    @Override
    public boolean isDiscardable() {
        return true;
    }
    
    public static int getTokensPointsWhenCombinated( int nb ){
        if( nb == 0){
            return 0;
        }
        // no 2
        else if( nb%3 == 0 ){
            return ShovelCard.values.get(3) + ShovelCard.getTokensPointsWhenCombinated( nb - 3);
        }
        // two 2 left or one left but there is no more place for piece of 3
        else if( (nb%3 == 1 && nb-4 == 0) || (nb%3 == 2 && nb-2 == 0) ){
            return ShovelCard.values.get(2) + ShovelCard.getTokensPointsWhenCombinated( nb - 2 );
        }
        // one or two 2 left but still have place to divide in piece of 3
        else{
            return ShovelCard.values.get(3) + ShovelCard.getTokensPointsWhenCombinated( nb - 3);
        }
    }
    
}
