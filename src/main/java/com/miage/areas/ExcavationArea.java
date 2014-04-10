

package com.miage.areas;

import java.util.Collections;
import java.util.LinkedList;
import com.miage.tokens.Token;
import java.io.Serializable;

/**
 * Excavation area
 * 
 * @author maxime
 * @version 1.0
 */
public class ExcavationArea extends Area implements Serializable{
	
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
     * @param id
     * @param name
     * @param codeColor
     */
    public ExcavationArea(int id, String name, String codeColor){
        this(id, name, codeColor, new LinkedList<Token>());
    }
    
    /**
     * 
     * @param id
     * @param name
     * @param codeColor
     * @param tokenList 
     */
    public ExcavationArea(int id, String name, String codeColor, LinkedList<Token> tokenList){
        super(id, name);
        this.codeColor = codeColor;
        this.tokenList = tokenList;
    }


    /***********************************************************************************************
     *
     *                                  Methods
     * 
     ***********************************************************************************************/
    
    /**
     * Add a new token inside this area
     * @param token 
     */
    public void addToken(Token token){
        this.tokenList.add(token);
    }

    /**
     * Pick the first token in the list
     * @return 
     */
    public Token pickToken(){
        return this.tokenList.removeFirst();
    }

    /**
     * Mix the token list randomly
     */
    public void mixTokenList(){
        Collections.shuffle(this.tokenList);
    }

    
    /***********************************************************************************************
     *
     *                                  Getter & Setter
     * 
     ***********************************************************************************************/
    
    public String getCodeColor() {
        return codeColor;
    }

    public void setCodeColor(String codeColor) {
        this.codeColor = codeColor;
    }

    public LinkedList<Token> getTokenList() {
        return tokenList;
    }

    public void setTokenList(LinkedList<Token> tokenList) {
        this.tokenList = tokenList;
    }

    
}
