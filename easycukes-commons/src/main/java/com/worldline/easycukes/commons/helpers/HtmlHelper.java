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

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * {@link HtmlHelper} allows to perform some operations in relation to the
 * manipulation of Html content. It use the API Jsoup for extracting data.
 *
 * @author mechikhi
 * @version 1.0
 */
@Slf4j
@UtilityClass
public class HtmlHelper {

    /**
     * Extract the input value from an html content.
     *
     * @param input       the name of the input in the html form
     * @param htmlContent the html content
     * @return The value, if it could be extracted, null otherwise.
     */
    public static String extractInputValueFromHtmlContent(@NonNull final String input,
                                                          @NonNull final String htmlContent) {
        Document document = Jsoup.parse(htmlContent);

        Elements elements = document.select("input[name=" + input + "]");

        if (elements == null || elements.isEmpty()) {
            log.warn(input + " input not found in response.");
            return null;
        }
        Element element = elements.iterator().next();
        return element.val();
    }
}
