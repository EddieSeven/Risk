package edu.T10.Model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestDeck {
	Deck d;
	Card[] cards;
	
	@Before
	public void setUp() {
		d = new Deck();
	}
	
	@Test
	public void testDrawCardsNoCard() {
		assertEquals(d.drawCards(1).length, 0);
	}
	
	@Test
	public void testDrawCards() {
		cards = new Card[] {new Card()};
		d.addCards(cards);
		assertEquals(d.drawCards(1)[0], cards[0]);
	}
}
