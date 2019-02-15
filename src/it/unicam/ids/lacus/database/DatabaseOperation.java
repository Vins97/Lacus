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
		else if(resultSetRows == 0 || resultSetRows == -1) {
			responce = false;
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

	// metodo che restituisce tutte le città corrispondenti ad un CAP
	private ResultSet getCitiesResultSet(String CAP) {
		ResultSet citiesList;
		String sql = "SELECT city FROM cities where CAP='" + CAP + "%';";
		getConnection();
		citiesList = createStatementAndRSForQuery(sql);
		return citiesList;
	}

	/*public String[] getCitiesFromCAP(String CAP) throws SQLException {
		String[] citiesListArray = new String[50];

		// controllo che il CAP sia di 5 cifre e che contenga solo numeri
		if (CAP.length() == 5 && StringChecker.numberStringChecker(CAP) == true) {

			DatabaseOperation operationForCAP = new DatabaseOperation();

			// controllo che ci sia almeno una città con quel CAP
			if (operationForCAP.resultSetRows(operationForCAP.getCitiesResultSet(CAP)) >= 1) {

				ResultSet resultSetOfCitiesList = operationForCAP.getCitiesResultSet(CAP);
				for (int arrayIndex = 0; resultSetOfCitiesList.next(); arrayIndex++) {
					citiesListArray[arrayIndex] = resultSetOfCitiesList.getString("city");
					System.out.println(citiesListArray[arrayIndex]);

				}

			} else
				System.out.println("IL CAP NON ESISTE");

		} else
			System.out.println("IL FORMATO NON E' CORRETTO");
		return citiesListArray;
	}*/

}

