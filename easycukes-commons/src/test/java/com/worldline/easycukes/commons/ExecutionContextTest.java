package com.worldline.easycukes.commons;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The class <code>ExecutionContextTest</code> contains tests for the class
 * <code>{@link ExecutionContext}</code>.
 * 
 * @author m.echikhi
 */
public class ExecutionContextTest {

	/**
	 * Run the boolean contains(String) method test.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testContains() throws Exception {
		String key = "projectname";

		boolean result = ExecutionContext.contains(key);

		assertEquals(true, result);
	}

	/**
	 * Run the String get(String) method test.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testGet() throws Exception {
		String key = "projectname";

		String result = ExecutionContext.get(key);

		assertEquals("easycukes", result);
	}

	/**
	 * Run the void put(String,String) method test.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testPut() throws Exception {
		String key = "testOK";
		String value = "true";

		ExecutionContext.put(key, value);

		assertEquals("true", ExecutionContext.get(key));

	}

	/**
	 * Perform pre-test initialization.
	 * 
	 * @throws Exception
	 *             if the initialization fails for some reason
	 * 
	 */
	@Before
	public void setUp() throws Exception {
		ExecutionContext.put("projectname", "easycukes");
	}

	/**
	 * Perform post-test clean-up.
	 * 
	 * @throws Exception
	 *             if the clean-up fails for some reason
	 * 
	 */
	@After
	public void tearDown() throws Exception {
	}
}