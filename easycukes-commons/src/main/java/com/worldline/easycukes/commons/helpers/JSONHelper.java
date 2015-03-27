/*
 * EasyCukes is just a framework aiming at making Cucumber even easier than what it already is.
 * Copyright (C) 2014 Worldline or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package com.worldline.easycukes.commons.helpers;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private final static Logger LOG = LoggerFactory
			.getLogger(JSONHelper.class);

	/**
	 * {@link JSONParser} to be used in order to extract JSON data from the
	 * {@link String} objects to be submitted.
	 */
	final static JSONParser parser = new JSONParser();

	/**
	 * Returns <b>true</b> if JSON object o1 is equals to JSON object o2.
	 * 
	 * @param o1
	 *            a {@link JSONObject} containing some JSON data
	 * @param o2
	 *            another {@link JSONObject} containing some JSON data
	 * @return true if the json objects are equals
	 */
	public static boolean equals(JSONObject o1, JSONObject o2) {
		if (o1 == o2)
			return true;
		if (o1.size() != o2.size())
			return false;
		try {
			final Iterator<Entry<String, Object>> i = o1.entrySet().iterator();
			while (i.hasNext()) {
				final Entry<String, Object> e = i.next();
				final String key = e.getKey();
				final Object value1 = e.getValue();
				final Object value2 = o2.get(key);
				if (value1 == null) {
					if (!(o2.get(key) == null && o2.containsKey(key)))
						return false;
				} else if (value1 instanceof JSONObject) {
					if (!(value2 instanceof JSONObject))
						return false;
					if (!equals((JSONObject) value1, (JSONObject) value2))
						return false;
				} else if (value1 instanceof JSONArray) {

					if (!(value2 instanceof JSONArray))
						return false;
					if (!equals((JSONArray) value1, (JSONArray) value2))
						return false;
				} else if (!value1.equals(value2))
					return false;
			}
		} catch (final Exception unused) {
			unused.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Returns <b>true</b> if JSON array a1 is equals to JSON array a2.
	 * 
	 * @param a1
	 *            a {@link JSONArray} containing some JSON data
	 * @param a2
	 *            another {@link JSONArray} containing some JSON data
	 * @return true if the json arrays are equals
	 */
	public static boolean equals(JSONArray a1, JSONArray a2) {
		if (a1 == a2)
			return true;
		if (a1.size() != a2.size())
			return false;

		final ListIterator i1 = a1.listIterator();
		ListIterator i2 = null;
		boolean found = false;
		while (i1.hasNext()) {
			final Object o1 = i1.next();
			found = false;
			i2 = a2.listIterator();
			if (o1 instanceof JSONObject)
				while (i2.hasNext()) {
					final Object o2 = i2.next();
					if (!(o2 instanceof JSONObject))
						return false;
					if (equals((JSONObject) o1, (JSONObject) o2))
						found = true;
				}
			else if (o1 instanceof JSONArray)
				while (i2.hasNext()) {
					final Object o2 = i2.next();
					if (!(o2 instanceof JSONArray))
						return false;
					if (equals((JSONArray) o1, (JSONArray) o2))
						found = true;
				}
			else
				while (i2.hasNext()) {
					final Object o2 = i2.next();
					if (o1.equals(o2))
						found = true;
				}
			if (!found)
				return false;

		}
		return true;
	}

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
			return equals(json1, json2);
		} catch (final ParseException e) {
			LOG.error(e.getMessage(), e);
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
			LOG.error(e.getMessage(), e);
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
			LOG.error(e.getMessage(), e);
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
			LOG.error(e.getMessage(), e);
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
