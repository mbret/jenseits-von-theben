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
public class TestAssistantCard {
    
    public TestAssistantCard() {
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
     * Test of getKnowLedgePointsWhenCombinated method, of class AssistantCard.
     */
    @Test
    public void testGetKnowLedgePointsWhenCombinated() {
    	
    	// expected 5 pts knowledges, with 7 assistants
        assertEquals(5, AssistantCard.getKnowLedgePointsWhenCombinated(7));
        
     // expected 4 pts knowledges, with 6 assistants
        assertEquals(4, AssistantCard.getKnowLedgePointsWhenCombinated(6));
        
     // expected 3 pts knowledges, with 5 assistants
        assertEquals(3, AssistantCard.getKnowLedgePointsWhenCombinated(5));
        
     // expected 2 pts knowledges, with 4 assistants
        assertEquals(2, AssistantCard.getKnowLedgePointsWhenCombinated(4));
        
     // expected 2 pts knowledges, with 3 assistants
        assertEquals(2, AssistantCard.getKnowLedgePointsWhenCombinated(3));
        
     // expected 1 pts knowledges, with 2 assistants
        assertEquals(1, AssistantCard.getKnowLedgePointsWhenCombinated(2));
        
     // expected 1 pts knowledges, with 1 assistants
        assertEquals(1, AssistantCard.getKnowLedgePointsWhenCombinated(1));
        
     // expected 0 pts knowledges, with 0 assistants
        assertEquals(0, AssistantCard.getKnowLedgePointsWhenCombinated(0));
        
        
        
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
}
