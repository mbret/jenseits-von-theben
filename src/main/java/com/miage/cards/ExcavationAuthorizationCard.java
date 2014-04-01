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
 * Allow user to make a supplementary excavation action during the current year
 * @author maxime
 */
public class ExcavationAuthorizationCard extends Card{

    public ExcavationAuthorizationCard(int id, String areaName, int weekCost) {
        super(id, "excavationAuthorization", areaName, weekCost);
    }

    @Override
    public boolean isDiscardable() {
        return true;
    }
    
    
    
}
