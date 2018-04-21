package edu.T10.Controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
		t0 = new Territory();
		t0.assignOwner(0);
		t0.setId(5);
		t0.setName("TestName0");
		t0.setContinentID(3);
		t0.setAdjTerritories(new int[] {1, 2, 3});
		t0.setArmyStrength(5);
		t1 = new Territory();
		t1.assignOwner(1);
		t1.setId(10);
		t1.setName("TestName1");
		t1.setContinentID(5);
		t1.setAdjTerritories(new int[] {1, 2, 3});
		t1.setArmyStrength(3);
	}

	@Test
	public void testConductInvasion() {
		
	}
}