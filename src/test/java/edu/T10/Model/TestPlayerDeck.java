package edu.T10.Model;

import static org.junit.Assert.assertEquals;

import java.util.Vector;

import edu.T10.Model.Deck.Card;
import edu.T10.Model.Deck.Deck;
import edu.T10.Model.Deck.PlayerDeck;
import org.junit.Before;
import org.junit.Test;

public class TestPlayerDeck {
	Deck d;
	Vector<Card> cards;
	
	@Before
	public void setUp() {
		d = new PlayerDeck();
	}
	
	@Test
	public void testDrawCardsNoCard() {
		//assertEquals(d.drawCards(1).size(), 0);
	}
//
//	@Test
//	public void testDrawCards() {
//		cards = new Vector<Card>();
//		cards.add(new Card());
//		d.addCards(cards);
//		assertEquals(d.drawCards(1).get(0), cards.get(0));
//	}
}
