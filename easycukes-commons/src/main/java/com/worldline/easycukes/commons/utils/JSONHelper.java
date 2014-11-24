package com.worldline.easycukes.commons.utils;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This {@link JSONHelper} class provides various methods allowing to manipulate
 * JSON data. It uses internally {@link JSONObject} from <i>org.json.simple</i>.
 *
 * @author aneveux
 * @version 1.0
 */
public class JSONHelper {

	/**
	 * {@link Logger} to be used in order to log a few things about execution
	 */
	private final static Logger LOGGER = Logger
			.getLogger(Constants.CUKES_TESTS_LOGGER);

	/**
	 * {@link JSONParser} to be used in order to extract JSON data from the
	 * {@link String} objects to be submitted.
	 */
	final static JSONParser parser = new JSONParser();

	/**
	 * Returns <b>true</b> if JSON format of s1 is equals to JSON format of s2,
	 * ignoring the case.
	 *
	 * @param s1
	 *            a {@link String} containing some JSON data
	 * @param s2
	 *            another {@link String} containing some JSON data
	 * @return true if both {@link String} objects are containing the same JSON
	 *         data (ignoring the case)
	 */
	public static boolean equals(final String s1, final String s2) {
		try {
			final JSONObject json1 = (JSONObject) parser.parse(s1);
			final JSONObject json2 = (JSONObject) parser.parse(s2);
			return json1.equals(json2);
		} catch (final ParseException e) {
			LOGGER.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * Returns <b>true</b> if JSON format of s1 is containing JSON format of s2
	 *
	 * @param s1
	 *            a {@link String} containing some JSON data
	 * @param s2
	 *            another {@link String} containing some JSON data
	 * @return true if s1 contains s2 data
	 */
	public static boolean contains(final String s1, final String s2) {
		try {
			final JSONObject json1 = (JSONObject) parser.parse(s1);
			final JSONObject json2 = (JSONObject) parser.parse(s2);
			return json1.toJSONString().contains(json2.toJSONString());
		} catch (final ParseException e) {
			LOGGER.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * Returns a proper representation in JSON of a {@link String} object
	 * already containing JSON (it basically deals with indentation)
	 *
	 * @param s
	 *            a {@link String} containing JSON to be cleaned
	 * @return a clean JSON representation of the JSON data contained in the
	 *         provided {@link String}
	 * @throws ParseException
	 *             if the provided {@link String} does not actually contain a
	 *             valid JSON data
	 */
	public static String toProperJSON(final String s) throws ParseException {
		final Object obj = parser.parse(s);
		if (obj instanceof JSONObject)
			return ((JSONObject) obj).toJSONString();
		if (obj instanceof JSONArray)
			return ((JSONArray) obj).toJSONString();
		return s;
	}

	/***
	 * Converts given {@link String} s to {@link JSONObject} format and returns
	 * it
	 *
	 * @param s
	 *            a {@link String} containing some JSON data
	 * @return a {@link JSONObject} corresponding to the JSON data contained in
	 *         the provided {@link String}
	 * @throws ParseException
	 *             if the provided {@link String} does not actually contain some
	 *             valid JSON
	 */
	public static JSONObject toJSONObject(final String s) throws ParseException {
		try {
			return (JSONObject) parser.parse(s);
		} catch (final ParseException e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * Returns the value of a given property from a particular
	 * {@link JSONObject}
	 *
	 * @param jsonObject
	 *            the {@link JSONObject} to be used for extracting values
	 * @param property
	 *            the property you'd like to get from the specified
	 *            {@link JSONObject}
	 * @return the value of the specified property if it's found, or null if it
	 *         cannot be found
	 * @throws ParseException
	 */
	public static String getPropertyValue(final String s, final String property)
			throws ParseException {
		try {
			final Object obj = parser.parse(s);
			if (obj != null)
				if (obj instanceof JSONObject)
					return JSONHelper.getValue((JSONObject) obj, property);
				else if (obj instanceof JSONArray)
					return JSONHelper.getValue((JSONArray) obj, property);
		} catch (final ParseException e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		}
		return null;
	}

	/**
	 * Returns the value of a given property from a particular
	 * {@link JSONObject}
	 *
	 * @param jsonObject
	 *            the {@link JSONObject} to be used for extracting values
	 * @param property
	 *            the property you'd like to get from the specified
	 *            {@link JSONObject}
	 * @return the value of the specified property if it's found, or null if it
	 *         cannot be found
	 */
	public static String getValue(final JSONObject jsonObject,
			final String property) {
		if (jsonObject != null)
			if (jsonObject.get(property) != null)
				return jsonObject.get(property).toString();
		final int index = property.indexOf(".");
		if (index > 0) {
			final Object object = getProperty(jsonObject,
					property.substring(0, index));
			if (object != null) {
				if (object instanceof JSONObject)
					return getValue((JSONObject) object,
							property.substring(index + 1));
				if (object instanceof JSONArray) {
					final JSONArray jsonArray = (JSONArray) object;
					if (jsonArray.size() > 0)
						return getValue((JSONObject) jsonArray.get(0),
								property.substring(index + 1));
				}
			}
		}
		return null;
	}

	/**
	 * Returns the value of a given property from a particular
	 * {@link JSONObject}
	 *
	 * @param jsonObject
	 *            the {@link JSONObject} to be used for extracting values
	 * @param property
	 *            the property you'd like to get from the specified
	 *            {@link JSONObject}
	 * @return the value of the specified property if it's found, or null if it
	 *         cannot be found
	 */
	public static String getValue(final JSONArray jsonArray,
			final String property) {
		if (jsonArray == null)
			return null;
		final int index = property.indexOf(".");
		if (index > 0) {
			final Object object = getProperty(jsonArray,
					property.substring(0, index));
			if (object != null) {
				if (object instanceof JSONObject)
					return getValue((JSONObject) object,
							property.substring(index + 1));
				if (object instanceof JSONArray)
					return getValue((JSONArray) object,
							property.substring(index + 1));
			}
		} else
			return getProperty(jsonArray, property).toString();
		return null;
	}

	private static Object getProperty(JSONObject jsonObject, String simpleProp) {
		final int idx1 = simpleProp.indexOf("[");
		if (idx1 > 0) {
			final JSONArray jsonArray = (JSONArray) jsonObject.get(simpleProp
					.substring(0, idx1));
			return getProperty(jsonArray, simpleProp.substring(idx1));
		} else
			return jsonObject.get(simpleProp);
	}

	@SuppressWarnings("unchecked")
	private static Object getProperty(JSONArray jsonArray, String simpleProp) {
		if (simpleProp.startsWith("[")) {
			final int idx2 = simpleProp.indexOf("]");
			if (idx2 > 0) {
				final String exp = simpleProp.substring(1, idx2);
				if (exp.contains("=")) {
					final String[] expParams = exp.split("=");
					for (final Iterator<JSONObject> iterator = jsonArray
							.iterator(); iterator.hasNext();) {
						final JSONObject jsonObject = iterator.next();
						if (getValue(jsonObject, expParams[0]).equals(
								expParams[1]))
							return jsonObject;
					}
				} else if (StringUtils.isNumeric(exp)
						&& jsonArray.size() > Integer.parseInt(exp))
					return jsonArray.get(Integer.parseInt(exp));
			}
		}
		return null;
	}

	/**
	 * Returns a {@link JSONArray} format from a given String containing some
	 * JSON data
	 *
	 * @param s
	 *            a {@link String} containing some JSON data
	 * @return a {@link JSONArray} representation of the provided {@link String}
	 * @throws ParseException
	 *             if the provided {@link String} does not actually contain some
	 *             valid JSON
	 */
	public static JSONArray toJSONArray(final String s) throws ParseException {
		try {
			return (JSONArray) parser.parse(s);
		} catch (final ParseException e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * Returns a proper representation in JSON of a {@link String} object
	 * already containing JSON (it basically deals with indentation)
	 *
	 * @param s
	 *            a {@link String} containing JSON to be cleaned
	 * @return a clean JSON representation of the JSON data contained in the
	 *         provided {@link String}
	 * @throws ParseException
	 *             if the provided {@link String} does not actually contain a
	 *             valid JSON data
	 */
	public static Object parseJSON(final String s) throws ParseException {
		return parser.parse(s);

	}
}
