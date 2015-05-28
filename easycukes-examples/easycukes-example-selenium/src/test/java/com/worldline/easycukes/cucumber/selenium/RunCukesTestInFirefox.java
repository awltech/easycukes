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

import org.slf4j.MDC;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.worldline.easycukes.commons.ExecutionContext;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

/**
 * This {@link RunCukesTestInFirefox} class simply allows to specify all the
 * options to set for Cucumber in order to run it with TestNG.
 * 
 * @author aneveux
 * @version 1.0
 */
@CucumberOptions(features = "classpath:features/", format = { "json",
		"html:target/cucumber-html-report/firefox",
		"json:target/firefox/cucumber-json-report.json",
		"junit:target/firefox/cucumber-xml-report.xml" }, tags = { "~@wip" }, glue = { "com.worldline.easycukes" })
public class RunCukesTestInFirefox extends AbstractTestNGCucumberTests {

	@BeforeTest
	@Parameters(value = { "browserName" })
	public void init(String browserName) throws Exception {
		ExecutionContext.put("browserName", browserName);
		MDC.put("testid", browserName);
	}

}
