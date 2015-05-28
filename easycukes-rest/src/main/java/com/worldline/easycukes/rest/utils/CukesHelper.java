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
import lombok.experimental.UtilityClass;

import java.text.ParseException;

/**
 * This {@link CukesHelper} class provides various methods allowing to
 * manipulate the expressions and the execution context.
 *
 * @author mechikhi
 * @version 1.0
 */
@UtilityClass
public class CukesHelper {

    /**
     * Checks if the given string matching an expression
     *
     * @param expression the string to be checked
     * @Return true if the specified string is an expression
     */
    public static boolean isExpression(String expression) {
        if (expression.startsWith("{") && expression.endsWith("}"))
            return true;
        if (expression.startsWith("\"{") && expression.endsWith("}\""))
            return true;
        return false;
    }

    /**
     * Allows to evaluate the given expression
     *
     * @param expression
     * @return the result of the evaluation of the specified expression
     * @throws ParseException
     */
    public static String evalExpression(String expression)
            throws ParseException {
        if (expression == null)
            return null;
        final String toEval = expression
                .substring(expression.indexOf("{") + 1, expression.indexOf("}"))
                .replaceAll(" ", "").toLowerCase();
        if (DateHelper.isDateExpression(toEval))
            return DateHelper.getDateValue(toEval);
        throw new ParseException(expression + " is not a valid expression!", 0);
    }

    /**
     * Sets the value in the execution context
     *
     * @param param the key to be used in the context
     * @param value the value to associate with the provided param
     */
    public static void setParameter(String param, String value) {
        ExecutionContext.put(param.toLowerCase(), value);
    }

}
