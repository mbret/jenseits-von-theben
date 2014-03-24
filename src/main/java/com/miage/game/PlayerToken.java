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

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.miage.areas.Area;

/**
 * 
 * @author maxime
 */
public class PlayerToken implements Comparable{
	
	private String color;
	private Area position;
        /**
         * 
         */
	private LocalDate timeState;
	
	public PlayerToken(String color){
		this.color = color;
	}


	public Area getPosition() {
		return position;
	}


	public void setPosition(Area position) {
		this.position = position;
	}


	public String getColor() {
		return color;
	}
	
	public LocalDate getTimeState() {
		return timeState;
	}


	public void setTimeState(LocalDate timeState) {
		this.timeState = timeState;
	}
	


	public String toString(){
		return "Piece coloured "+this.getColor()+" positioned in "+this.getPosition().toString();
	}

	/**
	 * @author Gael
	 * Compare 2 pieces depending on their timeState
	 * @param o Object to compare
	 * @return -1 if the timeState of this is <= timeState of p, else 1
	 */
	public int compareTo(Object o) {
		
		int result;
		
		PlayerToken p = (PlayerToken) o;
		
		if(this.getTimeState().isAfter(p.getTimeState()))
			result = 1;
		else
			result = -1;
			
		
		
		return result;
	}
	
	
	/**
	 * @author Gael
	 * Add some weeks at the timeState
	 * @param nb number of weeks to add
	 */
	public void addWeeks(int nb){
		
		this.setTimeState(this.timeState.plusDays(nb*7));
		
	}
	
	/**
	 * @author Gael
	 * return the current number of week of the playerToken
	 * @return
	 */
	public int getCurrentWeek(){
		
		return (int) (Math.ceil(this.timeState.getDayOfYear()));
	}
	
	
	/**
	 * @author Gael
	 * return the current year of the playerToken
	 * @return
	 */
	public int getCurrentYear(){
		
		return this.timeState.getYear();
	}

	
	

}
