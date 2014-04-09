

package com.miage.cards;

import Interface.KnowledgeElement;
import java.io.Serializable;

/**
 *
 * @author maxime
 */
public class AssistantCard extends Card implements Serializable, KnowledgeElement{

    
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
    
}
