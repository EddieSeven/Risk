package edu.T10.Controller;

import static org.junit.Assert.assertTrue;

import edu.T10.Controller.Game;
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

	@Test
	public void testInvasion(){
		Player p1 = new Player("bob", 0);
		Player p2 = new Player("john", 1);
		Player p3 = new Player("tony", 2);
		Player players[] = new Player[3];
		players[0] = p1;
		players[1] = p2;
		players[2] = p3;


		Game game = new Game(players);
		int a = 4;


	}
}
