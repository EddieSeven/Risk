package edu.T10.Controller;

import static org.junit.Assert.assertTrue;

import edu.T10.Controller.Game;
import edu.T10.Model.Board.Territory;
import edu.T10.Model.Exceptions.MoveException;
import edu.T10.Model.Exceptions.NumberOfDiceException;
import edu.T10.Model.Exceptions.NumberOfUnitsException;
import edu.T10.Model.InvasionResult;
import edu.T10.Model.Player;
import org.junit.Before;
import org.junit.Test;

import org.junit.Rule;
import org.junit.contrib.java.lang.system.SystemOutRule;

public class TestGame {

	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

	@Before
	public void setUp() {	}

	@Test
	public void testGame() {
		// As private variables are not accessable outside
		// Will use the tests that are in main to test Game for now
	}

	// @Test todo delete at a later date, useful for now
	public void testConductInvasion() {
		Player p1 = new Player("bob", 0);
		Player p2 = new Player("john", 1);
		Player players[] = new Player[2];
		players[0] = p1;
		players[1] = p2;

		Game game = new Game(players);
		Territory invadingTerritory = game.getTerritory(3);
		Territory invadedTerritory = game.getTerritory(2);
		InvasionResult invasionResult = new InvasionResult();

		invadingTerritory.setArmyStrength(7);
		invadedTerritory.setArmyStrength(5);

		try {

			for (int i = 0; i < 10000; i++){
				invasionResult = game.conductInvasion(3, 2, 6, 3, 2);
				invadingTerritory.setArmyStrength(7);
				invadedTerritory.setArmyStrength(5);


			}



		} catch (MoveException | NumberOfDiceException | NumberOfUnitsException e) {
			e.printStackTrace();
		}


	}

}
