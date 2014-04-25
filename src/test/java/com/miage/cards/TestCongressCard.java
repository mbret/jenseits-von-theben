/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.miage.cards;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Maxime
 */
public class TestCongressCard {
    
    public TestCongressCard() {
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

    /**
     * Test of getPoints method, of class CongressCard.
     */
    @Test
    public void GetPoints() {
        assertEquals(1, CongressCard.getPoints( 1 ));
        assertEquals(6, CongressCard.getPoints( 3 ));
        assertEquals(28, CongressCard.getPoints( 7 ));
    }
    
    @Test (expected = IndexOutOfBoundsException.class)
    public void GetPointsWithException() {
        try{
            CongressCard.getPoints( 8 ); // should be catch
        }
        catch( IndexOutOfBoundsException ex ){
            CongressCard.getPoints( -1 ); // should be catch
        }
    }

    /**
     * Test of isDiscardable method, of class CongressCard.
     */
    @Test
    public void testIsDiscardable() {

    }
    
}
