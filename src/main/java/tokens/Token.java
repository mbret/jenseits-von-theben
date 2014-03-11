package tokens;

public abstract class Token {

	private String color;
	
	public Token(String color){
		this.color = color;
	}

	public String getColor() {
		return color;
	}
	
	public String toString(){
		return this.getColor()+ " token";
	}
	
}
