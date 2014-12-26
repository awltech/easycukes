package com.worldline.easycukes.rest.utils;

import java.text.ParseException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.worldline.easycukes.commons.ExecutionContext;

/**
 * The class <code>DateHelperTest</code> contains tests for the class
 * <code>{@link DateHelper}</code>.
 * 
 * @author a513260
 * 
 */

public class DateHelperTest {

	private static String date;
	private static String date_format_json;

	/**
	 * Perform variable's initialization before tests have been running .
	 * 
	 */
	@BeforeClass
	public static void setup() {
		ExecutionContext.put(RestConstants.DATE_FORMAT, "dd-MM-yyyy");
		date = "24-12-2014";
		date_format_json = "2014-12-24T00:00:00:0Z";

	}

	/**
	 * Perform clean-up after all tests have been passed.
	 * 
	 */
	@AfterClass
	public static void teardown() {
		date = null;
		date_format_json = null;
	}

	/**
	 * Run the void isDateExpression(String) method test.
	 * 
	 */
	@Test
	public void isDateExpressionTest() {
		Assert.assertTrue(DateHelper.isDateExpression(RestConstants.TODAY));
		Assert.assertTrue(DateHelper.isDateExpression(RestConstants.YESTERDAY));
		Assert.assertTrue(DateHelper.isDateExpression(RestConstants.TOMORROW));
		Assert.assertTrue(DateHelper.isDateExpression(date));
		Assert.assertFalse(DateHelper.isDateExpression("test"));
	}

	/**
	 * Run the void parseDateToJson(String) method test.
	 * 
	 */
	@Test
	public void parseDateToJsonTest() {
		try {
			Assert.assertTrue(date_format_json.equals(DateHelper
					.parseDateToJson(date)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Run the void getDateValue(String) method test.
	 * 
	 */
	@Test
	public void getDateValueTodayTest() {
		try {
			Assert.assertTrue(date_format_json.equals(DateHelper
					.getDateValue(date)));
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
}
