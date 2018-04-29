package edu.T10.Model.Deck;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestGameDeck {

	private GameDeck gameDeck;
	
	@Before
	public void setUp() {
		gameDeck = new GameDeck();
	}
	
	@Test
	public void testAddDeck() {
		assertEquals(gameDeck.getDeckSize(), 44);
		gameDeck.addCard(new Card(Card.CardType.INFANTRY));
		assertEquals(gameDeck.getDeckSize(), 45);
	}
	
	@Test
	public void testDraw() {
		Card lastCard = new Card(Card.CardType.INFANTRY);
		gameDeck.addCard(lastCard);
		assertEquals(gameDeck.draw(), lastCard);
	}
	
}
