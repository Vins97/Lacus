package sample.DataBase;

import java.sql.*;
import java.sql.ResultSet;

public class ConnectionDataBase {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String CONN = "jdbc:mysql://localhost/lacus?"
            + "useUnicode=true&useJDBCCompliantTimezoneShift=true" + "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    static Connection conn = null;
    Statement stmt;
    ResultSet rs = null;

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }




    public ResultSet createStatementAndRSForQuery(String sql) {
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

        } catch (SQLException e) {
            System.err.println(e);
        }
        return rs;
    }

    public void createStatementForUpdate(String sql) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.err.println(e);
        }
    }
    public static void closeConnection() {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        else
            System.out.println("La connessione non è aperta");
    }

    public void closeStatement() {
        try {
            if(stmt!=null)stmt.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public void closeResultSet() {
        try {
            if(rs!=null) rs.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }



}
