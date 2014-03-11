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

package cards;
import areas.Area;

/**
 * Card
 * 
 * @author maxime
 */
public abstract class Card {
	
    private String name;
    private String areaName;
    private int cost = 0;

    /**
     * 
     * @param name
     * @param areaName
     * @param cost 
     */
    public Card(String name, String areaName, int cost){
            this.name = name;
            this.areaName = areaName;
            this.cost = cost;
    }

    public String getName() {
            return name;
    }

    public int getCost() {
            return cost;
    }

    public void setCost(int c){
            this.cost = c;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public String toString() {
        return "Card{" + "name=" + name + ", areaName=" + areaName + ", cost=" + cost + '}';
    }
    
    

        
}
