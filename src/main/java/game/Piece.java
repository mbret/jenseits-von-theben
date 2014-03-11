package game;

import java.util.Calendar;
import java.util.GregorianCalendar;

import areas.Area;

public class Piece implements Comparable{
	
	private String color;
	private Area position;
	private Calendar timeState;
	private Player player;
	
	
	public Piece(String color){
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
	
	public Calendar getTimeState() {
		return timeState;
	}


	public void setTimeState(Calendar timeState) {
		this.timeState = timeState;
	}
	
	
	
	
	public Player getPlayer() {
		return player;
	}


	public void setPlayer(Player player) {
		this.player = player;
	}


	public String toString(){
		return "Piece coloured "+this.getColor()+" positioned in "+this.getPosition().toString();
	}

	/**
	 * Compare 2 pieces depending on their timeState
	 * @param o Object to compare
	 * @return -1 if the timeState of this is <= timeState of p, else 1
	 */
	public int compareTo(Object o) {
		
		int result;
		
		Piece p = (Piece) o;
		
		if(this.getTimeState().compareTo(p.getTimeState()) < 1)
			result = -1;
		else
			result = 1;
			
		
		
		return result;
	}
	
	
	public void addWeeks(int nb){
		
		Calendar calendar = this.getTimeState();
		calendar.add(Calendar.WEEK_OF_YEAR, nb);
		this.setTimeState(calendar);
		
	}

	
	

}
