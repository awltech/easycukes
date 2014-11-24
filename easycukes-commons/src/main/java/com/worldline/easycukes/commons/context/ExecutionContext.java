package com.worldline.easycukes.commons.context;

import java.util.HashMap;
import java.util.Map;

/**
 * This {@link ExecutionContext} singleton allows to store the application's
 * execution context in a simple {@link Map}. It allows to store variables in
 * order to share them across all the features of the application.
 * 
 * @author aneveux
 * @version 1.0
 */
public class ExecutionContext {

	private static ThreadLocal<Map<String, String>> context = new ThreadLocal<Map<String, String>>();

	/**
	 * Just a getter for {@link #context}...
	 * 
	 * @return {@link #context}, obviously
	 */
	private static Map<String, String> getContext() {
		if (context.get() == null) {
			context.set(new HashMap<String, String>());
		}
		return context.get();
	}

	/**
	 * Returns true if the {@link ExecutionContext} contains a mapping for the
	 * specified key
	 * 
	 * @param key
	 *            the key whose presence in this map is to be tested
	 * @Return true if this map contains a mapping for the specified key
	 */
	public static boolean contains(final String key) {
		return getContext().containsKey(key);
	}

	/**
	 * Returns the value to which the specified key is mapped in the
	 * {@link ExecutionContext}
	 * 
	 * @param key
	 *            the key to be used in the {@link ExecutionContext} map
	 * @return the value associated to the provided key
	 */
	public static String get(final String key) {
		return getContext().get(key);
	}

	/**
	 * Simply puts values in the {@link ExecutionContext}
	 * 
	 * @param key
	 *            the key to be used in the {@link ExecutionContext} map
	 * @param value
	 *            the value to associate with the provided key
	 */
	public static void put(final String key, final String value) {
		getContext().put(key, value);
	}

}
