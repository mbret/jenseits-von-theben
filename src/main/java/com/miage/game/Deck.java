/**
 * Copyright (C) 2014 maxime
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.miage.game;

import java.util.Collections;
import java.util.LinkedList;
import com.miage.cards.Card;

/**
 * 
 * @author maxime
 */
@SuppressWarnings("serial")
public class Deck extends LinkedList<Card>{
	
	
	public Deck(){
		
	}
	
	
	/**
	 * Draw the first card of the deck
	 * @return the first Card of the deck
	 */
	public Card pick(){
		
			
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
