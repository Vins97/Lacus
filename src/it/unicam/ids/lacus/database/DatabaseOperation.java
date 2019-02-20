package it.unicam.ids.lacus.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseOperation extends DatabaseConnection {

	public boolean searchElement(String sql) {
		boolean responce = false;
		int resultSetRows;
		getConnection();
		rs = createStatementAndRSForQuery(sql);
		if(resultSetRows(rs) == 1) {
			resultSetRows = 1;
			closeResultSet();
			closeStatement();
			closeConnection();
		}
		else {
			resultSetRows = 0;
		}
		if(resultSetRows == 1) {
			responce = true;
		}
		return responce;
	}

	// ritorna il numero di righe di una query
	public int resultSetRows(ResultSet rs) {
		int rsRows;
		try {
			rs.first();
			int firstRow = rs.getRow();
			rs.last();
			int lastRow = rs.getRow();
			// ovvero se abbiamo una sola riga
			if (lastRow == 1 && firstRow == 1) {
				rsRows = 1;
				// se non abbiamo righe
			} else if (lastRow == 0 && firstRow == 0)
				rsRows = 0;
				// se abbiamo più di una riga
			else
				rsRows = lastRow;
		} catch (SQLException e) {
			e.getMessage();
			rsRows = -1;
		}
		return rsRows;
	}
}

