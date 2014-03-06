package Tokens;

public abstract class Token {

	private String color;
	
	public Token(String c){
		this.color = c;
	}

	public String getColor() {
		return color;
	}
	
	public String toString(){
		return this.getColor()+ " token";
	}
	
}
