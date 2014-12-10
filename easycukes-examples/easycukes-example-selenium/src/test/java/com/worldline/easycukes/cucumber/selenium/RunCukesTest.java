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
package com.worldline.easycukes.cucumber.selenium;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * This {@link RunCukesTest} class simply allows to specify all the options to
 * set for Cucumber in order to run it with JUnit.
 *
 * @author aneveux
 * @version 1.0
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = { "classpath:features/" }, format = { "json",
		"html:target/cucumber-html-report",
"json:target/cucumber-json-report.json" }, tags = { "~@wip" }, glue = { "com.worldline.easycukes" })
public class RunCukesTest {
}
