package com.miage.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.ini4j.Ini;

/**
 * Class ConfigLoader allow program to load properties from .properties files
 * /!\ Class Singleton
 * 
 *  - Use init() method to catch clearly and just one time exceptions due to unknown or invalid path (for exemple in start of static main)
 *    Otherwise init() is call each time it's needed
 * 
 * @author Maxime
 */
public class ConfigManager {
 
    // use the classname for the logger, this way you can refactor
    private final static Logger LOGGER = LogManager.getLogger(ConfigManager.class.getName());

    private static ConfigManager instance;
    
    private final String configFilename = "config.properties";
    private final String optionsFilename = "src/options.ini";
    private Properties config;
    private Ini options;

    private ConfigManager() throws IOException {
        this.init();
    }
    
    
    /**
     * Init loading of all files
     * @throws java.io.IOException
     */
    public void init() throws IOException{
        // Load config.properties
        if( this.config == null){
            this.config = new Properties();
            InputStream input = null;
            try {

                input = ClassLoader.getSystemResourceAsStream( this.configFilename );
                
                if(input == null){
                    throw new FileNotFoundException("property file '" + this.configFilename + "' not found in the classpath");
                }

                //load a properties file from class path, inside static method
                this.config.load( input );
            } 
            catch (IOException e) {
                throw new IOException(e);
            } 
            finally{
                if(input != null){
                    input.close();
                }
            }
        }
        
        // Load ini file
        if( this.options == null ){
            this.options = new Ini( new File( this.optionsFilename ) );
        }
    }
    
    
    /**
     * Return the unique instance of ConfigManager
     * @return 
     */
    public static ConfigManager getInstance() throws IOException{
        if(ConfigManager.instance == null){
            ConfigManager.instance = new ConfigManager();
        }
        return ConfigManager.instance;
    }
    
    
    /**
     * Retourne the config properties file
     * 
     * @return Properties file
     * @throws java.io.IOException
     */
    public Properties getConfig(){
        return this.config;
    }
    
//    public <T extends Object> T getConfigValue( String key, Class<T> desiredClass){
//        Class<T extends Object> theClass = desiredClass;
//        return (T)(desiredClass.getClass().cast( this.config.get(key) ));
//    }
    
    public ArrayList<String> getConfigKeysBeginningBy( String beginning ){
        ArrayList<String> keys = new ArrayList<String>();
        Enumeration<Object> result = config.keys();
        while(result.hasMoreElements()){
            String param = (String) result.nextElement();
            if( param.startsWith( beginning )){
                keys.add(param);
            }
        }
        return keys;
    }
    
    
    
    /**
     * Return options.ini
     * 
     * @return 
     * @throws java.lang.Exception 
     */
    public Ini getOptions(){
        return this.options;
    }
    
    
    
    
    
    /**
     * Return the desired value 
     * 
     * @param <T>
     * @param section
     * @param key
     * @param desiredClass ( if you want a double for exemple: double.class )
     * @return T value
     * @throws IOException 
     */
    public <T extends Object> T getOptionValue( String section, String key, Class<T> desiredClass){
        return this.options.get(section, key, desiredClass);
    }
    
    /**
     * Add or replace the option inside options.ini
     * 
     * @param section
     * @param key
     * @param value 
     * @throws java.lang.Exception 
     */
    public void setOption(String section, String key, String value) throws IOException{
        this.options.put(section, key, value); // add or replace
        this.options.store(); // store new data inside file
    }
    
    
}