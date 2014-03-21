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
import java.util.ArrayList;
import java.util.List;
import com.miage.tokens.PointToken;

/**
 * 
 * @author maxime
 */
public class ExpoCard extends Card {
	
    private boolean bigExpo;
    private int value;
    private List<PointToken> tokens;

    public ExpoCard(String name, String areaName,int cost,  boolean bigExpo) {
        super(name, areaName, cost);
        this.bigExpo = bigExpo;
        this.tokens = new ArrayList<PointToken>();
    }

    public int getValue(){
        return value;
    }

    public boolean getBigExpo(){
        return bigExpo;
    }

    public String toString(){
        return "Exposition "+super.toString();
    }

    @Override
    public boolean isDiscardable() {
        return false;
    }
    
}
