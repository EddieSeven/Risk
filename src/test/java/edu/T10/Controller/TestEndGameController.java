package edu.T10.Controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.T10.Controller.*;
import edu.T10.Model.Board.Territory;

import edu.T10.Model.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestEndGameController {

	EndGameController egController;

	@Mock
	private Game game;

	@Mock
	private Territory territory;

	@Before
	public void setUp() {
		when(game.getPlayerTerritories(0)).thenReturn(new Territory[] {territory});
		when(game.getPlayerTerritories(1)).thenReturn(new Territory[] {territory});
		egController = new EndGameController(game, new Player[] {new Player("player1"), new Player("player2")});
	}

	@Test
	public void testCheckGame() {
		assertTrue(!egController.checkGame());
	}

	@Test
	public void testSwapPlayer() {
		assertEquals(egController.swapPlayer(1), 0);
	}

}
