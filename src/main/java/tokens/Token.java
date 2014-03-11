package tokens;

public class Token {

	private String name;
	private String color;
	private int value;
	
	
	public Token(String name, String color, int value){
		this.name = name;
		this.color = color;
		this.value = value;
	}

	public String getColor() {
		return color;
	}
	
	
	
	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public String toString(){
		return this.getName()+ " token coloured "+this.getColor();
	}
	
	
}
