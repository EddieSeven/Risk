package edu.T10.Model.Deck;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestPlayerDeck {

	private PlayerDeck playerDeck;
	
	@Before
	public void setUp() {
		playerDeck = new PlayerDeck();
	}
	
	@Test
	public void testCanCollectCards() {
		assertTrue(!playerDeck.canCollectCards());
	}
	
}
