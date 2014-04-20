package com.miage.gui;

import com.miage.areas.Area;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * 
 * 
 * 
 */

public class PlayerTokenPosition {
	
	
	/**
	 *  map of the position (x,y) of areas
	 */
	private static Map<String, Point> positionsArea = new HashMap(){{
            this.put("london", new Point(180,125));
            this.put("paris", new Point(215,235));
            this.put("rome", new Point(325,355));
            this.put("berlin", new Point(365,170));
            this.put("vienna", new Point(420,280));
            this.put("warsaw", new Point(490,195));
            this.put("moscow", new Point(640,125));
            this.put("greece", new Point(510,420));
            this.put("crete", new Point(570,540));
            this.put("egypt", new Point(650,655));
            this.put("palestine", new Point(775,645));
            this.put("mesopotamia", new Point(890,530));
        }};
        
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
	
	private final static int x_square1 = 40;
        private final static int y_square1 = 20;
	
        private final static int x_squareStart = 80;
        private final static int y_squareStart = 55;
        
        private final static int decal = 6;

        
        
        private PlayerTokenPosition() {
        }

	
	/**
	 * Method which update the position of the player token depending on the timestate of the player token
	 * 
	 * @param weeks number of weeks of the player
     * @param decalMultiplier
         * @return 
	 */
	public static Point positionDependingOnWeeks(int weeks, int decalMultiplier ){
                int x_base;
                int y_base;
                
                if( weeks > 0){
                    x_base = PlayerTokenPosition.x_square1;
                    y_base = PlayerTokenPosition.y_square1;
                }
                else{
                    x_base = PlayerTokenPosition.x_squareStart;
                    y_base = PlayerTokenPosition.y_squareStart;
                }
		
		if(weeks >= TOP_LEFT && weeks <= TOP_RIGHT)
			x_base += (weeks-1)*SPACE_BETWEEN_SQUARE;
		
		if(weeks > TOP_RIGHT && weeks <= BOTTOM_RIGHT){
			x_base += LENGTH*SPACE_BETWEEN_SQUARE;
			y_base += ((weeks-1)-LENGTH)*SPACE_BETWEEN_SQUARE;
		}
		
		if(weeks > BOTTOM_RIGHT && weeks <= BOTTOM_LEFT){
			x_base += (LENGTH-((weeks-1)-LENGTH-HEIGHT))*SPACE_BETWEEN_SQUARE;
			y_base += HEIGHT*SPACE_BETWEEN_SQUARE;
		}
		
		if(weeks > BOTTOM_LEFT)
			y_base += (HEIGHT-((weeks-1)-(2*LENGTH)-HEIGHT))*SPACE_BETWEEN_SQUARE;
		
                return new Point(x_base + decalMultiplier * PlayerTokenPosition.decal, y_base);
	}
	
	
	
	/**
	 * 
	 * Initalization of the coord of the differents areas
	 * 
	 */
	public static Point positionDependingOnArea( String area, int decalMultiplier){
            Point p = PlayerTokenPosition.positionsArea.get(area);
            p.x += decalMultiplier * PlayerTokenPosition.decal;
            return p;
		
	}
	
}
