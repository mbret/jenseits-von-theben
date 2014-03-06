package gameTest;
import static org.junit.Assert.*;
import game.Player;
import game.PlayerKnowledges;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class TestPlayer {
	
	private Player player;
	PlayerKnowledges playerK;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		playerK = new PlayerKnowledges();
		playerK.setSpecificKnowledge(3);
		playerK.setEthnologicalKnowledge(2);
		
		player = new Player("Test");
		player.getSpecialKnowledges().put("red", playerK);
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetSpecificKnowledge() {
		
		assertEquals(3, player.getSpecificKnowledge("red"));
		assertEquals(0, player.getSpecificKnowledge("blue"));
		
	}
	
	@Test
	public void testGetEthnologicalKnowledge() {
		
		assertEquals(2, player.getEthnologicalKnowledge("red"));
		assertEquals(0, player.getEthnologicalKnowledge("blue"));
		
	}
	
	

}
