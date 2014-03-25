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
     * Number (used inside distance array)
     */
    private Integer num;
    
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
     * @param num
     * @param name 
     */
    public Area(Integer num, String name){
        this.name = name;
        this.num = num;
        this.distances = new HashMap<String, String[]>();
    }


    public Integer getNum() {
        return num;
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
    public String[] distance(String nameOfDestinationArea){
    	
    	return this.distances.get(nameOfDestinationArea);
    	
    }

}
