package com.miage.tokens;

public abstract class Token {

	private String name;
	private String color;
	
	public Token(String name, String color){
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
		return this.getName()+ " token coloured "+this.getColor();
	}
	
	
}
