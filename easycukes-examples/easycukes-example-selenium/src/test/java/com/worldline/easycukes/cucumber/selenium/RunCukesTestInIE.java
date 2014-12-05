package com.worldline.easycukes.cucumber.selenium;

import org.apache.log4j.MDC;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.worldline.easycukes.commons.context.ExecutionContext;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

/**
 * This {@link RunCukesTestInIE} class simply allows to specify all the options
 * to set for Cucumber in order to run it with TestNG.
 * 
 * @author aneveux
 * @version 1.0
 */
@CucumberOptions(features = "classpath:features/", format = { "json",
		"html:target/cucumber-html-report/ie",
		"json:target/ie/cucumber-json-report.json",
		"junit:target/ie/cucumber-xml-report.xml" }, tags = { "~@wip" }, glue = { "com.worldline.easycukes" })
public class RunCukesTestInIE extends AbstractTestNGCucumberTests {

	@BeforeTest
	@Parameters(value = { "browserName" })
	public void init(String browserName) throws Exception {
		ExecutionContext.put("browserName", browserName);
		MDC.put("testid", browserName);
	}

}
