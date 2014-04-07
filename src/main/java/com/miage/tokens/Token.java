package com.miage.tokens;

public abstract class Token {


	private final String color;
        
        /**
         * Name of area where the token can be pick up
         */
        protected final String areaName;
        
        /**
         * Use the id inside .properties
         * Used to retrieve the image file
         */
	private final String id;
        
        public Token(String id, String areaName, String color){
            this.id = id;
            this.color = color;
            this.areaName = areaName;
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
