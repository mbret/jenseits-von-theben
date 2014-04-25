

package com.miage.tokens;

import com.miage.interfaces.ActiveElement;
import com.miage.interfaces.KnowledgeElement;
import java.io.Serializable;

/**
 *
 * @author maxime
 */
public class SpecificKnowledgeToken extends Token implements KnowledgeElement, ActiveElement{

    private final int value;
    
    public SpecificKnowledgeToken(String id, String areaName, String color, int value) {
        super(id, areaName, color);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int getKnowledgePoints() {
        return value;
    }

    @Override
    public String toString() {
        return "SpecificKnowledgeToken{" + "value=" + value + '}';
    }
    
    
    
}
