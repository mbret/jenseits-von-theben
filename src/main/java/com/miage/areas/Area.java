

package com.miage.areas;

import java.io.Serializable;
import java.util.HashMap;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * Places allowed for moving
 * 
 * @author maxime
 * @version 1.0
 */
public abstract class Area implements Serializable {

    private final static Logger LOGGER = LogManager.getLogger(Area.class.getName());
    
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
     * @param displayName 
     */
    public Area(int id, String name, String displayName){
        this.name = name;
        this.id = id;
        this.displayName = displayName;
        this.distances = new HashMap();
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
     * @return String[]
     */
    public String[] getDistanceAreasSteps(String nameOfDestinationArea){
        LOGGER.debug("getDistanceAreasSteps: from " + this.name + " to " + nameOfDestinationArea);
        String[] steps = this.distances.get(nameOfDestinationArea);
        LOGGER.debug("getDistanceAreasSteps: nbSteps = " + steps.length);
    	return steps;
    }
    
    /**
     * Return the weekcost between this area and the provided area
     * 
     * @param destinationAreaName
     * @return int
     */
    public int getDistanceWeekCostTo( String destinationAreaName ){
        LOGGER.debug("getDistanceWeekCostTo: from " + this.name + " to " + destinationAreaName);
        int value;
        if(destinationAreaName.equals(this.name)){
            value = 0;
        }
        else{
            value = this.distances.get( destinationAreaName ).length + 1; // lengh of steps + the last (destinationAreaName)
        }
        LOGGER.debug("getDistanceWeekCostTo: cost = " + value);
        return value;
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

    public String getDisplayName() {
        return displayName;
    }

    
}
