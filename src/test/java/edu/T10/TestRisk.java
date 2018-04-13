package edu.T10;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

public class TestRisk {
	Risk risk;
	
	@Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
	
	@Before
	public void setUp() {
		risk = new Risk();
	}
	
	@Test
	public void testRisk() {
		Risk.main(new String[] {});
        assertEquals("Hello world!", systemOutRule.getLog());
	}
	
}
