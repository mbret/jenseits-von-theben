package Areas;
import java.util.ArrayList;
import java.util.List;

import Tokens.Token;


public class ExcavationArea extends Area {
	
	private String color;
	private List<Token> tokenList;
	
	
	public ExcavationArea(int nu, String n, String c){
		super(nu, n);
		this.color = c;
		this.tokenList = new ArrayList<Token>();
	}


	public String getColor() {
		return color;
	}
	
	

}
