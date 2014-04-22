package com.miage.tokens;

import java.io.Serializable;

public abstract class Token implements Serializable{

    /**
     * Name of area where the token can be pick up
     */
    protected final String areaName;

    /**
     * Use the id inside .properties
     * Used to retrieve the image file
     */
    private final String id;

    public Token(String id, String areaName){
        this.id = id;
        this.areaName = areaName;
        this.color = "color";

    }

    public String getColor() {
            return color;
    }

    public String getAreaName() {
            return areaName;
    }

    @Override
    public String toString() {
        return "Token{" + "areaName=" + areaName + ", id=" + id + ", color=" + color + '}';
    }

    

    public String getId() {
        return id;
    }
	
    
    
    
    
    
    
    
    
    
    
    
    /**
     * @deprecated 
     */
    private final String color;
    /**
     * @deprecated 
     * @param id
     * @param areaName
     * @param color 
     */
    public Token(String id, String areaName, String color){
        this.id = id;
        this.color = color;
        this.areaName = areaName;
    }
	
}
