package com.worldline.easycukes.arquillian;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

import com.worldline.easycukes.arquillian.example.controller.BellyController;
import com.worldline.easycukes.arquillian.example.domain.Belly;
import com.worldline.easycukes.arquillian.example.producer.FacesContextProducer;
import com.worldline.easycukes.selenium.stepdefs.CommonStepdefs;
import com.worldline.easycukes.selenium.stepdefs.ConfigurationStepdefs;

import cucumber.api.CucumberOptions;
import cucumber.runtime.arquillian.CukeSpace;
import cucumber.runtime.arquillian.api.Glues;

@RunWith(CukeSpace.class)
@Glues({ ConfigurationStepdefs.class, CommonStepdefs.class })
@CucumberOptions(features = { "classpath:features/" }, format = { "json",
		"html:target/cucumber-report/html", "json:target/cucumber-report.json" }, tags = { "~@wip" })
public class RunCukesTest {

	@Deployment(testable = false)
	public static Archive<?> createDeployment() {
		return create(WebArchive.class, "belly.war")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource(
						new StringAsset("<faces-config version=\"2.0\"/>"),
						"faces-config.xml")
				.addAsWebResource(new File("src/main/webapp/belly.xhtml"),
						"belly.xhtml").addClass(Belly.class)
				.addClass(BellyController.class)
				.addClass(FacesContextProducer.class);
	}

}
