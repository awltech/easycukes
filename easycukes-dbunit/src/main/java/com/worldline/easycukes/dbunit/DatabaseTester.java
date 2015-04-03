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

import lombok.NonNull;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.dbunit.ext.db2.Db2DataTypeFactory;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.oracle.OracleDataTypeFactory;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;

/**
 * DatabaseTester that uses JDBC's Driver Manager to create connections.<br>
 * This class defines a factory for creating {@link DataType} that will be used,
 * depending on the target database.
 *
 * @author mechikhi
 * @version 1.0
 */

public class DatabaseTester extends JdbcDatabaseTester {

    private IDataTypeFactory dataTypeFactory;

    /**
     * Creates a new DatabaseTester with the specified properties.
     *
     * @param driverClass   the classname of the JDBC driver to use
     * @param connectionUrl the connection url
     * @param username      a username that can has access to the database
     * @param password      the user's password
     * @throws ClassNotFoundException If the given driverClass was not found
     */
    public DatabaseTester(@NonNull String driverClass, @NonNull String connectionUrl,
                          String username, String password) throws ClassNotFoundException {
        super(driverClass, connectionUrl, username, password);
        if (driverClass.contains("hsqldb"))
            dataTypeFactory = new HsqldbDataTypeFactory();
        else if (driverClass.contains("oracle"))
            dataTypeFactory = new OracleDataTypeFactory();
        else if (driverClass.contains("db2"))
            dataTypeFactory = new Db2DataTypeFactory();
        if (driverClass.contains("mysql"))
            dataTypeFactory = new MySqlDataTypeFactory();
        if (driverClass.contains("postgresql"))
            dataTypeFactory = new PostgresqlDataTypeFactory();
    }

    @Override
    /**
     * Returns the test database connection.
     */
    public IDatabaseConnection getConnection() throws Exception {
        IDatabaseConnection databaseConnection = super.getConnection();

        if (dataTypeFactory != null)
            databaseConnection.getConfig().setProperty(
                    DatabaseConfig.PROPERTY_DATATYPE_FACTORY, dataTypeFactory);
        return databaseConnection;
    }

}
