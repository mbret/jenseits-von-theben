package com.miage.game;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



public class TestPlayerTokenStack {
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {
	}

        /**
         * We test the correct order update of the stack
         */
        @Test
        public void testOrder(){
            LinkedList<PlayerToken> playerTokenStack = new LinkedList();
		
            PlayerToken red = new PlayerToken("red", null, LocalDate.now(), null);
            PlayerToken yellow = new PlayerToken("yellow", null, LocalDate.now(), null);
            PlayerToken blue = new PlayerToken("blue", null, LocalDate.now(),null);
            PlayerToken purple = new PlayerToken("purple", null, LocalDate.now(), null);
            
            // All player same position
            playerTokenStack.add( red );
            playerTokenStack.add( blue );
            playerTokenStack.add( yellow );
            playerTokenStack.add( purple );
            
            // test first who must play, red must play
            assertTrue( playerTokenStack.getFirst().getColor().equals("red") );
            
            // red is moving to 1 week, so blue must play now and red is the last
            
            red.getTimeState().plusWeeks(1);
            Collections.sort( playerTokenStack );
            assertTrue( playerTokenStack.getFirst().getColor().equals("blue") );
            assertTrue( playerTokenStack.getLast().getColor().equals("red") );
            
            // yellow move to 5 weeks (yellow must be the last)
            yellow.getTimeState().plusWeeks( 5 );
            Collections.sort( playerTokenStack );
            assertTrue( playerTokenStack.getLast().getColor().equals("yellow") );
            
            // now blue join yellow 
            blue.getTimeState().plusWeeks( 5 );
            Collections.sort( playerTokenStack );
            // then red join yellow
            red.getTimeState().plusWeeks( 4 );
            Collections.sort( playerTokenStack );
            // then purple join yellow
            purple.getTimeState().plusWeeks( 5 );
            Collections.sort( playerTokenStack );
            
            // Here purple is the last one to have join yellow so it must be the first to play
            assertTrue( playerTokenStack.getFirst().getColor().equals("purple") );
            // red who was the second last to join yellow must be the second to play
            assertTrue( playerTokenStack.get( 1 ).getColor().equals("red") );
            // then blue and then yellow
            assertTrue( playerTokenStack.get( 2 ).getColor().equals("blue") );
            assertTrue( playerTokenStack.getLast().getColor().equals("yellow") );
            
        }
	
	

}
