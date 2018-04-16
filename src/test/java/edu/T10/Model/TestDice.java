package edu.T10.Model;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestDice {
	Dice d;
	
	@Before
	public void setUp() {
		d = new Dice();
	}
	
	@Test
	public void testRoll() {
		assertTrue(d.roll() >= 1 && d.roll() <= 6);
	}
}
