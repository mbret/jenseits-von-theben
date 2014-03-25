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

import java.util.Collections;
import java.util.LinkedList;
import com.miage.tokens.Token;

/**
 * Excavation area
 * 
 * @author maxime
 * @version 1.0
 */
public class ExcavationArea extends Area {
	
    /**
     * codeColor: easiest than color name. Can be used with graphical api
     */
    private String codeColor;
    
    /**
     * linked list because of storage without condition, work with firt element only and few element inside
     */
    private LinkedList<Token> tokenList; 

    /**
     * 
     * @param num
     * @param name
     * @param color 
     */
    public ExcavationArea(int num, String name, String codeColor){
        super(num, name);
        this.codeColor = codeColor;
        this.tokenList = new LinkedList<Token>();
    }
    
    /**
     * 
     * @param num
     * @param name
     * @param color
     * @param tokenList 
     */
    public ExcavationArea(int num, String name, String codeColor, LinkedList<Token> tokenList){
        super(num, name);
        this.codeColor = codeColor;
        this.tokenList = tokenList;
    }


    public void addToken(Token token){
        this.tokenList.add(token);
    }

    /**
     * Pick a token in the list
     * @return 
     */
    public Token pickToken(){
        return this.tokenList.removeFirst();
    }

    /**
     * Mix the list randomly
     */
    public void mixTokenList(){
        Collections.shuffle(this.tokenList);
    }

    public String getCodeColor() {
        return codeColor;
    }

    public LinkedList<Token> getTokenList() {
        return tokenList;
    }

    
    @Override
    public String toString() {
        return "ExcavationArea{" + super.toString() + "codeColor=" + codeColor + ", tokenList=" + tokenList + '}';
    }
	
    
    
}
