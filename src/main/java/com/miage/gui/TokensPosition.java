package com.miage.gui;

import com.miage.areas.Area;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.LogManager;

/**
 * 
 * 
 * 
 * 
 * 
 */

public class TokensPosition {
	
	private final static org.apache.log4j.Logger LOGGER = LogManager.getLogger(TokensPosition.class.getName());
        
	/**
	 *  map of the position (x,y) of areas
	 */
	private static Map<String, Point> positionsArea = new HashMap(){{
            this.put("london", new Point(130,125));
            this.put("paris", new Point(180,225));
            this.put("rome", new Point(300,345));
            this.put("berlin", new Point(330,150));
            this.put("vienna", new Point(400,280));
            this.put("warsaw", new Point(460,175));
            this.put("moscow", new Point(600,110));
            this.put("greece", new Point(500,420));
            this.put("crete", new Point(500,540));
            this.put("egypt", new Point(600,655));
            this.put("palestine", new Point(700,645));
            this.put("mesopotamia", new Point(800,530));
        }};
        
        private static final Map<Integer, Point> timeTokenPosition = new HashMap(){{
            this.put(1901, new Point(8,120));
            this.put(1902, new Point(8,60)); 
            this.put(1903, new Point(8,10));
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

        
        
        private TokensPosition() {
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
                    x_base = TokensPosition.x_square1;
                    y_base = TokensPosition.y_square1;
                }
                else{
                    x_base = TokensPosition.x_squareStart;
                    y_base = TokensPosition.y_squareStart;
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
		
                return new Point(x_base + decalMultiplier * TokensPosition.decal, y_base);
	}
	
	
	
	/**
	 * 
	 * Initalization of the coord of the differents areas
	 * 
     * @param area
     * @param decalMultiplier
	 */
	public static Point positionDependingOnArea( String area, int decalMultiplier){
            Point p = TokensPosition.positionsArea.get(area);
            return new Point(p.x + decalMultiplier * TokensPosition.decal, p.y);
		
	}
	
        
        public static Point positionDependingOnYear( Integer year ){
            return TokensPosition.timeTokenPosition.get(year);
        }
}
