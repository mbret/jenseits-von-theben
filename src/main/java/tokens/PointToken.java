package tokens;


public class PointToken extends Token {

	private int value;
	
	public PointToken(String color, int value) {
		super(color);
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	
	public String toString(){
		return super.toString()+ " and worthing "+this.getValue()+" points";
		
	}

	
}
