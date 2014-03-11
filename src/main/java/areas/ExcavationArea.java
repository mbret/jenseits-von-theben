package areas;
import java.util.ArrayList;
import java.util.List;

import tokens.Token;


public class ExcavationArea extends Area {
	
	private String color;
	private List<Token> tokenList;
	
	
	public ExcavationArea(int num, String name, String color){
		super(num, name);
		this.color = color;
		this.tokenList = new ArrayList<Token>();
	}


	public String getColor() {
		return color;
	}
	
	

}
