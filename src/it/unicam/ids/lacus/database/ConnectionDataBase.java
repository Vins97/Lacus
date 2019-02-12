package it.unicam.ids.lacus.database;

import javafx.scene.control.Alert;
import java.sql.*;
import java.sql.ResultSet;

public class ConnectionDataBase {

	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static final String CONN = "jdbc:mysql://localhost/lacus?" + "useUnicode=true&useJDBCCompliantTimezoneShift=true" + "&useLegacyDatetimeCode=false&serverTimezone=UTC";
	public static Connection conn = null;
	public Statement stmt;
	public ResultSet rs = null;

	public Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
			databaseConnectionError();
		}
		return conn;
	}

	public ResultSet createStatementAndRSForQuery(String sql) {
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

		} catch (SQLException e) {
			System.err.println(e);
			databaseConnectionError();
		}
		return rs;
	}

	public void createStatementForUpdate(String sql) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			System.err.println(e);
			databaseConnectionError();
		}
	}

	public void closeConnection() {
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				databaseConnectionError();
			}
		else
			System.out.println("La connessione non è aperta");
	}

	public void closeStatement() {
		try {
			if(stmt!=null)stmt.close();
		} catch (SQLException e) {
			System.err.println(e);
			databaseConnectionError();
		}
	}

	public void closeResultSet() {
		try {
			if(rs!=null) rs.close();
		} catch (SQLException e) {
			System.err.println(e);
			databaseConnectionError();
		}
	}

	public void databaseConnectionError() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Errore di connessione al Database");
		alert.setHeaderText(null);
		alert.setContentText("La tua operazione non è andata a buon fine a causa di un problema di connessione al Database!");
		alert.showAndWait();
	}
}
