package it.unicam.ids.lacus.database;

import it.unicam.ids.lacus.view.Alerts;
import java.sql.*;
import java.sql.ResultSet;

public class DatabaseConnection {

	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static final String CONN = "jdbc:mysql://localhost/lacus?" + "useUnicode=true&useJDBCCompliantTimezoneShift=true" + "&useLegacyDatetimeCode=false&serverTimezone=UTC";
	protected static Connection conn = null;
	protected Statement stmt;
	protected ResultSet rs = null;

	protected void getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
		} catch (Exception e) {
			Alerts alert = new Alerts();
			alert.printDatabaseConnectionError();
		}
	}

	protected ResultSet createStatementAndRSForQuery(String sql) {
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

		} catch (SQLException e) {
			Alerts alert = new Alerts();
			alert.printDatabaseConnectionError();
		}
		return rs;
	}

	protected void createStatementForUpdate(String sql) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			Alerts alert = new Alerts();
			alert.printDatabaseConnectionError();
		}
	}

	protected void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				Alerts alert = new Alerts();
				alert.printDatabaseConnectionError();
			}
		}
	}

	protected void closeStatement() {
		try {
			if(stmt!=null)stmt.close();
		} catch (SQLException e) {
			Alerts alert = new Alerts();
			alert.printDatabaseConnectionError();
		}
	}

	protected void closeResultSet() {
		try {
			if(rs!=null) rs.close();
		} catch (SQLException e) {
			Alerts alert = new Alerts();
			alert.printDatabaseConnectionError();
		}
	}
}
