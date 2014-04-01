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
    private String name;
    
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Override

    public String toString() {
        return this.getName();

    }

    public HashMap<String, String[]> getDistances() {
        return distances;
    }
    
    public void setDistances(HashMap<String, String[]> distances){
    	this.distances = distances;
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

}
