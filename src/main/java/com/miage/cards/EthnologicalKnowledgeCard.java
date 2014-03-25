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
    private String codeColor;

    public EthnologicalKnowledgeCard(String areaName, int weekCost, int value, String codeColor) {
            super("ethnologicalKnowledge",areaName,weekCost);
            this.value = value;
            this.codeColor = codeColor;
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
    
    public String toString(){
    	return super.toString()+","+value+","+codeColor;
    }
}
