package edu.T10.Model.Board;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.T10.Model.Board.Continent;

public class TestContinent {
	
	Continent c;
	
	@Before
	public void setUp() {
		c = new Continent();
	}
	
	@Test
	public void testToString() {
		c.id = 0;
		c.name = "Name";
		c.members = new int[] {1, 2};
		assertEquals(c.toString(), "0 Name [1, 2]");
	}
	
}
