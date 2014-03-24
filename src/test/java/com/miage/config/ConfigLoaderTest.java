
package com.miage.config;

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
        ConfigManager.getInstance().init();
    }
    
    /**
     * Test of getProperties method, of class ConfigLoader.
     */
    @Test
    public void testGetConfig() throws Exception{
        System.out.println("getProperties");
        String expResult = "16";
        Properties result = ConfigManager.getInstance().getConfig();
        assertEquals(expResult, result.get("nbEmptyTokens"));
    }

    
    @Test
    public void testGetOption() throws Exception{
        System.out.println("getOption");
        String expResult = "tata";
        Ini options = ConfigManager.getInstance().getOptions();
        assertEquals(expResult, options.get("test", "testGet") );
    }
    
    @Test
    public void testSetOption() throws Exception{
        System.out.println("setOption");
        Double expResult = (Math.random() * ( 5000 - 1 ));
        ConfigManager.getInstance().setOption("test", "testSet", expResult.toString());
        assertEquals(expResult,  ConfigManager.getInstance().getOptions().get( "test", "testSet", double.class));
    }
    
    public void testInitArrea() throws Exception{
        System.out.println("initArea");
        
    }
}
