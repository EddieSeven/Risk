package edu.T10.Controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Vector;

import edu.T10.Controller.*;
import edu.T10.Model.InvasionResult;
import edu.T10.Model.Board.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestBattleController {

	private BattleController controller;
	private Territory t0;
	private Territory t1;
	
	@Mock
	private Board board;
	
	@Mock
	private Game game;
	
	@Mock
	private InvasionResult result;
	

	@Before
	public void setUp() {	
	}

	@Test
	public void testConductInvasion() {
		
	}
}