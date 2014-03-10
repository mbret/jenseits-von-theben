package gameTest;

import static org.junit.Assert.*;
import game.Board;
import game.Piece;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBoard {
	
	Board board;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		board = new Board(3);
		board.initAreas();
		board.initDistances();
		board.initializationDecks();
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	/**
	 * Test of the method distance()
	 */
	public void testDistance() {
		assertEquals(3, board.distance(board.getAreas()[0], board.getAreas()[7]));
		assertEquals(4, board.distance(board.getAreas()[0], board.getAreas()[9]));
		assertEquals(0, board.distance(board.getAreas()[2], board.getAreas()[2]));
		assertEquals(3, board.distance(board.getAreas()[6], board.getAreas()[11]));
		
	}
	
	@Test
	/**
	 * Test of the method printDeck()
	 */
	public void testPrintDeck(){
		System.out.println(board.printDeck(board.getDeck()));
	}

}
