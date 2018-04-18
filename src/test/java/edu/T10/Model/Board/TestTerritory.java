package edu.T10.Model.Board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestTerritory {
	private Territory t;
	
	@Before
	public void setUp() {
		t = new Territory();
	}
	
	@Test
	public void testAssignOwner() {
		t.assignOwner(2);
		assertEquals(t.getOwner(), 2);
	}
	
	@Test
	public void testGetId() {
		t.setId(1);
		assertEquals(t.getId(), 1);
	}
	
	@Test
	public void testGetName() {
		t.setName("TestName");
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
	public void testToString() {
		t.setId(1);
		t.setArmyStrength(0);
		assertEquals(t.toString(), "1 0");
	}
	
}
