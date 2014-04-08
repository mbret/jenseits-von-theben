

package com.miage.cards;

import Interface.CombinableElement;
import Interface.KnowledgeElement;
import Interface.UsableElement;
import java.util.HashMap;

/**
 *
 * @author maxime
 */
public class AssistantCard extends Card implements KnowledgeElement, CombinableElement, UsableElement{

    
    private static final HashMap<Integer, Integer> values = new HashMap(){{
        this.put(2, 1); // two assistants
        this.put(3, 2); // three assistants
    }};
    
    public AssistantCard(int id, String displayName, String areaName, int weekCost) {
        super(displayName, areaName, id, weekCost);
    }

    
    /**
     * Special behavior here
     * @return 
     */
    @Override
    public boolean isDiscardable() {
        return true;
    }
    
    public boolean isDiscardableWhenCombinated( int nbCombinatedElement ) {
        return nbCombinatedElement <= 1;
    }

    @Override
    public int getKnowledgePoints() {
        return 1;
    }

    /**
     * // 2 -> 2            2%3 = 2
     * // 3 -> 3
     * // 4 -> 2 + 2        4%3 = 1
     * // 5 -> 3 + 2        5%3 = 2
     * // 6 -> 3 + 3        6%3 = 0
     * // 7 -> 3 + 2 + 2    7%3 = 1
     * // 8 -> 3 + 3 + 2    8%3 = 2
     * @param nbOfThisElementCombined
     * @return 
     */
    public static int getKnowLedgePointsWhenCombinated( int nbOfThisElementCombined ){
        if( nbOfThisElementCombined == 0){
            return 0;
        }
        // no 2
        else if( nbOfThisElementCombined%3 == 0 ){
            return AssistantCard.values.get(3) + AssistantCard.getKnowLedgePointsWhenCombinated( nbOfThisElementCombined - 3);
        }
        // two 2 left or one left but there is no more place for piece of 3
        else if( (nbOfThisElementCombined%3 == 1 && nbOfThisElementCombined-4 == 0) || (nbOfThisElementCombined%3 == 2 && nbOfThisElementCombined-2 == 0) ){
            return AssistantCard.values.get(2) + AssistantCard.getKnowLedgePointsWhenCombinated( nbOfThisElementCombined - 2 );
        }
        // one or two 2 left but still have place to divide in piece of 3
        else{
            return AssistantCard.values.get(3) + AssistantCard.getKnowLedgePointsWhenCombinated( nbOfThisElementCombined - 3);
        }
    }
    
}
