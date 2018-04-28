package edu.T10.Model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

public class TestPlayer {
	Player p;
	
	@Before
	public void setUp() {
		p = new Player("Test", 0);
	}
	
//	@Test
//	public void testNoCardUseCard() {
//		assertTrue(!p.useCards(1));
//	}
//
	@Test
	public void testUseCard() {
//		Vector<Card> v = new Vector<Card>();
//		v.add(new Card(CardType.ARTILLERY));
//		p.addCard2Deck(v);
//		assertTrue(p.useCards(1));
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
		p.assignColor(2);
		assertEquals(p.toString(), "Test RED 0");
	}
}
