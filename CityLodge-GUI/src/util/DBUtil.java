package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	private DBUtil() {
		super();
	}

	/*
	 * This static method returns the connection object for a database operation
	 */
	public static Connection getDBConnection() {
		Connection connection = null;

		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:file:database/testDB;hsqldb.lock_file=false", "SA",
					"");

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}

		return connection;
	}
}
