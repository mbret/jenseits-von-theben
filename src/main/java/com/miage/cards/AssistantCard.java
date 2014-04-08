

package com.miage.cards;

import Interface.CombinableElement;
import Interface.KnowledgeElement;
import Interface.UsableElement;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author maxime
 */
public class AssistantCard extends Card implements KnowledgeElement, CombinableElement, UsableElement{

    
    private static HashMap<Integer, Integer> values = new HashMap(){{
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
     * @param nb
     * @param nbCombinatedElement
     * @return 
     */
//    public static int getKnowLedgePointsWhenCombinated( int nbCombinatedElement ){
//        if( nbCombinatedElement == 1) return 1;
//        int result = 0;
//        int nbPartOf2 = 0;
//        switch( nbCombinatedElement % 3){
//            case 0:
//                break;
//            case 1:
//                nbPartOf2 = 2;
//                break;
//            case 2:
//                nbPartOf2 = 1;
//                break;
//        }
//        for (int i = 0; i < (nbCombinatedElement - (nbPartOf2*2) ); i++) result += AssistantCard.values.get(3);
//        for (int i = 0; i < nbPartOf2; i++) result += AssistantCard.values.get(2);
//        return result;
//    }
    
    public static int getKnowLedgePointsWhenCombinated( int nb ){
        if( nb == 0){
            return 0;
        }
        // no 2
        else if( nb%3 == 0 ){
            return AssistantCard.values.get(3) + AssistantCard.getKnowLedgePointsWhenCombinated( nb - 3);
        }
        // two 2 left or one left but there is no more place for piece of 3
        else if( (nb%3 == 1 && nb-4 == 0) || (nb%3 == 2 && nb-2 == 0) ){
            return AssistantCard.values.get(2) + AssistantCard.getKnowLedgePointsWhenCombinated( nb - 2 );
        }
        // one or two 2 left but still have place to divide in piece of 3
        else{
            return AssistantCard.values.get(3) + AssistantCard.getKnowLedgePointsWhenCombinated( nb - 3);
        }
    }
    
}
