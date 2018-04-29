package edu.T10;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.*;
import javax.websocket.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import edu.T10.Model.Board.Territory;

@RunWith(MockitoJUnitRunner.class)
public class TestServer {
	Server s;

	@Mock
	private Session session;

	@Mock
	private RemoteEndpoint.Basic remote;

	@Mock
	private Throwable throwable;

	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

	@Before
	public void setUp() {
		s = new Server();
		when(session.getId()).thenReturn("0");
		when(session.getBasicRemote()).thenReturn(remote);
	}

	@Test
	public void testOnOpen() {
		try {
			s.onOpen(session);
			assertEquals("[ServerSide] 0 open successfully" + System.lineSeparator(), systemOutRule.getLog());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testOnMessageInit() throws Exception {
		s.onMessage(session, "{\"Action\":\"Init\",\"names\":\"A GREEN,B YELLOW,C RED,D BLUE,E PINK,F GREY\"}");
		assertTrue(systemOutRule.getLog().contains(
				"[ServerSide] Received message : {\"Action\":\"Init\",\"names\":\"A GREEN,B YELLOW,C RED,D BLUE,E PINK,F GREY\"}"));

		s.onMessage(session, "{\"Action\":\"Reinforce\",\"territoryID\":24,\"unitValue\":7}");
		assertTrue(systemOutRule.getLog().contains("{\"Action\":\"Reinforce\",\"territoryID\":24,\"unitValue\":7}"));
		
		s.onMessage(session, "{\"Action\":\"Fortify\",\"fromTerritoryID\":24,\"toTerritoryID\":22,\"unitValue\":1}");
		assertTrue(systemOutRule.getLog().contains("{\"Action\":\"Fortify\",\"fromTerritoryID\":24,\"toTerritoryID\":22,\"unitValue\":1}"));
		
		s.onMessage(session, "{\"Action\":\"EndTurn\"}");
		assertTrue(systemOutRule.getLog().contains("[ServerSide] Received message : {\"Action\":\"EndTurn\"}"));
	}

	@Test
	public void testOnClose() throws IOException {
		s.onClose(session);
		assertEquals("[ServerSide] 0 socket closed" + System.lineSeparator(), systemOutRule.getLog());
	}

	@Test
	public void testOnError() {
		s.onError(session, throwable);
		assertEquals("[ServerSide] 0 gets error" + System.lineSeparator(), systemOutRule.getLog());
	}
}
