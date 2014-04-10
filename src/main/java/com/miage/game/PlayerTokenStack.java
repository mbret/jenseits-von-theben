

package com.miage.game;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Stack of player's token
 * This stack allow the program to know the position of each player's token and which one should play before others
 * 
 * @author maxime
 */
public class PlayerTokenStack extends LinkedList<PlayerToken> implements Iterable<PlayerToken>, Serializable{
	
	
	/**
	 * Add a piece into the stack depending on its timeStatus
	 * @param p Piece to add in the stack
         * @deprecated 
	 */
	public void addPlayerToken(PlayerToken playerToken){
		
		int index = 0;
		
		for(PlayerToken p : this){
			
			if(playerToken.getTimeState().compareTo(p.getTimeState()) < 1)
				break;
			
			index++;
		}
		
		this.add(index, playerToken);
		
	}
	
	/**
	 * delete the first piece of the stack and return it
	 * @return the first piece of the stack
	 */
	public PlayerToken getFirstPlayerToken(){
            PlayerToken firstPiece = this.removeFirst();
            return firstPiece;
	}
	
	public String toString(){
		String result = "";
		
		for(PlayerToken p : this){
			result += p.getColor()+" ";
		}
		
		return result;
	}

}
