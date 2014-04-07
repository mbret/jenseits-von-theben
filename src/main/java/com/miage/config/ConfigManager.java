package com.miage.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
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
 
    private final static Logger LOGGER = LogManager.getLogger(ConfigManager.class.getName());

    /**
     * Singleton
     */
    private static ConfigManager instance;
    
    private final String optionsFilename = "src/options.ini";
    private Ini options;
    
    /**
     * Contain all loaded config files, they are added dynamically when asked
     */
    private HashMap<String, Properties> configs;

    public final static String GENERAL_CONFIG_NAME = "config.properties";
    public final static String AREAS_CONFIG_NAME = "areas.properties";
    public final static String CARDS_CONFIG_NAME = "cards.properties";
    
    
    
    private ConfigManager() throws IOException {
        this.configs = new HashMap<String, Properties>();
        this.loadAll();
    }
    
    
    /***********************************************************************************************
     *
     *                                  Methods
     * 
     ***********************************************************************************************/
    
    /**
     * Load the specified config file. Use static Board name as parameter (Board.NAME_OF_CONFIG)
     * @param name
     * @throws IOException 
     */
    private void loadConfigFile( String name ) throws IOException{
        LOGGER.debug("loadConfigFile: load config file " + name);
        InputStream input = null;
        try {
            input = ClassLoader.getSystemResourceAsStream( name );
            if(input == null){
                throw new FileNotFoundException("property file '" + name + "' not found in the classpath");
            }
            this.configs.put( name, new Properties()); // add new properties
            this.configs.get( name ).load( input ); // load file
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
    
    /**
     * Init loading of all config files
     * Useful to try/catch just one time this class
     * @throws java.io.IOException
     */
    public void loadAll() throws IOException{
        LOGGER.debug("loadAll: Load all configs and otpions files");
        this.loadConfigFile( ConfigManager.GENERAL_CONFIG_NAME );
        this.loadConfigFile( ConfigManager.AREAS_CONFIG_NAME );
        this.loadConfigFile( ConfigManager.CARDS_CONFIG_NAME );
        
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
    public Properties getConfig( String name ) throws IOException{
        if( ! this.configs.containsKey( name )){
            this.loadConfigFile( name );
        }
        return this.configs.get( name );
    }
    
    /**
     * @deprecated 
     * @param configName
     * @param beginning
     * @return 
     */
    public ArrayList<String> getConfigKeysBeginningBy( String configName, String beginning ){
//        LOGGER.debug("getConfigKeysBeginningBy: Load all keys beginning by " + beginning);
        ArrayList<String> keys = new ArrayList<String>();
        Enumeration<Object> result = this.configs.get(configName).keys();
        while(result.hasMoreElements()){
            String param = (String) result.nextElement();
            if( param.startsWith( beginning )){
                keys.add(param);
            }
        }
        return keys;
    }
    
    /**
     * Return an hashmap of keys -> value but only those (keys) which are beginning by the given string
     * @param configName
     * @param beginning 
     */
    public HashMap<String, String> getConfigEntriesWithKeysBeginningBy( String configName, String beginning ){
        HashMap<String, String> entries = new HashMap<String, String>();
        Enumeration<Object> result = this.configs.get(configName).keys();
        while(result.hasMoreElements()){
            String param = (String) result.nextElement();
            if( param.startsWith( beginning )){
                entries.put( param, this.configs.get(configName).getProperty(param));
            }
        }
        return entries;
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
    
    
    /***********************************************************************************************
     *
     *                                  Getter & Setter
     * 
     ***********************************************************************************************/
    
    /**
     * Return options.ini
     * 
     * @return 
     * @throws java.lang.Exception 
     */
    public Ini getOptions(){
        return this.options;
    }
    
    
    
}
