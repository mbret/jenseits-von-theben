

package com.miage.tokens;

import java.io.Serializable;
import java.util.Objects;

/**
 * A point token can be empty or with a value
 * 
 * @author maxime
 */
public class PointToken extends Token implements Serializable{

    private final Integer value;
    
    public PointToken(String id, String areaName, String color, Integer value) {
        super(id, areaName, color);
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
    
    
    public String toString(){
    	return super.toString()+","+value;
    }

    public boolean isBlank(){
        return this.value == 0;
    }
    
}
