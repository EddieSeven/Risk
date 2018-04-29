package edu.T10.Model.Board;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import edu.T10.Model.Board.Board;

@RunWith(MockitoJUnitRunner.class)
public class TestBoard {
	
	private Board b;
	private Territory t;
	
	@Mock
	Continent c;
	
	@Before
	public void setUp() {
		// int owner, String name, int armyStrength, int id, int continentID, int adj
		t = new Territory(0, "Test Territory", 1, 0, 0, 2);
		b = new Board(c, t);
	}
	
	@Test
	public void testSetTerritoryStrength() {
		b.setTerritoryStrength(0, 2);
		assertEquals(b.getArmyStrength(0), 2);
	}
	
	@Test
	public void testUpdateTerritoryStrength() {
		b.updateTerritoryStrength(0, 2);
		assertEquals(b.getArmyStrength(0), 3);
	}
}
