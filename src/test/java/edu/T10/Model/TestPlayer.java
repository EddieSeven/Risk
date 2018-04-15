package edu.T10.Model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestPlayer {
	Player p;
	
	@Before
	public void setUp() {
		p = new Player("Test");
	}
	
	@Test
	public void testNoCardUseCard() {
		assertTrue(!p.useCards(1));
	}
	
	@Test
	public void testUseCard() {
		p.addCard2Deck(new Card[] {new Card(CardType.ARTILLERY)});
		assertTrue(p.useCards(1));
	}
	
	@Test
	public void testFreeArmies() {
		assertEquals(p.getFreeArmies(), 0);
		p.addNewArmies(5);
		assertEquals(p.getFreeArmies(), 5);
	}

	@Test
	public void testAssignColor() {
		assertEquals(p.toString(), "Test RED 0");
		p.assignColor(Color.BLACK);
		assertEquals(p.toString(), "Test BLACK 0");
		p.assignColor(1);
		assertEquals(p.toString(), "Test YELLOW 0");
	}
}
