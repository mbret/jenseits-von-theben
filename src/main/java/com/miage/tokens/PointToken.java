

package com.miage.tokens;

/**
 * A point token can be empty or with a value
 * @author maxime
 */
public class PointToken extends Token{

    private Integer value;
    
    public PointToken(String id, String name, String color, Integer value) {
        super(id, name, color);
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
    
    
    public String toString(){
    	return super.toString()+","+value;
    }
    
}
