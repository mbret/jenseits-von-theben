

package com.miage.cards;

import com.miage.areas.Area;
import com.miage.interfaces.CombinableElement;
import com.miage.interfaces.DiscardableElement;
import com.miage.interfaces.KnowledgeElement;
import com.miage.interfaces.UsableElement;
import java.io.Serializable;
import java.util.HashMap;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * AssistantCard represent the cards assistant. These cards are combinable. It means that the user can use several of this card simultaneously.
 * 
 * <p><b>Please note that this card is combinable, it means that it has some special behaviors. Check all method rigorously</b></p>
 * 
 * @author maxime
 * 
 */
public class AssistantCard extends Card implements KnowledgeElement, CombinableElement, UsableElement, Serializable{

    private final static Logger LOGGER = LogManager.getLogger(AssistantCard.class.getName());
    
    /**
     * Represent the values earned depending of how many of this card are combinated
     */
    private static final HashMap<Integer, Integer> values = new HashMap(){{
        this.put(2, 1); // two assistants
        this.put(3, 2); // three assistants
    }};
    
    /**
     * 
     * @param id
     * @param displayName
     * @param areaName
     * @param weekCost 
     */
    public AssistantCard(int id, String displayName, String areaName, int weekCost) {
        super(displayName, areaName, id, weekCost);
    }

    
    /**
     * 
     * @return 
     */
    @Override
    public boolean isDiscardable() {
        return true;
    }
    
    /**
     * Check if the card is discardable when this card is combinated with x of itself
     * @param nbCombinatedElement
     * @return boolean
     */
    public boolean isDiscardableWhenCombinated( int nbCombinatedElement ) {
        return nbCombinatedElement <= 1; // if <= 1 then discardable otherwise, never !
    }

    @Override
    public int getKnowledgePoints() {
        return 1;
    }

    /**
     * Return the number of knowledge the combinate assistant cards allow the player to use
     * <br/>Effect:
     * <br/>- Check the number of combinate cards and calculate the points with the HashMap values
     * @param nbOfThisElementCombined
     * @return int
     */
    public static int getKnowLedgePointsWhenCombinated( int nbOfThisElementCombined ){
        /**
         * These calculs are based on the modulo.
         * If x % 3 give 2 then there is one pair of 2
         * if x % 3 give 1 then there are two pair of 2
         * if x % 3 give 0 then there are only pair of 3
         * The following calcul check this result and calculate how many maximum of pair of 3 we can use
         */
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
