/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.miage.game;

import com.miage.config.ConfigManager;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 *
 * @author Maxime
 */
public class Chronotime {
    
    private HashMap<Integer, HashMap<Integer, Integer>> values;

    /**
     * <br/>- Init the inner chronotimes values with the config manager
     * @param values
     * @throws IOException 
     */
    public Chronotime() throws IOException {
        
        this.values = new HashMap();
        Properties configProperties = ConfigManager.getInstance().getConfig( ConfigManager.GENERAL_CONFIG_NAME );
        
        // We iterate over each chronotime.(number)
        for( String key : ConfigManager.getInstance().getConfigEntriesWithKeysBeginningBy( ConfigManager.GENERAL_CONFIG_NAME, "chronotime.knowledge").keySet()){
            
            HashMap<Integer, Integer> subValues = new HashMap();

            Integer numberOfKnowledge = Integer.parseInt(key.substring( "chronotime.knowledge.".length() ));
            
            // We get all the weeks with their associated numbe rof token allowed to pick up
            String[] nbTokensToPickUp = configProperties.getProperty( "chronotime.knowledge." + numberOfKnowledge).split("\\|");
            for (int i = 0; i < nbTokensToPickUp.length; i++) {
                subValues.put( i, Integer.parseInt(nbTokensToPickUp[i]) );
            }
                
            // We put all the weeks with their associated tokens inside the current knowledge range
            this.values.put( numberOfKnowledge, subValues);
        }
        this.values = values;
    }
    
    /**
     * Return the number tokens to pick up relating to the given number knowledge point and weeks
     * @param nbKnowIedgePoint
     * @param nbWeeks
     * @return 
     */
    public int getNbTokensToPickUp( int nbKnowIedgePoint, int nbWeeks){
        return this.values.get( nbKnowIedgePoint ).get( nbWeeks );
    }
   
    
}
