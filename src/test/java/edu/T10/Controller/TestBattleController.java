package edu.T10.Controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Vector;

import edu.T10.Controller.*;
import edu.T10.Model.InvasionResult;
import edu.T10.Model.Board.*;
import edu.T10.Model.Exceptions.MoveException;
import edu.T10.Model.Exceptions.NumberOfDiceException;
import edu.T10.Model.Exceptions.NumberOfUnitsException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestBattleController {

	private BattleController controller;
	private Territory t0;
	private Territory t1;
	
	@Mock
	private Board board;
	
	@Mock
	private Game game;
	
	@Mock
	private Territory territory;

	@Before
	public void setUp() {	
		// int owner, String name, int armyStrength, int id, int continentID, int adj
		t0 = new Territory(0, "Attacker", 6, 0, 0, 1);
		t1 = new Territory(1, "Defender", 1, 1, 1, 0);
		when(board.getArmyStrength(1)).thenReturn(1);
		when(board.getTerritory(0)).thenReturn(t0);
		when(game.getTerritory(0)).thenReturn(t0);
		when(game.getTerritory(1)).thenReturn(t1);
		when(game.getBoard()).thenReturn(board);
		controller = new BattleController(game);
	}

	@Test
	public void testConductInvasion() {
		InvasionResult realResult;
		try {
			// int fromTerritoryID, int toTerritoryID, int attackerUnits, int attackerDice, int defenderDice
			realResult = controller.conductInvasion(0, 1, 5, 3, 1);
			assertTrue(realResult.toString().contains("{\"action\":\"result\",\"result\":"));
		} catch (MoveException | NumberOfUnitsException | NumberOfDiceException e) {
			e.printStackTrace();
		}
	}
}