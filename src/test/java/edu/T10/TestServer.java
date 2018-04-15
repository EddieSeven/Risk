//package edu.T10.Controller;
//
//import static org.junit.Assert.assertEquals;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.contrib.java.lang.system.SystemOutRule;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//@RunWith(MockitoJUnitRunner.class)
//public class TestServer {
//	Server s;
//	
//	@Mock
//	private HttpServletRequest request;
//	private HttpServletResponse response;
//	
//	@Rule
//    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
//	
//	@Before
//	public void setUp() {
//		s = new Server();
//	}
//	
////	@Test
////	public void testDoGet() {
////		try {
////			s.doGet(request, response);
////			assertEquals("Hello world!\n", systemOutRule.getLog());
////		} catch (IOException e) {
////			e.printStackTrace();
////		} catch (ServletException e) {
////			e.printStackTrace();
////		}
////        
////	}
////	
////	@Test
////	public void testDoPost() {
////		try {
////			s.doPost(request, response);
////			assertEquals("Hello world!\n", systemOutRule.getLog());
////		} catch (IOException e) {
////			e.printStackTrace();
////		} catch (ServletException e) {
////			e.printStackTrace();
////		}
////        
////	}
//	
//}
