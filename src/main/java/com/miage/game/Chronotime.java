
package com.miage.game;

import com.miage.utils.ConfigManager;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Properties;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxime
 */
public class Chronotime implements Serializable{
    
    private final static Logger LOGGER = LogManager.getLogger(Chronotime.class.getName());
    
    private HashMap<Integer, HashMap<Integer, Integer>> values ;

    /**
     * <br/>- Init the inner chronotimes values with the config manager
     * @throws IOException 
     */
    public Chronotime() throws IOException {
        
        this.values = new HashMap();
        Properties configProperties = ConfigManager.getInstance().getConfig( ConfigManager.GENERAL_CONFIG_NAME );
        
        // We iterate over each chronotime.(number)
        for( String key : ConfigManager.getInstance().getConfigEntriesWithKeysBeginningBy( ConfigManager.GENERAL_CONFIG_NAME, "chronotime.knowledge").keySet()){
            
            HashMap<Integer, Integer> subValues = new HashMap();
            
            Integer numberOfKnowledge = Integer.parseInt(key.substring( "chronotime.knowledge.".length() ));
            
            // We get all the weeks with their associated number of token allowed to pick up
            String[] nbTokensToPickUp = configProperties.getProperty( "chronotime.knowledge." + numberOfKnowledge).split("\\|");
            for (int i = 0; i < nbTokensToPickUp.length; i++) {
                subValues.put( i+1, Integer.parseInt(nbTokensToPickUp[i]) );
            }
            
            // We put all the weeks with their associated tokens inside the current knowledge range
            this.values.put( numberOfKnowledge, subValues);
        }
    }
    
    /**
     * Return the number tokens to pick up relating to the given number knowledge point and weeks
     * @param nbKnowIedgePoint
     * @param nbWeeks
     * @return 
     */
    public int getNbTokensToPickUp( int nbKnowIedgePoint, int nbWeeks){
        LOGGER.debug("getNbTokensToPickUp: nbKnowIedgePoint="+nbKnowIedgePoint+" nbWeeks="+nbWeeks);
        try{
            if( nbKnowIedgePoint > 12) nbKnowIedgePoint = 12;
            return this.values.get( nbKnowIedgePoint ).get( nbWeeks );
        }
        catch( NullPointerException ex ){
            throw new NullPointerException("Please provide a valid range for the chronotime");
        }
    }
   
    
}
