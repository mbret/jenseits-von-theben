package gameTest;

import static org.junit.Assert.*;
import game.Piece;
import game.PiecesStack;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestPieceStack {
	
	private PiecesStack pieceStack;
	private Piece piece1, piece2, piece3, piece4;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		pieceStack = new PiecesStack();
		
		Calendar c1 = new GregorianCalendar(1900, 11, 31);
		Calendar c2 = new GregorianCalendar(1901, 3, 20);
		Calendar c3 = new GregorianCalendar(1901, 2, 27);
		Calendar c4 = new GregorianCalendar(1900, 11, 31);
		
		piece1 = new Piece("red");
		piece2 = new Piece("blue");
		piece3 = new Piece("green");
		piece4 = new Piece("yellow");
		
		piece1.setTimeState(c1);
		piece2.setTimeState(c2);
		piece3.setTimeState(c3);
		piece4.setTimeState(c4);
		
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	/**
	 * Test of the method addPiece()
	 */
	public void testAddPiece() {
		
		pieceStack.addPiece(piece1);
		assertEquals("red ", pieceStack.toString());
		pieceStack.addPiece(piece2);
		assertEquals("red blue ", pieceStack.toString());
		pieceStack.addPiece(piece3);
		assertEquals("red green blue ", pieceStack.toString());
		pieceStack.addPiece(piece4);
		assertEquals("yellow red green blue ", pieceStack.toString());
		
	}
	
	@Test
	/**
	 * Test of the method getFirst()
	 */
	public void testGetFirst(){
		
		pieceStack.addPiece(piece1);
		pieceStack.addPiece(piece2);
		pieceStack.addPiece(piece3);
		pieceStack.addPiece(piece4);
		
		assertEquals("yellow", pieceStack.getFirstPiece().getColor());
		assertEquals("red", pieceStack.getFirstPiece().getColor());
		assertEquals("green", pieceStack.getFirstPiece().getColor());
		assertEquals("blue", pieceStack.getFirstPiece().getColor());
		
	}

}
