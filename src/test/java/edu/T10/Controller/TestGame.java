package edu.T10.Controller;

import static org.junit.Assert.assertTrue;

import edu.T10.Controller.Game;
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
}
