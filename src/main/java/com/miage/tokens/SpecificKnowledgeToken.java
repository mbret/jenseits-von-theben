

package com.miage.tokens;

import Interface.KnowledgeElement;
import java.io.Serializable;

/**
 *
 * @author maxime
 */
public class SpecificKnowledgeToken extends Token implements KnowledgeElement, Serializable{

    private final int value;
    
    public SpecificKnowledgeToken(String id, String name, String color, int value) {
        super(id, name, color);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int getKnowledgePoints() {
        return value;
    }
    
    
}
