package edu.T10.Model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestInvasionResult {
	
	InvasionResult result;
	
	@Before
	public void setUp() {
		result = new InvasionResult();
	}
	
	@Test
	public void testGetDefaultVictor() {
		assertEquals(result.getVictor(), Victor.DEFAULT);
	}
	
	@Test
	public void testGetVictor() {
		result.setVictor(Victor.ATTACKER);
		assertEquals(result.getVictor(), Victor.ATTACKER);
	}
	
	@Test
	public void testAttackerLoss() {
		assertEquals(result.getAttackerLosses(), 0);
		result.incrementAttackerLosses(1);
		assertEquals(result.getAttackerLosses(), 1);
	}
	
	@Test
	public void testDefenderLoss() {
		assertEquals(result.getDefenderLosses(), 0);
		result.incrementDefenderLosses(1);
		assertEquals(result.getDefenderLosses(), 1);
	}
	
}