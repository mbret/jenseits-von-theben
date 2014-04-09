package com.miage.game;

import java.time.LocalDate;

import com.miage.areas.Area;
import com.miage.cards.Card;

/**
 * 
 * @author maxime
 */
public class PlayerToken implements Comparable{
	
	private String color;
        
        /**
         * Define the actual position of the player token ( an area )
         */
	private Area position;
        
        /**
         * Define the current Date (duration of playing) of the player
         */
	private LocalDate timeState;
	
        
	public PlayerToken(String color){
            this.color = color; 
	}

        /**
         * @author maxime
         * @return 
         */
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
            return (int) (Math.ceil(this.timeState.getDayOfYear()/7));
	}
	
	
	/**
	 * @author Gael
	 * return the current year of the playerToken
	 * @return
	 */
	public int getCurrentYear(){
            return this.timeState.getYear();
	}
	
	
	/**
	 * @author Gael
	 * 
	 * Move a playerToken by going in all the steps Area on the pass, add cost of the move depending on zeppelin or car cards
	 * the player owns
	 * 
	 * @param destinationArea 
	 * @param board
	 * @param useZeppelin
	 * @return the table of steps
	 */
	public String[] move(String destinationArea, Board board, boolean useZeppelin){
		
		String[] steps = this.getPosition().getDistanceAreasSteps(destinationArea);
		
		for(String step : steps){
			this.setPosition(board.getArea(step));
		}
		
		this.setPosition(board.getArea(destinationArea));
		
		if(!useZeppelin){
			if(steps.length+1 >= 3){
				if(board.getPlayerTokensAndPlayers().get(board.getCurrentPlayerToken()).getCompetences().get("car") > 0)
					board.getCurrentPlayerToken().addWeeks(steps.length);
				else
					board.getCurrentPlayerToken().addWeeks(steps.length+1);
			}
			else
				board.getCurrentPlayerToken().addWeeks(steps.length+1);
		}
	
			
		
		return steps;
		
	}
	
	
	/**
	 * @author Gael
	 * 
	 * add weeks at the playerToken depending on the card picked
	 * 
	 * @param card 
	 */
	public void addWeeksPlayerToken(Card card){
		
		addWeeks(card.getWeekCost());
		
	}
        
        
	
	
	

	
	

}
