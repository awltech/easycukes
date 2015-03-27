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

import com.worldline.easycukes.commons.config.EasyCukesConfiguration;
import com.worldline.easycukes.commons.config.beans.CommonConfigurationBean;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This {@link DataInjector} class allows to deal with dynamic data to be used
 * in the tests scenarios. It allows to introduce a way of defining variables
 * using the <i>$__variable__$</i> syntax.
 * <p/>
 * It simply generates some random values for the variables and injects it in
 * the execution context.
 *
 * @author aneveux
 * @version 1.0
 */
public class DataInjector {

    /**
     * Token allowing to define the start of a variable
     */
    public final static String TOKEN_START = "$__";

    /**
     * Token allowing to define the end of a variable
     */
    public final static String TOKEN_END = "__$";

    /**
     * {@link Pattern} allowing to find the variables in the {@link String}
     * elements to be submitted
     */
    protected final static Pattern p = Pattern.compile("\\$__([a-zA-Z]+)__\\$");

    /**
     * Searches for variables in the provided {@link String}, then replaces them
     * by some random generated {@link String} (it uses internally
     * {@link RandomStringUtils} from Apache Commons. Finally, it adds the
     * generated variable in the execution context so it can be retrieved using
     * the name of the variable. Also, if the execution context was already
     * containing a generated value for the variable, it just retrieves it from
     * the context, and injects the data directly.
     *
     * @param s {@link String} in which you'd like variables to be generated /
     *          injected
     * @return the same {@link String} as provided, except that variables will
     * be replaced by the value present in the execution context
     */
    public static String injectData(final String s) {
        EasyCukesConfiguration<CommonConfigurationBean> configuration = new EasyCukesConfiguration<>(CommonConfigurationBean.class);
        // 0. We store the initial string in order to use it for injecting
        // tokens
        String result = s;
        // 1. Apply pattern on the string to extract all tokens
        final Matcher m = p.matcher(s);
        // 2. While tokens are processed
        while (m.find()) {
            // 3. We extract the token content
            final String token = m.group(1);
            // 4. If the token has never been identified and is not present in
            // the context
            if (!ExecutionContext.contains(token)
                    || ExecutionContext.get(token) == null)
                // 5. If the token is present in the config file
                if (configuration.getValues().inject(token) != null)
                    ExecutionContext.put(token, configuration.getValues().inject(token));
                else if (configuration.getValues().variables != null && configuration.getValues().variables.containsKey(token))
                    ExecutionContext.put(token,
                            configuration.getValues().variables.get(token));
                else
                    // 7. We generate a value for the token and put it in
                    // the
                    // context
                    ExecutionContext.put(token, RandomStringUtils.randomAlphabetic(11)
                            .toLowerCase());
            // 8. Then, we'll inject the value from the context directly in the
            // result
            result = result.replace(TOKEN_START + token + TOKEN_END,
                    ExecutionContext.get(token));
        }
        // 7. Finally we return the result, which is the string with data
        // injected in it
        return result;
        // 8. BTW, all the tokens can be found from the context, with the random
        // values associated
    }

}
