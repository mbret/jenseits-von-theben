package com.miage.game;

/**
 * 
 * 
 * 
 * 
 * @author Gaël
 */

public class Position {
	
	// Absciss of the player token on the board
	private int x;
	
	// Ordinate of the player token on the board
	private int y;
	
	private final static int TOP_LEFT = 1;
	private final static int TOP_RIGHT = 16;
	private final static int BOTTOM_RIGHT = 27;
	private final static int BOTTOM_LEFT = 42;
	
	private final static int LENGTH = 15;
	private final static int HEIGHT = 11;
	private final static int SPACE_BETWEEN_SQUARE = 63;
	
	
	
	
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}

	
	/**
	 * Method which update the position of the player token depending on the timestate of the player token
	 * 
	 * @param weeks number of weeks of the player
	 */
	public void positionDependingOnWeeks(int weeks){
		
		Position basePosition = new Position(70,37);
		
		if(weeks >= TOP_LEFT && weeks <= TOP_RIGHT)
			basePosition.x += (weeks-1)*SPACE_BETWEEN_SQUARE;
		
		if(weeks > TOP_RIGHT && weeks <= BOTTOM_RIGHT){
			basePosition.x += LENGTH*SPACE_BETWEEN_SQUARE;
			basePosition.y += ((weeks-1)-LENGTH)*SPACE_BETWEEN_SQUARE;
		}
		
		if(weeks > BOTTOM_RIGHT && weeks <= BOTTOM_LEFT){
			basePosition.x += (LENGTH-((weeks-1)-LENGTH-HEIGHT))*SPACE_BETWEEN_SQUARE;
			basePosition.y += HEIGHT*SPACE_BETWEEN_SQUARE;
		}
		
		if(weeks > BOTTOM_LEFT)
			basePosition.y += (HEIGHT-((weeks-1)-(2*LENGTH)-HEIGHT))*SPACE_BETWEEN_SQUARE;
		
	
		this.x = basePosition.getX();
		this.y = basePosition.getY();
		
	}
	
	
	
    /***********************************************************************************************
    *
    *                                  Getter & Setter
    * 
    *  
   ************************************************************************************************/
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	
	

}
