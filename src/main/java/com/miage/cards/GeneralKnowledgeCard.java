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

/**
 * 
 * @author maxime
 */
public class GeneralKnowledgeCard extends Card{
	
    /**
     * 
     */
    private final int value;

    public GeneralKnowledgeCard(int id, String displayName, String areaName, int weekCost, int value) {
            super(id, displayName, areaName, weekCost);
            this.value = value;
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
    public boolean isDiscardable() {
        return false;
    }
    
    @Override
    public String toString(){
    	return super.toString()+","+value;
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
    public int getValue() {
        return value;
    }


    
}
