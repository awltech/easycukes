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
package com.worldline.easycukes.rest.utils;

/**
 * This {@link RestConstants} class contains all constants that could be
 * manipulated in this project. It mainly consists in keys you can use in order
 * to retrieve some configuration values from the configuration file.
 * 
 * @author mechikhi
 * @version 1.0
 */
public interface RestConstants {

	/**
	 * Key matching with the Jenkins max attempts to do before considering it
	 * failed
	 */
	public static final String MAX_ATTEMPTS_KEY = "max_attempts";

	/**
	 * Key matching with the time to wait before different attempts on Jenkins
	 */
	public static final String TIME_TO_WAIT_KEY = "waiting_time";

	/**
	 * Key matching with the date format to use setting dates
	 */
	public static final String DATE_FORMAT = "date_format";

	/**
	 * Key matching with yesteday used to set parameter date to yesteday
	 */
	public static final String YESTERDAY = "yesterday";

	/**
	 * Key matching with tomorrow used to set parameter date to tomorrow
	 */
	public static final String TOMORROW = "tomorrow";

	/**
	 * Key matching with today used to set parameter date to today
	 */
	public static final String TODAY = "today";

}
