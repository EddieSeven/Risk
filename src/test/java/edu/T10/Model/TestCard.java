package edu.T10.Model;

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
	public void testGetType() {
		// assertEquals(c.getType(), 3);
	}
//
//	@Test
//	public void testGetTypeNotDefault() {
//		c = new Card(CardType.ARTILLERY);
//		assertEquals(c.getType(), CardType.ARTILLERY);
//	}
}
