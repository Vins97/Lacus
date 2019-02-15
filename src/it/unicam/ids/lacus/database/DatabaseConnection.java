package it.unicam.ids.lacus.database;

import it.unicam.ids.lacus.view.Alerts;
import java.sql.*;
import java.sql.ResultSet;

public class DatabaseConnection {

	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static final String CONN = "jdbc:mysql://localhost/lacus?" + "useUnicode=true&useJDBCCompliantTimezoneShift=true" + "&useLegacyDatetimeCode=false&serverTimezone=UTC";
	public static Alerts alert = new Alerts();
	public static Connection conn = null;
	public Statement stmt;
	public ResultSet rs = null;

	public Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
			alert.databaseConnectionError();
		}
		return conn;
	}

	public ResultSet createStatementAndRSForQuery(String sql) {
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

		} catch (SQLException e) {
			System.err.println(e);
			alert.databaseConnectionError();
		}
		return rs;
	}

	public void createStatementForUpdate(String sql) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			System.err.println(e);
			alert.databaseConnectionError();
		}
	}

	public void closeConnection() {
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				alert.databaseConnectionError();
			}
		else
			System.out.println("La connessione non è aperta");
	}

	public void closeStatement() {
		try {
			if(stmt!=null)stmt.close();
		} catch (SQLException e) {
			System.err.println(e);
			alert.databaseConnectionError();
		}
	}

	public void closeResultSet() {
		try {
			if(rs!=null) rs.close();
		} catch (SQLException e) {
			System.err.println(e);
			alert.databaseConnectionError();
		}
	}
}
