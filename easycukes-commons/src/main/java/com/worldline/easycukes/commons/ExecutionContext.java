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
package com.worldline.easycukes.commons;

import lombok.experimental.UtilityClass;

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
@UtilityClass
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
