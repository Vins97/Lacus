package it.unicam.ids.lacus.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.unicam.ids.lacus.controller.StringChecker;

public class DataBaseOperation extends ConnectionDataBase {
	public boolean searchEmailDomain(String emailDomain) {
		String sql = "SELECT * FROM email WHERE email='" + emailDomain + "';";
		boolean responce = false;
		int resultSetRows = -1;
		try {
			getConnection();
			rs = createStatementAndRSForQuery(sql);
			if (resultSetRows(rs) == 1) {
				resultSetRows = 1;
				closeResultSet();
				closeStatement();
				closeConnection();
			} else
				resultSetRows = 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block e.printStackTrace();
			System.err.println(e.getMessage());
		}
		if (resultSetRows == 1)
			responce = true;
		else if (resultSetRows == 0 || resultSetRows == -1)
			responce = false;
		return responce;

	}

	// ritorna il numero di righe di una query
	public static int resultSetRows(ResultSet rs) {
		int rsRows = -1;
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
		ResultSet citiesList = null;
		String sql = "SELECT city FROM cities where CAP='" + CAP + "%';";
		try {
			getConnection();
			citiesList = createStatementAndRSForQuery(sql);

		} catch (SQLException e) {
			e.getMessage();
		}
		return citiesList;
	}

	/*public String[] getCitiesFromCAP(String CAP) throws SQLException {
		String[] citiesListArray = new String[50];

		// controllo che il CAP sia di 5 cifre e che contenga solo numeri
		if (CAP.length() == 5 && StringChecker.numberStringChecker(CAP) == true) {

			DataBaseOperation operationForCAP = new DataBaseOperation();

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

