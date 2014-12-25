package com.worldline.easycukes.commons.helpers;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The class <code>DomHelperTest</code> contains tests for the class
 * <code>{@link DomHelper}</code>.
 * 
 * @author m.echikhi
 */
public class DomHelperTest {

	private String filename;

	/**
	 * Run the String getElementContent(String,String) method test.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testGetElementContent() throws Exception {
		String name = "artifactId";

		String result = DomHelper.getElementContent(filename, name);

		assertEquals("tests.project-template", result);
	}

	/**
	 * Run the void setElementContent(String,String,String) method test.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testSetElementContent() throws Exception {
		String name = "name";
		String value = "Project Example - To be used for testing / Demo";

		DomHelper.setElementContent(filename, name, value);

		assertEquals(value, DomHelper.getElementContent(filename, name));

	}

	/**
	 * Perform pre-test initialization.
	 * 
	 * @throws Exception
	 *             if the initialization fails for some reason
	 * 
	 * @generatedBy CodePro at 25/12/14 16:03
	 */
	@Before
	public void setUp() throws Exception {
		filename = "target/test-classes/project-template-pom.xml";
	}

	/**
	 * Perform post-test clean-up.
	 * 
	 * @throws Exception
	 *             if the clean-up fails for some reason
	 * 
	 * @generatedBy CodePro at 25/12/14 16:03
	 */
	@After
	public void tearDown() throws Exception {
	}
}