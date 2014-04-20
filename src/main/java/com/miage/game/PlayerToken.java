package com.miage.game;

import com.miage.gui.PlayerTokenPosition;
import java.time.LocalDate;

import com.miage.areas.Area;
import com.miage.cards.Card;
import java.io.Serializable;

/**
 * 
 * @author maxime
 */
public class PlayerToken implements Comparable, Serializable{
	
    private String color;

    /**
     * Define the actual position of the player token ( an area )
     */
    private Area position;

    /**
     * Define the current Date (duration of playing) of the player
     */
    private LocalDate timeState;
    
    /**
     * Represent the position of the temporal player token on the board
     */
    private PlayerTokenPosition temporalPosition;

    /**
     * Use this constructeur when you do not need the complete init of the token
     * @param color 
     */
    public PlayerToken(String color){
        this(color, null, null, null);
    }

    /**
     * 
     * @param color
     * @param position
     * @param timeState 
     * @param temporalPosition
     */
    public PlayerToken(String color, Area position, LocalDate timeState, PlayerTokenPosition temporalPosition) {
        this.color = color;
        this.position = position;
        this.timeState = timeState;
    }
        
    
    
    /***********************************************************************************************
     *
     *                                  Public Methods
     * 
     ***********************************************************************************************/

    @Override
    public String toString() {
        return "PlayerToken{" + "color=" + color + ", position=" + position + ", timeState=" + timeState + '}';
    }

    /**
     * Compare 2 pieces depending on their timeState.
     * <br/>If the coming token has the same position no token move
     * <br/>If the coming token has a upper position then it move after this token
     * <br/>If the coming token has a lower position it move before
     * @param o Object to compare
     * @return int
     */
    @Override
    public int compareTo(Object o) {
        int result;
        PlayerToken p = (PlayerToken) o;
        if(this.getTimeState().isAfter(p.getTimeState()))
            result = 1;
        else if(this.getTimeState().equals( p.getTimeState() )){
            result = -1;
        }
        else{
            result = -1;
        }
        return result;
    }
	
    /**
     * Add some weeks at the timeState.
     * @param nb number of weeks to add
     */
    public void addWeeks(int nb){
        this.setTimeState(this.timeState.plusWeeks(nb));
    }
    
    /**
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
     * @deprecated 
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
     * Move the position of the token
     * Effect:
     *  - update position
     *  - update timeStage relating to (zepellin and car)
     * @param destinationArea
     * @param useZeppelin
     * @return 
     */
    public String[] movePlayerToken(Area destinationArea, boolean useZeppelin, boolean useCardCard){

        String[] steps = this.getPosition().getDistanceAreasSteps( destinationArea.getName() );

        this.setPosition( destinationArea );

        if(!useZeppelin){
            if(steps.length+1 >= 3){
                if( useCardCard )
                    this.addWeeks(steps.length);
                else
                    this.addWeeks(steps.length+1);
            }
            else
                this.addWeeks(steps.length+1);
        }
        return steps;
    }
    
    
        
        
	
    /***********************************************************************************************
     *
     *                                  Getter & Setter
     * 
     ***********s************************************************************************************/
        
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

    public PlayerTokenPosition getTemporalPosition() {
        return temporalPosition;
    }
	
    

}
