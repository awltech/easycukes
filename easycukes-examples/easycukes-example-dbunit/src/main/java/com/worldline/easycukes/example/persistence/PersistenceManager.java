package com.worldline.easycukes.example.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.worldline.easycukes.example.data.Person;

public class PersistenceManager {

	private Connection conn;

	public PersistenceManager() throws Exception {

		// Load the HSQL Database Engine JDBC driver
		Class.forName("org.hsqldb.jdbcDriver");

		conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb",
				"sa", "");

	}

	public void shutdown() throws SQLException {

		Statement st = conn.createStatement();

		st.execute("SHUTDOWN");
		conn.close();
	}

	// use for SQL command SELECT
	public synchronized List<Person> query(String expression)
			throws SQLException {

		Statement st = null;
		ResultSet rs = null;

		st = conn.createStatement(); // statement objects can be reused with

		// repeated calls to execute but we
		// choose to make a new one each time
		rs = st.executeQuery(expression); // run the query

		List<Person> result = buildResult(rs);
		st.close();
		return result;
	}

	private List<Person> buildResult(ResultSet rs) throws SQLException {

		List<Person> result = new ArrayList<Person>();

		for (; rs.next();) {
			result.add(new Person(rs.getString(1), rs.getString(2), rs
					.getString(3), rs.getString(4)));
		}
		return result;
	}

	/**
	 * Use for SQL commands CREATE, DROP, INSERT and UPDATE
	 * 
	 * @param expression
	 * @throws SQLException
	 */
	public synchronized void update(String expression) throws SQLException {

		Statement st = null;

		st = conn.createStatement(); // statements

		int i = st.executeUpdate(expression); // run the query

		if (i == -1) {
			System.out.println("db error : " + expression);
		}

		st.close();
	}

}
