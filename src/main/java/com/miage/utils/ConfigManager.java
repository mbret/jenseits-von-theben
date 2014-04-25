package com.miage.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.ini4j.Ini;

/**
 * Class ConfigLoader allow program to load properties from .properties files.
 * <br/>This class is a Singleton, please use getInstance()
 * <br/><b>To catch the first time all probably exceptions the laoding might throw use loadAll(). 
 * This method will load all needed files for the program</b>
 * 
 * 
 * @author Maxime
 */
public class ConfigManager {
 
    private final static Logger LOGGER = LogManager.getLogger(ConfigManager.class.getName());

    // Instance
    private static ConfigManager instance;
    
    private final String optionsFilename = "src/options.ini";
    private Ini options;
    
    // Contain all loaded config files, they are added dynamically when asked
    private HashMap<String, Properties> configs;

    public final static String GENERAL_CONFIG_NAME = "config.properties";
    public final static String AREAS_CONFIG_NAME = "areas.properties";
    public final static String CARDS_CONFIG_NAME = "cards.properties";
    
    
    
    private ConfigManager() {
        this.configs = new HashMap();
    }
    
    
    /***********************************************************************************************
     *
     *                                  Methods
     * 
     ***********************************************************************************************/
    
    
    
    /**
     * Load and store locally all needed files.
     * <br/>Useful to try/catch just one time this the entire loading
     * @throws java.io.IOException
     */
    public void loadAll() throws IOException{
        LOGGER.debug("loadAll: Load all configs and options files");
        this._loadConfigFile( ConfigManager.GENERAL_CONFIG_NAME );
        this._loadConfigFile( ConfigManager.AREAS_CONFIG_NAME );
        this._loadConfigFile( ConfigManager.CARDS_CONFIG_NAME );
        this._loadOptionsFile( this.optionsFilename );
    }
    
    /**
     * Return the unique instance of ConfigManager
     * @return ConfigManager
     */
    public static ConfigManager getInstance(){
        return ConfigManager.instance == null ? new ConfigManager() : ConfigManager.instance;
    }
    
    /**
     * Retourne the config properties file
     * 
     * @param configName Use ConfigManager static KEY (exemple ConfigManager.CARDS_CONFIG_NAME)
     * @return Properties file
     * @throws java.io.IOException
     */
    public Properties getConfig( String configName ){
        if( ! this.configs.containsKey( configName )){
            try {
                this._loadConfigFile( configName );
            } catch (IOException ex) {
                LOGGER.fatal( ex );
                System.exit( 0 );
            }
        }
        return this.configs.get( configName );
    }
    
    /**
     * Return an hashmap of keys -> value but only those (keys) which are beginning by the given string
     * @param configName
     * @param beginning 
     * @return  HashMap<String, String>
     */
    public HashMap<String, String> getConfigEntriesWithKeysBeginningBy( String configName, String beginning ) throws IOException{
        HashMap<String, String> entries = new HashMap();
        Enumeration<Object> result = this.getConfig(configName).keys();
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
     * @throws java.io.IOException 
     */
    public <T extends Object> T getOptionValue( String section, String key, Class<T> desiredClass) throws IOException{
        LOGGER.debug("getOptionValue: ");
        if( this.options == null ){
            this._loadOptionsFile( this.optionsFilename );
        }
        return this.options.get(section, key, desiredClass);
    }
    
    /**
     * Return options.ini
     * 
     * @return 
     * @throws java.lang.Exception 
     */
    public Ini getOptions() throws IOException{
        if( this.options == null ){
            this._loadOptionsFile( this.optionsFilename );
        }
        return this.options;
    }
    
    /**
     * Add or replace the option inside options.ini
     * 
     * @param section
     * @param key
     * @param value 
     * @throws java.io.IOException 
     */
    public void setOption(String section, String key, String value) throws IOException{
        LOGGER.debug("setOption: ");
        if( this.options == null ){
            this._loadOptionsFile( this.optionsFilename );
        }
        this.options.put(section, key, value); // add or replace
        this.options.store(); // store new data inside file
    }
    
    
    
    /***********************************************************************************************
     *
     *                                  Private Methods
     * 
     ***********************************************************************************************/
    
    
    /**
     * Load the specified config file. Use static Board name as parameter (Board.NAME_OF_CONFIG)
     * @param name
     * @throws IOException 
     */
    private void _loadConfigFile( String name ) throws IOException{
//        LOGGER.debug("loadConfigFile: load config file " + name+" from file storage");
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
    
    private void _loadOptionsFile( String optionsFilename ) throws IOException{
        LOGGER.debug("loadOptionsFile: Load options file "+optionsFilename+" from file storage");
        // Load ini file
        if( this.options == null ){
            
            this.options = new Ini( new File( optionsFilename ) );
        }
    }
    
    
    
    
    
    
    
    
    
    
    /**
     * @deprecated 
     * @param configName
     * @param beginning
     * @return 
     */
    public ArrayList<String> getConfigKeysBeginningBy( String configName, String beginning ){
//        LOGGER.debug("getConfigKeysBeginningBy: Load all keys beginning by " + beginning);
        ArrayList<String> keys = new ArrayList();
        Enumeration<Object> result = this.configs.get(configName).keys();
        while(result.hasMoreElements()){
            String param = (String) result.nextElement();
            if( param.startsWith( beginning )){
                keys.add(param);
            }
        }
        return keys;
    }
    
}
