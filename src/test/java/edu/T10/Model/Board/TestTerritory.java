package edu.T10.Model.Board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestTerritory {
	Territory t;
	
	@Before
	public void setUp() {
		t = new Territory();
		t.readTerritoryFromLine("1 TestName 0 11 12 ;");
		t.readAdjacentsFromLine("1 1 2 3 ;");
	}
	
	@Test
	public void testAssignGetOwner() {
		t.assignOwner(2);
		assertEquals(t.getOwner(), 2);
	}
	
	@Test
	public void testGetId() {
		assertEquals(t.getId(), 1);
	}
	
	@Test
	public void testGetName() {
		assertEquals(t.getName(), "TestName");
	}
	
	@Test
	public void testGetAssignStrength() {
		assertEquals(t.getStrength(), 0);
		t.updateArmyStrength(5);
		assertEquals(t.getStrength(), 5);
	}

	@Test
	public void testSetArmyStrength() {
		t.setArmyStrength(10);
		assertEquals(t.getStrength(), 10);
	}
	
	@Test
	public void testReadTerritoryError() {
		assertTrue(!t.readTerritoryFromLine(""));
	}

	@Test
	public void testReadAdjacentsError() {
		assertTrue(!t.readAdjacentsFromLine(""));
	}
	
	@Test
	public void testToString() {
		assertEquals(t.toString(), "1 TestName 0 0 [1, 2, 3]");
	}
	
}
