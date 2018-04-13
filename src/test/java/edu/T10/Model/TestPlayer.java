package edu.T10.Model;

import static org.junit.Assert.assertTrue;

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
	
	// TODO: Army test needed, but since I don't see anywhere modifies players' free armies, will do it later 
	
}
