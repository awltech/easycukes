package com.worldline.easycukes.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The class <code>DataInjectorTest</code> contains tests for the class
 * <code>{@link DataInjector}</code>.
 * 
 * @author m.echikhi
 */
public class DataInjectorTest {
	/**
	 * Run the DataInjector() constructor test.
	 * 
	 */
	@Test
	public void testDataInjector() throws Exception {
		DataInjector result = new DataInjector();
		assertNotNull(result);
	}

	/**
	 * Run the String injectData(String) method test.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testInjectData() throws Exception {
		String s = "test data injection $__projectname__$";

		String result = DataInjector.injectData(s);

		assertEquals("test data injection easycukes", result);
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