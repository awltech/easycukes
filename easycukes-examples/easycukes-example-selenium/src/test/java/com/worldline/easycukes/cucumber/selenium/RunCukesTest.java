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
