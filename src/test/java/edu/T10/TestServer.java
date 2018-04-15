package edu.T10;

import static org.junit.Assert.assertEquals;

import java.io.*;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestServer {
	Server s;
	
	@Mock
	private Session session;
	
	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
	
	@Before
	public void setUp() {
		s = new Server();
	}
	
	@Test
	public void testOnOpen() {
	}
	
	@Test
	public void testOnMessage() {
	}
	
	@Test
	public void testOnClose() {
	}

	@Test
	public void testOnError() {
	}
}
