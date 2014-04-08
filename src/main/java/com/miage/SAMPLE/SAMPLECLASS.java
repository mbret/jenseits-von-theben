

package com.miage.SAMPLE;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * You should write here short description of the class
 * 
 * tou should write here long description of the class
 * 
 * @author yourName
 * @version yourVersion of the class
 * // @deprecated used only if you want keep this class but you do not recommend its use
 */
public class SAMPLECLASS {

    /**
     * Logger, used to log useful informations
     */
    private final static Logger LOGGER = LogManager.getLogger(SAMPLECLASS.class.getName());
    
    /**
     * Description of attribute1
     */
    private Object attribute1;
    
    /**
     * Description of attribute 2
     */
    private Object attribute2;

    /**
     * Eventual description of constructor actions
     * @param attribute1
     * @param attribute2 
     */
    public SAMPLECLASS(Object attribute1, Object attribute2) {
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
    }
    
    

    /***********************************************************************************************
     *
     *                                  Public Methods
     * 
     ***********************************************************************************************/
    
    /**
     * Description of this method
     */
    public void method1(){
        
    }
    
    /**
     * Method which use some log inside
     */
    public void methodWithSomeLogInside(){
        LOGGER.debug("Message used for debug (sout)");
        LOGGER.info("Message used for information");
        LOGGER.warn("Something went wrong but the application can continue running");
        LOGGER.fatal("Fatal error, the application stopped or crashed");
    }
    
    
    /**
     * description of method2
     * @param a
     * @param b
     * @return a string value
     */
    public String method2( String a, String b ){
            return "";
    }
    
    
    /***********************************************************************************************
     *
     *                                  Private Methods
     * 
     ***********************************************************************************************/
    
    private void _doSmthg(){
        
    }
    
    
    /***********************************************************************************************
     *
     *                                  Getter & Setter
     * 
     ***********************************************************************************************/
    
    public Object getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(Object attribute1) {
        this.attribute1 = attribute1;
    }

    public Object getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(Object attribute2) {
        this.attribute2 = attribute2;
    }
    
    
    
}
