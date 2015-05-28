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
package com.worldline.easycukes.dbunit.stepdefs;

import com.worldline.easycukes.dbunit.DBUnitManager;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * DBUnitStepdefs are all the step definitions you can use in your Cucumber
 * features in order to interact with DBUnit. It allows you to manipulate a
 * database you may use for running tests in order to set up some data in it, or
 * to put it in a particular state.
 *
 * @author mechikhi
 * @version 1.0
 */
@Slf4j
public class DBUnitStepdefs {

    @Before
    public void before(Scenario scenario) throws Exception {
        if (scenario.getSourceTagNames() == null
                || !scenario.getSourceTagNames().contains("@dbunit"))
            DBUnitManager.getInstance().setUp();
    }

    @After
    public void after(Scenario scenario) throws Exception {
        if (scenario.getSourceTagNames() == null
                || !scenario.getSourceTagNames().contains("@dbunit"))
            DBUnitManager.getInstance().tearDown();
    }

    /**
     * Allows to pass the data to be inserted in the specified table before
     * starting the scenarios
     *
     * @param table
     * @param data
     * @throws Throwable
     */
    @Given("^fill the table \"(.*?)\" with data:$")
    public void fillATableWithData(String table, List<Map<String, String>> data)
            throws Throwable {
        final StringBuffer xmlBuffer = new StringBuffer();
        for (final Map<String, String> row : data) {
            xmlBuffer.append("\t<").append(table);
            for (final String key : row.keySet())
                xmlBuffer.append(" " + key + "=\"").append(row.get(key))
                        .append("\"");
            xmlBuffer.append(" />\n");
        }
        DBUnitManager.getInstance().addToDataSet(xmlBuffer.toString());
    }

    /**
     * Allows to add the file content to the dataset to be used used during the
     * DBUnit operations
     *
     * @param fileName
     * @throws Throwable
     */
    @Given("^add dataset file \"(.*?)\"$")
    public void defineDatasetFile(String fileName) throws Throwable {
        DBUnitManager.getInstance().addFileToDataSet(fileName);
    }

    /**
     * Allows to specify the setup operation to be performed before the test
     * scenario
     *
     * @param operation
     * @throws Throwable
     */
    @Given("^the operation before executing tests is (NONE|UPDATE|INSERT|REFRESH|DELETE|DELETE_ALL|TRUNCATE_TABLE|CLEAN_INSERT)$")
    public void defineOperationToBeExecutedBeforeTheTests(String operation)
            throws Throwable {
        DBUnitManager.getInstance().setSetUpOperation(operation);
    }

    /**
     * Allows to specify the cleanup operation to be performed when ending the
     * test scenario
     *
     * @param operation
     * @throws Throwable
     */
    @Given("^the operation after executing tests is (NONE|UPDATE|INSERT|REFRESH|DELETE|DELETE_ALL|TRUNCATE_TABLE|CLEAN_INSERT)$")
    public void defineOperationToBeExecutedAfterTheTests(String operation)
            throws Throwable {
        DBUnitManager.getInstance().setTearDownOperation(operation);
    }

}
