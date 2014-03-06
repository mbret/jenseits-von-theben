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
	
	private PiecesStack ps;
	private Piece p1, p2, p3, p4;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		ps = new PiecesStack();
		
		Calendar c1 = new GregorianCalendar(1900, 11, 31);
		Calendar c2 = new GregorianCalendar(1901, 3, 20);
		Calendar c3 = new GregorianCalendar(1901, 2, 27);
		Calendar c4 = new GregorianCalendar(1900, 11, 31);
		
		p1 = new Piece("red");
		p2 = new Piece("blue");
		p3 = new Piece("green");
		p4 = new Piece("yellow");
		
		p1.setTimeState(c1);
		p2.setTimeState(c2);
		p3.setTimeState(c3);
		p4.setTimeState(c4);
		
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddPiece() {
		
		ps.addPiece(p1);
		assertEquals("red ", ps.toString());
		ps.addPiece(p2);
		assertEquals("red blue ", ps.toString());
		ps.addPiece(p3);
		assertEquals("red green blue ", ps.toString());
		ps.addPiece(p4);
		assertEquals("yellow red green blue ", ps.toString());
		
	}
	
	@Test
	public void testGetFirst(){
		
		ps.addPiece(p1);
		ps.addPiece(p2);
		ps.addPiece(p3);
		ps.addPiece(p4);
		
		assertEquals("yellow", ps.getFirstPiece().getColor());
		assertEquals("red", ps.getFirstPiece().getColor());
		assertEquals("green", ps.getFirstPiece().getColor());
		assertEquals("blue", ps.getFirstPiece().getColor());
		
	}

}
