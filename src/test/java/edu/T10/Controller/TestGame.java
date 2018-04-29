package edu.T10.Controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import edu.T10.Controller.Game;
import edu.T10.Model.Deck.Card;
import edu.T10.Model.Deck.Deck.CardType;
import edu.T10.Model.Exceptions.DeckCompositionException;
import edu.T10.Model.Exceptions.NumberOfUnitsException;
import edu.T10.Model.Exceptions.PlayerException;
import edu.T10.Model.Player;
import edu.T10.Model.Board.Territory;

import org.junit.Before;
import org.junit.Test;

public class TestGame {

	private Game game;

	@Before
	public void setUp() {
		game = new Game(new Player[] {new Player("Player1", 0), 
				new Player("Player2", 1), new Player("Player3", 2), new Player("Player4", 3)});
	}

	@Test
	public void testPlayCard() throws DeckCompositionException {
		game.setCurrentPlayerGetsCard();
		game.getCurrentPlayer().addCard(new Card(CardType.ARTILLERY));
		game.getCurrentPlayer().addCard(new Card(CardType.ARTILLERY));
		game.getCurrentPlayer().addCard(new Card(CardType.ARTILLERY));
		game.playCards();
		assertEquals(game.getCurrentPlayer().getFreeArmies(), 4);
	}
	
	@Test
	public void testGetTerritory() {
		assertEquals(game.getTerritory(0), game.getBoard().getTerritory(0));
	}

}
