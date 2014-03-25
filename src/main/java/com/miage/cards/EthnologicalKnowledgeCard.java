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
public class EthnologicalKnowledgeCard extends Card{
    
    private int value;
    private String excavationAreaName;
    private String codeColor;

    public EthnologicalKnowledgeCard(String areaName, int weekCost, int value, String paramExcavationAreaName) {
            super("ethnologicalKnowledge",areaName,weekCost);
            this.value = value;
            this.excavationAreaName = paramExcavationAreaName;
            
            
            if(excavationAreaName.compareTo("greece") == 0)
            	this.codeColor = "#ff5b2b";
            else if(excavationAreaName.compareTo("crete") == 0)
            	this.codeColor = "#895959";
            else if(excavationAreaName.compareTo("egypt") == 0)
            	this.codeColor = "#fff168";
            else if(excavationAreaName.compareTo("palestine") == 0)
            	this.codeColor = "#b7ca79";
            else if(excavationAreaName.compareTo("mesopotamia") == 0)
            	this.codeColor = "#375d81";
            	
            
    }

    public int getValue() {
            return value;
    }

    public String getCodeColor() {
        return codeColor;
    }

    @Override
    public boolean isDiscardable() {
        return false;
    }
    
    
    public String getExcavationAreaName() {
		return excavationAreaName;
	}

	public String toString(){
    	return super.toString()+","+value+","+excavationAreaName;
    }
}
