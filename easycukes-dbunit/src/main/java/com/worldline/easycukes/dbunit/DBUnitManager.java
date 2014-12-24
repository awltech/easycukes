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
package com.worldline.easycukes.dbunit;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;

/**
 * This class aims at containing the DBUnit operations can be used in the tests
 * scenarios.
 * 
 * @author mechikhi
 * @version 1.0
 */
public class DBUnitManager {

	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = LogManager
			.getLogger(DBUnitManager.class);

	private IDatabaseTester databaseTester;

	private String dataSetString;

	private List<IDataSet> dataSets = new ArrayList<IDataSet>();

	/**
	 * Singleton instance
	 */
	private static DBUnitManager _instance;

	/**
	 * Allows to get the singleton instance of {@link DBUnitManager}
	 * 
	 * @return the singleton instance ({@link #_instance}) of
	 *         {@link DBUnitManager}
	 */
	public static final DBUnitManager getInstance() {
		if (_instance == null)
			_instance = new DBUnitManager();
		return _instance;
	}

	/**
	 * Gets the IDatabaseTester for the test scenario.<br>
	 * If the IDatabaseTester is not set yet, this method calls
	 * newDatabaseTester() to obtain a new instance.
	 * 
	 * @return a {@link JdbcDatabaseTester}
	 * @throws Exception
	 */
	private IDatabaseTester getDatabaseTester() throws Exception {
		if (databaseTester == null) {
			databaseTester = newDatabaseTester();
		}
		return databaseTester;
	}

	/**
	 * Creates a new IDatabaseTester.<br>
	 * 
	 * @return a {@link JdbcDatabaseTester} configured with the values driver
	 *         class, connection Url, Username and Password
	 * @throws ClassNotFoundException
	 *             when the driverClass was not found
	 */
	private IDatabaseTester newDatabaseTester() throws ClassNotFoundException {
		LOGGER.debug("newDatabaseTester() - start");
		Map<String, Object> dbunitProps = Configuration.getTargetDBUnit();
		return new JdbcDatabaseTester(
				dbunitProps.get("driverclass").toString(), dbunitProps.get(
						"connectionurl").toString(), dbunitProps
						.get("username").toString(),
				dbunitProps.get("password") != null ? dbunitProps.get(
						"password").toString() : "");
	}

	/**
	 * adds to a dataSet content
	 * 
	 * @param data
	 */
	public void addToDataSet(String data) {
		dataSetString = (dataSetString != null ? dataSetString + data : data);
	}

	/**
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public void addFileToDataSet(String fileName) throws Exception {
		this.dataSets.add(new FlatXmlDataSetBuilder()
				.build(new FileInputStream(fileName)));
	}

	/**
	 * Sets the database operation to execute in test setup.
	 * 
	 * @param operation
	 * @throws Exception
	 */
	public void setSetUpOperation(String operation) throws Exception {
		getDatabaseTester().setSetUpOperation(getDBOperation(operation));
	}

	/**
	 * Sets the database operation to execute in test cleanup.
	 * 
	 * @param operation
	 * @throws Exception
	 */
	public void setTearDownOperation(String operation) throws Exception {
		getDatabaseTester().setTearDownOperation(getDBOperation(operation));
	}

	/**
	 * Cleanup operation to call when ending the test secnario
	 * 
	 * @throws Exception
	 */
	public void tearDown() throws Exception {
		LOGGER.debug("tearDown() - start");

		try {
			final IDatabaseTester databaseTester = getDatabaseTester();
			Assert.assertNotNull("DatabaseTester is not set", databaseTester);
			for (IDataSet dataSet : this.dataSets) {
				databaseTester.setDataSet(dataSet);
				databaseTester.onTearDown();
			}
		} finally {
			databaseTester = null;
			dataSetString = null;
			dataSets = new ArrayList<IDataSet>();
		}

	}

	/**
	 * Setup operation to call before starting the test secnario
	 * 
	 * @throws Exception
	 */
	public void setUp() throws Exception {

		LOGGER.debug("setUp() - start");
		final IDatabaseTester databaseTester = getDatabaseTester();
		Assert.assertNotNull("DatabaseTester is not set", databaseTester);
		if (dataSetString != null)
			dataSets.add(buildXmlDataSet());
		for (IDataSet dataSet : this.dataSets) {
			databaseTester.setDataSet(dataSet);
			databaseTester.onSetup();
		}
	}

	/**
	 * Returns the test database connection
	 * 
	 * @return the test database connection
	 * @throws Exception
	 */
	public final IDatabaseConnection getConnection() throws Exception {
		LOGGER.debug("getConnection() - start");

		final IDatabaseTester databaseTester = getDatabaseTester();
		Assert.assertNotNull("DatabaseTester is not set", databaseTester);
		IDatabaseConnection connection = databaseTester.getConnection();
		return connection;
	}

	/**
	 * Gets the Database operation
	 * 
	 * @param operation
	 * @return
	 * @throws Exception
	 */
	private static DatabaseOperation getDBOperation(String operation)
			throws Exception {

		switch (operation) {
		case "INSERT":
			return DatabaseOperation.INSERT;
		case "CLEAN_INSERT":
			return DatabaseOperation.CLEAN_INSERT;
		case "UPDATE":
			return DatabaseOperation.UPDATE;
		case "REFRESH":
			return DatabaseOperation.REFRESH;
		case "DELETE":
			return DatabaseOperation.DELETE;
		case "DELETE_ALL":
			return DatabaseOperation.DELETE_ALL;
		case "TRUNCATE_TABLE":
			return DatabaseOperation.TRUNCATE_TABLE;
		case "NONE":
			return DatabaseOperation.NONE;
		default:
			LOGGER.error("Unknown DB operation : " + operation);
			throw new Exception("Unknown DB operation : " + operation);
		}
	}

	/**
	 * Builds a dataSet from the string content
	 * 
	 * @return dataSet
	 * @throws Exception
	 */
	private IDataSet buildXmlDataSet() throws Exception {
		StringBuffer xmlBuffer = new StringBuffer();
		xmlBuffer.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
		xmlBuffer.append("<dataset>\n");
		xmlBuffer.append(dataSetString);
		xmlBuffer.append("</dataset>");
		return new FlatXmlDataSetBuilder().build(new ByteArrayInputStream(
				xmlBuffer.toString().getBytes("UTF-8")));
	}
}
