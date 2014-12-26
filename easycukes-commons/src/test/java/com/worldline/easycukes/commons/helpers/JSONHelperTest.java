package com.worldline.easycukes.commons.helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The class <code>JSONHelperTest</code> contains tests for the class
 * <code>{@link JSONHelper}</code>.
 * 
 * @author m.echikhi
 */
public class JSONHelperTest {

	/**
	 * Run the boolean equals(String,String) method test.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testEquals() throws Exception {
		String s1 = "{" + "	\"id\": 123,"
				+ "	\"description\": \"easy cukes desc\","
				+ "	\"name\": \"easycukes\"," + "	\"group\":\"default\","
				+ " }";
		String s2 = "{" + "	\"group\":\"default\","
				+ "	\"name\": \"easycukes\","
				+ "	\"description\": \"easy cukes desc\"," + "\"id\": 123"
				+ " }";

		assertEquals(true, JSONHelper.equals(s1, s2));
	}

	/**
	 * Run the boolean equals(JSONArray,JSONArray) method test.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testEquals2() throws Exception {
		JSONArray a1 = JSONHelper.toJSONArray("[{" + "	\"id\": 123,"
				+ "	\"description\": \"easy cukes desc\","
				+ "	\"name\": \"easycukes\"," + "	\"group\":\"default\","
				+ " }," + "{" + "	\"id\": 321,"
				+ "	\"description\": \"easy cukes desc 2\","
				+ "	\"name\": \"easycukes2\"," + "	\"group\":\"cukes\","
				+ " }]");
		JSONArray a2 = JSONHelper.toJSONArray("[{" + "	\"id\": 321,"
				+ "	\"description\": \"easy cukes desc 2\","
				+ "	\"name\": \"easycukes2\"," + "	\"group\":\"cukes\","
				+ " }, {" + "	\"id\": 123,"
				+ "	\"description\": \"easy cukes desc\","
				+ "	\"name\": \"easycukes\"," + "	\"group\":\"default\","
				+ " }]");

		boolean result = JSONHelper.equals(a1, a2);

		assertEquals(true, result);
	}

	/**
	 * Run the boolean equals(JSONObject,JSONObject) method test.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testEquals3() throws Exception {
		JSONObject o1 = new JSONObject();
		o1.put("id", 123L);
		o1.put("description", "easy cukes desc");
		o1.put("name", "easycukes");
		o1.put("group", "default");

		JSONObject o2 = JSONHelper.toJSONObject("{" + "	\"group\":\"default\","
				+ "	\"name\": \"easycukes\","
				+ "	\"description\": \"easy cukes desc\"," + "\"id\": 123"
				+ " }");

		assertEquals(true, JSONHelper.equals(o1, o2));
	}

	/**
	 * Run the String getPropertyValue(String,String) method test.
	 * 
	 * @throws Exception
	 * 
	 */
	public void testGetPropertyValue() throws Exception {
		String s = "{" + "	\"group\":\"default\","
				+ "	\"name\": \"easycukes\","
				+ "	\"description\": \"easy cukes desc\"," + "\"id\": 123"
				+ " }";
		String property = "description";

		String result = JSONHelper.getPropertyValue(s, property);

		assertEquals("easy cukes desc", result);
	}

	/**
	 * Run the String getValue(JSONArray,String) method test.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testGetValue() throws Exception {
		JSONArray jsonArray = JSONHelper.toJSONArray("[{" + "	\"id\": 123,"
				+ "	\"description\": \"easy cukes desc\","
				+ "	\"name\": \"easycukes\"," + "	\"group\":\"default\","
				+ " }," + "{" + "	\"id\": 321,"
				+ "	\"description\": \"easy cukes desc 2\","
				+ "	\"name\": \"easycukes2\"," + "	\"group\":\"cukes\","
				+ " }]");
		String property = "[id=321].name";

		String result = JSONHelper.getValue(jsonArray, property);

		assertEquals("easycukes2", result);
	}

	/**
	 * Run the String getValue(JSONObject,String) method test.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testGetValue2() throws Exception {
		JSONObject o1 = new JSONObject();
		o1.put("id", 123);
		o1.put("description", "easy cukes desc");
		o1.put("name", "easycukes");
		o1.put("group", "default");
		String property = "group";

		String result = JSONHelper.getValue(o1, property);

		assertEquals("default", result);
	}

	/**
	 * Run the Object parseJSON(String) method test.
	 * 
	 * @throws Exception
	 * 
	 */
	public void testParseJSON() throws Exception {
		String s = "{" + "	\"id\": 123,"
				+ "	\"description\": \"easy cukes desc\","
				+ "	\"name\": \"easycukes\"," + "	\"group\":\"default\","
				+ " }";

		Object result = JSONHelper.parseJSON(s);

		assertNotNull(result);
	}

	/**
	 * Run the JSONArray toJSONArray(String) method test.
	 * 
	 * @throws Exception
	 * 
	 */
	public void testToJSONArray() throws Exception {
		String s = "[{" + "	\"id\": 123,"
				+ "	\"description\": \"easy cukes desc\","
				+ "	\"name\": \"easycukes\"," + "	\"group\":\"default\","
				+ " }," + "{" + "	\"id\": 321,"
				+ "	\"description\": \"easy cukes desc 2\","
				+ "	\"name\": \"easycukes2\"," + "	\"group\":\"cukes\","
				+ " }]";

		JSONArray result = JSONHelper.toJSONArray(s);

		assertNotNull(result);
	}

	/**
	 * Run the JSONObject toJSONObject(String) method test.
	 * 
	 * @throws Exception
	 * 
	 */
	public void testToJSONObject() throws Exception {
		String s = "{" + "	\"id\": 123,"
				+ "	\"description\": \"easy cukes desc\","
				+ "	\"name\": \"easycukes\"," + "	\"group\":\"default\","
				+ " }";

		JSONObject result = JSONHelper.toJSONObject(s);

		assertNotNull(result);
	}

	/**
	 * Run the String toProperJSON(String) method test.
	 * 
	 * @throws Exception
	 * 
	 */
	public void testToProperJSON() throws Exception {
		String s = "[{" + "	\"id\": 123,"
				+ "	\"description\": \"easy cukes desc\","
				+ "	\"name\": \"easycukes\"," + "	\"group\":\"default\","
				+ " }," + "{" + "	\"id\": 321,"
				+ "	\"description\": \"easy cukes desc 2\","
				+ "	\"name\": \"easycukes2\"," + "	\"group\":\"cukes\","
				+ " }]";

		String result = JSONHelper.toProperJSON(s);

		assertNotNull(result);
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