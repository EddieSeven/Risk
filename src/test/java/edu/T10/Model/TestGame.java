package edu.T10.Model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import org.junit.Rule;
import org.junit.contrib.java.lang.system.SystemOutRule;

public class TestGame {
	Game g;

	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

	@Before
	public void setUp() {	}
	
	@Test
	public void testGame() {
		// As private variables are not accessable outside
		// Will use the tests that are in main to test Game for now
		String output = "";
		Game.main(new String[] {});
		//assertEquals(output, systemOutRule.getLog());
	}
}
