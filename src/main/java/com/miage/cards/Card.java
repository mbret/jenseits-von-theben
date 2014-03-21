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

package com.miage.cards;
import com.miage.areas.Area;

/**
 * Card
 * 
 * @author maxime
 */
public abstract class Card {
	
    /**
     * name: used as key inside program
     */
    private String name;
    
    /**
     * displayName: name used graphical
     */
    private String displayName;
    
    private String areaName;
    
    
    private int weekCost = 0;

    /**
     * 
     * @param name
     * @param areaName
     * @param weekCost 
     */
    public Card(String name, String areaName, int weekCost){
            this.name = name;
            this.areaName = areaName;
            this.weekCost = weekCost;
    }
    
    /**
     * 
     * @param name
     * @param areaName 
     */
    public Card(String name, String areaName){
            this.name = name;
            this.areaName = areaName;
    }

    public String getName() {
            return name;
    }

    public int getWeekCost() {
        return weekCost;
    }

    public void setWeekCost(int weekCost) {
        this.weekCost = weekCost;
    }


    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    
    abstract public boolean isDiscardable();

        
}
