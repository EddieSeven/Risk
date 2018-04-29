package edu.T10.Model.Deck;

import static org.junit.Assert.assertEquals;

import edu.T10.Model.Deck.Card;

import org.junit.Before;
import org.junit.Test;

public class TestCard {
	Card c;
	
	@Before
	public void setUp() {
		c = new Card();
	}
	
	@Test
	public void testType() {
		assertEquals(c.getType(), Card.CardType.DEFAULT);
		c = new Card(Card.CardType.ARTILLERY);
		assertEquals(c.getType(), Card.CardType.ARTILLERY);
	}
}
