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
package com.worldline.easycukes.arquillian;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.runner.RunWith;

import com.worldline.easycukes.example.data.Person;
import com.worldline.easycukes.example.persistence.PersistenceManager;
import com.worldline.easycukes.example.rest.PersonService;

import cucumber.api.CucumberOptions;
import cucumber.runtime.arquillian.CukeSpace;

/**
 * This {@link RunCukesTest} class simply allows to specify all the options to
 * set for Cucumber in order to run it with JUnit.
 * 
 * @author mechikhi
 * @version 1.0
 */
@RunWith(CukeSpace.class)
@CucumberOptions(features = { "classpath:features/" }, format = { "json",
		"html:target/cucumber-report/html", "json:target/cucumber-report.json" }, tags = { "~@wip" }, glue = { "com.worldline.easycukes" })
public class RunCukesTest {

	@Deployment(testable = false)
	public static Archive<?> createDeployment() {
		WebArchive war = ShrinkWrap.create(WebArchive.class, "cukes.war")
				.setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
				.addClass(PersonService.class).addClass(Person.class)
				.addClass(PersistenceManager.class);

		JavaArchive[] libs = Maven.resolver()
				.resolve("com.sun.jersey:jersey-servlet:1.18.1")
				.withTransitivity().as(JavaArchive.class);

		war.addAsLibraries(libs);

		libs = Maven.resolver().resolve("com.sun.jersey:jersey-json:1.18.1")
				.withTransitivity().as(JavaArchive.class);

		war.addAsLibraries(libs);

		libs = Maven.resolver().resolve("org.hsqldb:hsqldb:2.3.2")
				.withTransitivity().as(JavaArchive.class);

		war.addAsLibraries(libs);
		return war;
	}

}
