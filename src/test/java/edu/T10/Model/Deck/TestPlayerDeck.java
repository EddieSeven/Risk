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
	
	@Test
	public void testAddCardFULL() {
		playerDeck.addCard(new Card(CardType.DEFAULT));
		assertTrue(!playerDeck.canCollectCards());
		playerDeck.addCard(new Card(CardType.ARTILLERY));
		playerDeck.addCard(new Card(CardType.CAVALRY));
		playerDeck.addCard(new Card(CardType.INFANTRY));
		assertTrue(playerDeck.canCollectCards());
		assertEquals(playerDeck.collectCardReward(), 4);
	}
	
	@Test
	public void testTire() {
		playerDeck.addCard(new Card(CardType.ARTILLERY));
		playerDeck.addCard(new Card(CardType.ARTILLERY));
		playerDeck.addCard(new Card(CardType.ARTILLERY));
		assertEquals(playerDeck.collectCardReward(), 4);
		playerDeck.addCard(new Card(CardType.INFANTRY));
		playerDeck.addCard(new Card(CardType.INFANTRY));
		playerDeck.addCard(new Card(CardType.INFANTRY));
		assertTrue(playerDeck.canCollectCards());
		assertEquals(playerDeck.collectCardReward(), 6);
		playerDeck.addCard(new Card(CardType.CAVALRY));
		playerDeck.addCard(new Card(CardType.CAVALRY));
		playerDeck.addCard(new Card(CardType.CAVALRY));
		assertTrue(playerDeck.canCollectCards());
		assertEquals(playerDeck.collectCardReward(), 8);
		playerDeck.addCard(new Card(CardType.CAVALRY));
		playerDeck.addCard(new Card(CardType.WILDCARD));
		playerDeck.addCard(new Card(CardType.ARTILLERY));
		assertTrue(playerDeck.canCollectCards());
		assertEquals(playerDeck.collectCardReward(), 10);
		playerDeck.addCard(new Card(CardType.INFANTRY));
		playerDeck.addCard(new Card(CardType.WILDCARD));
		playerDeck.addCard(new Card(CardType.ARTILLERY));
		assertTrue(playerDeck.canCollectCards());
		assertEquals(playerDeck.collectCardReward(), 12);
		playerDeck.addCard(new Card(CardType.CAVALRY));
		playerDeck.addCard(new Card(CardType.WILDCARD));
		playerDeck.addCard(new Card(CardType.INFANTRY));
		assertTrue(playerDeck.canCollectCards());
		assertEquals(playerDeck.collectCardReward(), 15);
	}
	
}
