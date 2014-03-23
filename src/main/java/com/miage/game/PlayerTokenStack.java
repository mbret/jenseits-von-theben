/*
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

import java.util.LinkedList;

/**
 * PieceStack correspond to the stack of player piece
 * 
 * @author maxime
 */
public class PlayerTokenStack extends LinkedList<PlayerToken> implements Iterable<PlayerToken>{
	
	
	/**
	 * Add a piece into the stack depending on its timeStatus
	 * @param p Piece to add in the stack
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
