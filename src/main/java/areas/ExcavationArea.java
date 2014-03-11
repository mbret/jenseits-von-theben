package areas;

import java.util.Collections;
import java.util.LinkedList;

import tokens.Token;


public class ExcavationArea extends Area {
	
	private String color;
	private LinkedList<Token> tokenList;
	
	
	public ExcavationArea(int num, String name, String color){
		super(num, name);
		this.color = color;
		this.tokenList = new LinkedList<Token>();
	}


	public String getColor() {
		return color;
	}
	
	public void addToken(Token token){
		this.tokenList.add(token);
	}
	
	public Token drawToken(){
		
		return this.tokenList.removeFirst();
	}
	
	public void mixTokenList(){
		Collections.shuffle(this.tokenList);
	}
	

}
