package tokens;

public class Token {

	private String name;
	private String color;
	private String value;
	
	
	public Token(String name, String color, String value){
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

	public String getValue() {
		return value;
	}

	public String toString(){
		return this.getColor()+ " token";
	}
	
}
