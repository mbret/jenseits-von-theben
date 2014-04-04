package com.miage.tokens;

public abstract class Token {

	private String areaName;
	private String color;
        
        /**
         * Use the id inside .properties
         * Used to retrieve the image file
         */
	private String id;
        
	public Token(String id, String areaName, String color){
            this.id = id;
		this.areaName = areaName;
		this.color = color;
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
	
	
}
