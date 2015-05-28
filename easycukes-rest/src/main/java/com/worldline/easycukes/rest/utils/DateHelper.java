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

import com.worldline.easycukes.commons.ExecutionContext;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This {@link DateHelper} class provides various methods allowing to manipulate
 * dates
 *
 * @author szamd
 * @version 1.0
 */
@Slf4j
@UtilityClass
public class DateHelper {

    /**
     * Used to convert date value in json date format
     *
     * @param value
     * @return
     */
    public static String convertDateToJsonFormat(@NonNull final Date value) {
        log.info("setting the date value " + value + " to format json");
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(value);
        return calendar.get(Calendar.YEAR) + "-"
                + formatTo2Digit(calendar.get(Calendar.MONTH) + 1) + "-"
                + formatTo2Digit(calendar.get(Calendar.DAY_OF_MONTH)) + "T"
                + formatTo2Digit(calendar.get(Calendar.HOUR_OF_DAY)) + ":"
                + formatTo2Digit(calendar.get(Calendar.MINUTE)) + ":"
                + formatTo2Digit(calendar.get(Calendar.SECOND)) + ":"
                + calendar.get(Calendar.MILLISECOND) + "Z";
    }

    /**
     * Checks if the given string matching a date expression
     *
     * @param expression
     * @return true if the specified string matching a date expression
     */
    public static boolean isDateExpression(@NonNull String expression) {
        if (expression.startsWith(RestConstants.TODAY)
                || expression.startsWith(RestConstants.YESTERDAY)
                || expression.startsWith(RestConstants.TOMORROW))
            return true;
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                ExecutionContext.get(RestConstants.DATE_FORMAT));
        try {
            simpleDateFormat.parse(expression);
            return true;
        } catch (final ParseException e) {
            return false;
        }
    }

    /**
     * Used to get date value
     *
     * @param expression the expression to parse
     * @return the date value in json format for the specified expression
     * @throws ParseException
     */
    public static String getDateValue(@NonNull final String expression)
            throws ParseException {
        log.info("Getting the date value from " + expression);
        final Calendar calendar = Calendar.getInstance();
        if (!expression.contains(RestConstants.YESTERDAY)
                && !expression.contains(RestConstants.TODAY)
                && !expression.contains(RestConstants.TOMORROW))
            return parseDateToJson(expression);

        int value = 0;
        int calendarField = -1;
        String toAdd = null;
        if (expression.startsWith(RestConstants.TODAY))
            toAdd = expression.substring(expression
                    .indexOf(RestConstants.TODAY)
                    + RestConstants.TODAY.length());
        else if (expression.startsWith(RestConstants.YESTERDAY)) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            toAdd = expression.substring(expression
                    .indexOf(RestConstants.YESTERDAY)
                    + RestConstants.YESTERDAY.length());
        } else if (expression.startsWith(RestConstants.TOMORROW)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            toAdd = expression.substring(expression
                    .indexOf(RestConstants.TOMORROW)
                    + RestConstants.TOMORROW.length());
        } else
            throw new ParseException("the date expression is not valid ! : "
                    + expression, 0);
        if (StringUtils.isNotEmpty(toAdd)) {
            if (toAdd.endsWith("year")) {
                value = Integer.valueOf(toAdd.substring(0,
                        toAdd.indexOf("year")));
                calendarField = Calendar.YEAR;
            } else if (toAdd.endsWith("day")) {
                value = Integer
                        .valueOf(toAdd.substring(0, toAdd.indexOf("day")));
                calendarField = Calendar.DAY_OF_MONTH;
            } else if (toAdd.endsWith("month")) {
                value = Integer.valueOf(toAdd.substring(0,
                        toAdd.indexOf("month")));
                calendarField = Calendar.MONTH;
            } else {
                value = Integer.valueOf(toAdd);
                calendarField = Calendar.DAY_OF_MONTH;
            }
            calendar.add(calendarField, value);

        }
        return convertDateToJsonFormat(calendar.getTime());
    }

    /**
     * Used to get date value
     *
     * @return
     * @throws ParseException
     */
    public static String parseDateToJson(@NonNull final String sDate)
            throws ParseException {
        log.info("Parsing date " + sDate);
        if (sDate == null)
            return null;
        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                ExecutionContext.get(RestConstants.DATE_FORMAT));
        try {
            calendar.setTime(simpleDateFormat.parse(sDate));
            return convertDateToJsonFormat(calendar.getTime());
        } catch (final ParseException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Used to format number in 2 digits
     *
     * @param number the number to format
     * @return the string representation in 2 digit for the given number
     */
    private static String formatTo2Digit(int number) {
        return number < 10 ? "0" + number : Integer.toString(number);
    }

}
