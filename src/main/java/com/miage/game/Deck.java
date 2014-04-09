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

import com.miage.cards.Card;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 
 * @author maxime
 */
@SuppressWarnings("serial")
public class Deck extends LinkedList<Card> implements Serializable{
	
        private final static Logger LOGGER = LogManager.getLogger(Deck.class.getName());
    
	
	public Deck(){
		
	}

        public Deck(Collection<? extends Card> c) {
            super(c);
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
         * @deprecated 
	 */
	public Deck getPartOfDeck(int fromIndex, int toIndex){
            Deck result = new Deck();
            for(int i = fromIndex; i <= toIndex; i++){
                    result.add(this.get(i));
            }
            return result;
	}
        
        /**
         * Return the deck divided in (nbPart) decks inside an array
         * @param nbPart
         * @return Deck[] the array of divided decks
         */
        public Deck[] getDividedDeck( int nbPart ){ // 2
            Deck[] decksReturn = new Deck[nbPart];
            int indexRatio = this.size() / nbPart; // get the lower (5/2 = 2 and not 2,5 or 3)
            int to = 0, from;
            for (int i = 0; i < nbPart; i++) {
                from = to;
                if (i == nbPart - 1) to = this.size(); // case of we are in the last part we get the size - 1 in order to cover unpair number of (nbPart) (because of int to = this.size() / nbPart;)
                else to = to + indexRatio;
//                LOGGER.debug("getDividedDeck : from " + from + ", to " + to + " of a deck sizeof : " + this.size());
                decksReturn[i] = new Deck( this.subList(from, to) );
            }
            return decksReturn;
        }
	
        
        /**
         * @deprecated 
         * @param cardsNumbers
         * @param classOfKey
         * @return 
         */
        public static HashMap<Object, Integer> transformListOfCard( List<String> cardsNumbers , Class classOfKey){
            HashMap<Object, Integer> cardsInsideDeck = new HashMap<Object, Integer>();
            for (String numberStr : cardsNumbers) {
                Object key = null;
                if( classOfKey.getName().equals("Integer") ){
                    key = Integer.parseInt(numberStr);
                }
                else if( classOfKey.getName().equals("String") ){
                    key = numberStr;
                }
                else{
                    key = numberStr;
                }
                cardsInsideDeck.putIfAbsent(key, 0); // add
                cardsInsideDeck.put(key, cardsInsideDeck.get(key) + 1); // and add the occurence
            }
            return cardsInsideDeck;
        }

        
}
