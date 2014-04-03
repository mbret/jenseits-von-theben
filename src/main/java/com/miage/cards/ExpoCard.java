package com.miage.cards;
import java.util.ArrayList;
import java.util.List;
import com.miage.tokens.PointToken;

/**
 * 
 * @author maxime
 */
public class ExpoCard extends Card {
	
    /**
     * Define wheter the exposition is big or not
     */
    private final boolean bigExpo;
    
    /**
     * 
     */
    private final int value;
    
    /**
     * 
     */
    private List<PointToken> tokens;

    
    public ExpoCard(int id, String displayName, String areaName, int weekCost,  boolean bigExpo, int value) {
        super(displayName, areaName, id, weekCost);
        this.bigExpo = bigExpo;
        this.tokens = new ArrayList<PointToken>();
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
    public String toString(){
       return super.toString()+","+value;
    }
    
    

    @Override
    public boolean isDiscardable() {
        return false;
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
    public List<PointToken> getTokens() {
        return tokens;
    }

    public void setTokens(List<PointToken> tokens) {
        this.tokens = tokens;
    }

    public boolean isBigExpo() {
        return bigExpo;
    }

    public int getValue() {
        return value;
    }

    
}
