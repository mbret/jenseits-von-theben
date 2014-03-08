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
	PlayerKnowledges playerKnowledges;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		playerKnowledges = new PlayerKnowledges();
		playerKnowledges.setSpecificKnowledge(3);
		playerKnowledges.setEthnologicalKnowledge(2);
		
		player = new Player("Test");
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	
	
	
	

}
