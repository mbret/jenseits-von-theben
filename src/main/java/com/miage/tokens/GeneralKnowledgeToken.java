

package com.miage.tokens;

import com.miage.interfaces.ActiveElement;
import com.miage.interfaces.CombinableElement;
import com.miage.interfaces.DiscardableElement;
import com.miage.interfaces.KnowledgeElement;
import com.miage.interfaces.ActivableElement;

import java.io.Serializable;

/**
 *
 * @author maxime
 */
public class GeneralKnowledgeToken extends Token implements KnowledgeElement, ActiveElement{

    private final int value;
    
    public GeneralKnowledgeToken(String id, String name, String color, int value) {
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

    @Override
    public String toString() {
        return "GeneralKnowledgeToken{" + "value=" + value + '}';
    }
    
    
    
}
