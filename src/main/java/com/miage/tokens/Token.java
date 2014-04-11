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

    public String toString(){
            return this.getAreaName()+","+this.getColor();
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
