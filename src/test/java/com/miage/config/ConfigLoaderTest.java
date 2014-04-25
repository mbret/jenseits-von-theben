
package com.miage.config;

import com.miage.utils.ConfigManager;
import java.io.IOException;
import java.util.Properties;
import org.ini4j.Ini;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Maxime
 */
public class ConfigLoaderTest {
    
    public ConfigLoaderTest(){
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testInit() throws IOException{
        ConfigManager.getInstance().loadAll();
    }
    
    /**
     * Test of getProperties method, of class ConfigLoader.
     */
//    @Test
//    public void testGetConfig() throws Exception{
//        System.out.println("getProperties");
//        String expResult = "16";
//        assertEquals(expResult, ConfigManager.getInstance().getConfig( ConfigManager.GENERAL_CONFIG_NAME ).getProperty("nbEmptyTokenPoint"));
//    }

    
    
    @Test
    public void testSetOption() throws Exception{
        String oldValue = ConfigManager.getInstance().getOptionValue( "test" , "testSet", String.class); // we keep old value to dont have any conflict with git
        Double expResult = (Math.random() * ( 5000 - 1 ));
        ConfigManager.getInstance().setOption("test", "testSet", expResult.toString());
        assertEquals(expResult,  ConfigManager.getInstance().getOptions().get( "test", "testSet", double.class));
        ConfigManager.getInstance().setOption("test", "testSet", oldValue); // we put old value (no conflict with futur git merge)
    }
    
    @Test
    public void testGetOption() throws Exception{
        Ini options = ConfigManager.getInstance().getOptions();
        assertTrue( Boolean.valueOf( options.get("general", "audio") ) instanceof Boolean );
    }
    
    public void testInitArrea() throws Exception{
        
    }
}
