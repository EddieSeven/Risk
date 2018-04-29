package edu.T10.Model.Deck;

import static org.junit.Assert.assertEquals;

import edu.T10.Model.Deck.Card;
import edu.T10.Model.Deck.Deck.CardType;

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
		assertEquals(c.getType(), CardType.DEFAULT);
		c = new Card(CardType.ARTILLERY);
		assertEquals(c.getType(), CardType.ARTILLERY);
	}
}
