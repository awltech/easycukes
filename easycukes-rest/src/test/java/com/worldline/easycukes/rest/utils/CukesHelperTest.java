package com.worldline.easycukes.rest.utils;

import java.text.ParseException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.worldline.easycukes.commons.ExecutionContext;

/**
 * The class <code>CukesHelperTest</code> contains tests for the class
 * <code>{@link CukesHelper}</code>.
 * 
 * @author a513260
 * 
 */
public class CukesHelperTest {

	private static String expression1;
	private static String expression2;
	private static String expression3;
	private static String expression4;
	private static String date_format_json;

	/**
	 * Perform variable's initialization before tests have been running .
	 * 
	 */
	@BeforeClass
	public static void setup() {
		expression1 = "{yesterday}";
		expression2 = "\"{today}\"";
		expression3 = "{25-12-2014}";
		expression4 = "25-12-2014";
		ExecutionContext.put(RestConstants.DATE_FORMAT, "dd-MM-yyyy");
		date_format_json = "2014-12-25T00:00:00:0Z";
	}

	/**
	 * Perform clean-up after all tests have been passed.
	 * 
	 */
	@AfterClass
	public static void teardown() {
		expression1 = null;
		expression2 = null;
		expression3 = null;
		expression4 = null;
	}

	/**
	 * Run the void isExpression(String) method test.
	 * 
	 */
	@Test
	public void isExpressionTest() {
		Assert.assertTrue(CukesHelper.isExpression(expression1));
		Assert.assertTrue(CukesHelper.isExpression(expression2));
		Assert.assertTrue(CukesHelper.isExpression(expression3));
		Assert.assertFalse(CukesHelper.isExpression(expression4));
	}

	/**
	 * Run the void evalExpression(String) method test.
	 * 
	 */
	@Test
	public void evalExpressionTest() {
		try {
			Assert.assertTrue(date_format_json.equals(CukesHelper
					.evalExpression(expression3)));
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
}
