package edu.T10.Controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.T10.Controller.EndGameController;
import edu.T10.Model.Board.*;

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
	private Board board;

	@Mock
	private Territory territory;

	@Before
	public void setUp() {
		when(board.getTerritories(0)).thenReturn(new Territory[] {territory});
		when(board.getTerritories(1)).thenReturn(new Territory[] {territory});
		egController = new EndGameController(board, new Player[] {new Player("player1"), new Player("player2")});
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
