package edu.T10.Model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import edu.T10.Model.Deck.*;

public class TestPlayer {
	Player p;
	
	@Before
	public void setUp() {
		p = new Player("Test", 0);
	}

	@Test
	public void testCard() {
		p.addCard(new Card());
		assertTrue(!p.canCollect());
		assertEquals(p.collectCardReward(), 4);
	}
	
	@Test
	public void testFreeArmies() {
		assertEquals(p.getFreeArmies(), 0);
		p.addNewArmies(5);
		assertEquals(p.getFreeArmies(), 5);
	}

	@Test
	public void testAssignColor() {
		assertEquals(p.toString(), "Test GREEN 0");
		p.assignColor(1);
		assertEquals(p.toString(), "Test YELLOW 0");
		p.assignColor(Color.RED);
		assertEquals(p.toString(), "Test RED 0");
	}
}
