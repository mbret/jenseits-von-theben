

package com.miage.areas;

import java.util.HashMap;


/**
 * Places allowed for moving
 * 
 * @author maxime
 * @version 1.0
 */
public abstract class Area {

    /**
     * Used as id from .properties
     * used to retrieve the image file
     */
    private int id;
    
    /**
     * Used as key inside program
     */
    private final String name;
    
    /**
     * Used for graphical display
     */
    private String displayName;
    
    /**
     * distance with other Areas
     */
    private HashMap<String, String[]> distances;
    
    /**
     * 
     * @param id
     * @param name 
     */
    public Area(int id, String name){
        this.name = name;
        this.id = id;
        this.distances = new HashMap<String, String[]>();
    }

    
    /***********************************************************************************************
     *
     *                                  Methods
     * 
     ***********************************************************************************************/
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return this.getName();

    }
    
    /**
     * 
     * @author Gael
     * 
     * return the table of steps between two areas
     * 
     * @param nameOfDestinationArea
     * @return
     */
    public String[] getDistanceAreasSteps(String nameOfDestinationArea){
    	return this.distances.get(nameOfDestinationArea);
    }
    
    /**
     * Return the weekcost between this area and the provided area
     * 
     * @param destinationAreaName
     * @return 
     */
    public int getDistanceWeekCostTo( String destinationAreaName ){
        if(destinationAreaName.equals(this.name)) return 0;
        return this.distances.get( destinationAreaName ).length + 1; // lengh of steps + the last (destinationAreaName)
    }
    
    
    
    /***********************************************************************************************
     *
     *                                  Getter & Setter
     * 
     ***********************************************************************************************/
    
    /**
     * 
     * @return 
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public HashMap<String, String[]> getDistances() {
        return distances;
    }
    
    public void setDistances(HashMap<String, String[]> distances){
    	this.distances = distances;
    }

}
