package com.miage.tokens;

public abstract class Token {

	private String name;
	private String color;
        
        /**
         * Use the id inside .properties
         * Used to retrieve the image file
         */
	private String id;
        
	public Token(String id, String name, String color){
            this.id = id;
		this.name = name;
		this.color = color;
	}

	public String getColor() {
		return color;
	}
	
	public String getName() {
		return name;
	}

	public String toString(){
		return this.getName()+","+this.getColor();
	}
	
	
}
