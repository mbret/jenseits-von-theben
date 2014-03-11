package game;

import java.util.Collections;
import java.util.LinkedList;

import cards.Card;



@SuppressWarnings("serial")
public class Deck extends LinkedList<Card>{
	
	
	public Deck(){
		
	}
	
	
	/**
	 * Draw the first card of the deck
	 * @return the first Card of the deck
	 */
	public Card draw(){
		
			
		return this.removeFirst();
		
		
	}
	
	/**
	 * mix the deck
	 */
	public void mix(){
			
		Collections.shuffle(this);
	}
	
	
	/**
	 * add a card in the deck
	 * @param card Card to add in the deck
	 */
	public void addCard(Card card){
		this.add(card);
	}
	
	
	public String toString(){
		String result = "";
		
		for(Card card : this){
			result += card.toString()+"\n";
		}
		
		return result;
	}
	
	/**
	 * Return a part of the deck into an other deck
	 * @param fromIndex index of beginning
	 * @param toIndex index of end
	 * @return a new Deck containing the part of the first deck
	 */
	public Deck divideDeck(int fromIndex, int toIndex){
		
		Deck result = new Deck();
		
		for(int i = fromIndex; i <= toIndex; i++){
			result.addCard(this.get(i));
		}
		
		return result;
		
	}
	
	

}
