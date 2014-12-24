package com.worldline.easycukes.rest.utils;

import java.text.ParseException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.worldline.easycukes.commons.ExecutionContext;

public class DateHelperTest {

	private static String date;
	private static String date_format_json;

	@BeforeClass
	public static void setup() {
		ExecutionContext.put(RestConstants.DATE_FORMAT, "dd-MM-yyyy");
		date = "24-12-2014";
		date_format_json = "2014-12-24T00:00:00:0Z";

	}

	@AfterClass
	public static void teardown() {
		date = null;
		date_format_json = null;
	}

	@Test
	public void isDateExpressionTest() {
		Assert.assertTrue(DateHelper.isDateExpression(RestConstants.TODAY));
		Assert.assertTrue(DateHelper.isDateExpression(RestConstants.YESTERDAY));
		Assert.assertTrue(DateHelper.isDateExpression(RestConstants.TOMORROW));
		Assert.assertTrue(DateHelper.isDateExpression(date));
		Assert.assertFalse(DateHelper.isDateExpression("test"));
	}

	@Test
	public void parseDateToJsonTest() {
		try {
			Assert.assertTrue(date_format_json.equals(DateHelper
					.parseDateToJson(date)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

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
