package Tokens;

public class PointToken extends Token {

	private int value;
	
	public PointToken(String c, int v) {
		super(c);
		this.value = v;
	}

	public int getValue() {
		return value;
	}
	
	
	public String toString(){
		return super.toString()+ " and worthing "+this.getValue()+" points";
		
	}

	
}
