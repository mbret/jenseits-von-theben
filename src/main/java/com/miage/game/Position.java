package com.miage.game;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * 
 * 
 * @author Gaël
 */

public class Position {
	
	/**
	 *  Abscissa of the player token on the board
	 */
	private int xTemporal;
	
	/**
	 *  Ordinate of the player token on the board
	 */
	private int yTemporal;
	
	/**
	 *  map of the position (x,y) of areas
	 */
	private static Map<String, Position> positionsArea = new HashMap<String, Position>();
	
	
	
	/**
	 * Constant variables
	 */
	private final static int TOP_LEFT = 1;
	private final static int TOP_RIGHT = 16;
	private final static int BOTTOM_RIGHT = 27;
	private final static int BOTTOM_LEFT = 42;
	
	private final static int LENGTH = 15;
	private final static int HEIGHT = 11;
	private final static int SPACE_BETWEEN_SQUARE = 63;
	
	
	
	

	public Position(int xTemporal, int yTemporal){
		this.xTemporal = xTemporal;
		this.yTemporal = yTemporal;
		
	}

	
	/**
	 * Method which update the position of the player token depending on the timestate of the player token
	 * 
	 * @param weeks number of weeks of the player
	 */
	public void positionDependingOnWeeks(int weeks){
		
		Position basePosition = new Position(70,37);
		
		
		if(weeks >= TOP_LEFT && weeks <= TOP_RIGHT)
			basePosition.xTemporal += (weeks-1)*SPACE_BETWEEN_SQUARE;
		
		if(weeks > TOP_RIGHT && weeks <= BOTTOM_RIGHT){
			basePosition.xTemporal += LENGTH*SPACE_BETWEEN_SQUARE;
			basePosition.yTemporal += ((weeks-1)-LENGTH)*SPACE_BETWEEN_SQUARE;
		}
		
		if(weeks > BOTTOM_RIGHT && weeks <= BOTTOM_LEFT){
			basePosition.xTemporal += (LENGTH-((weeks-1)-LENGTH-HEIGHT))*SPACE_BETWEEN_SQUARE;
			basePosition.yTemporal += HEIGHT*SPACE_BETWEEN_SQUARE;
		}
		
		if(weeks > BOTTOM_LEFT)
			basePosition.yTemporal += (HEIGHT-((weeks-1)-(2*LENGTH)-HEIGHT))*SPACE_BETWEEN_SQUARE;
		
	
		this.xTemporal = basePosition.getXTemporal();
		this.yTemporal = basePosition.getYTemporal();
		
	}
	
	
	
	
	/**
	 * 
	 * Initalization of the coord of the differents areas
	 * 
	 */
	public void initAreaPositions(){
		
		positionsArea.put("london", new Position(200,125));
		positionsArea.put("paris", new Position(235,235));
		positionsArea.put("rome", new Position(345,355));
		positionsArea.put("berlin", new Position(385,170));
		positionsArea.put("vienna", new Position(440,280));
		positionsArea.put("warsaw", new Position(510,195));
		positionsArea.put("moscow", new Position(660,125));
		positionsArea.put("greece", new Position(530,420));
		positionsArea.put("crete", new Position(590,540));
		positionsArea.put("egypt", new Position(670,655));
		positionsArea.put("palestine", new Position(795,645));
		positionsArea.put("mesopotamia", new Position(910,530));
	}
	
	
    /***********************************************************************************************
    *
    *                                  Getter & Setter
    * 
    *  
   ************************************************************************************************/
	
	public int getXTemporal() {
		return xTemporal;
	}

	public int getYTemporal() {
		return yTemporal;
	}


	public static Map<String, Position> getPositionsArea() {
		return positionsArea;
	}
	
	
	
	
	

}
