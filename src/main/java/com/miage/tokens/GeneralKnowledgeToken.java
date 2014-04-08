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

package com.miage.tokens;

import Interface.KnowledgeElement;
import Interface.UsableElement;

/**
 *
 * @author maxime
 */
public class GeneralKnowledgeToken extends Token implements KnowledgeElement, UsableElement{

    private final int value;
    
    public GeneralKnowledgeToken(String id, String name, String color, int value) {
        super(id, name, color);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int getKnowledgePoints() {
        return value;
    }
    
    
}
