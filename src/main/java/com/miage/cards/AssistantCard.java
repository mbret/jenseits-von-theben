

package com.miage.cards;

import Interface.KnowledgeElement;

/**
 *
 * @author maxime
 */
public class AssistantCard extends Card implements KnowledgeElement{

    
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
