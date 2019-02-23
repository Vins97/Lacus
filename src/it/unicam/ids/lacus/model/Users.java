package it.unicam.ids.lacus.model;

import it.unicam.ids.lacus.database.DatabaseConnection;
import it.unicam.ids.lacus.database.DatabaseOperation;

import it.unicam.ids.lacus.view.Alerts;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Users extends DatabaseConnection {

	private static int userid = -1;

	public int getUserid() {
		return Users.userid;
	}

	public void setUserid(int userid) {
		Users.userid = userid;
	}

	//Metodo che modifica i dati dell'utente quando li inserisce dalla schermata di modifica del profilo
	public void updateUser(String sql) {
		try {
			getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			closeStatement();
			closeConnection();

		} catch (SQLException e) {
			Alerts alert = new Alerts();
			alert.printDatabaseConnectionError();
		}
	}

	public boolean checkText(String firstname, String surname, String id, String psw, String email, String cf, String city, String street, String street_number, boolean empty_allowed) {
		//Raggruppa le stringhe per tipo (quelle che devono contenere solo lettere e quelle che possono contenere lettere e numeri)
		String[] characters = {firstname, surname, city, street};
		String[] charandnumb = {id, psw, cf};
		StringChecker check = new StringChecker();
		Alerts alert = new Alerts();
		//Controlla le stringhe che devono contenere solo lettere o spazi
		for(int i=0; i<4; i++) {
			switch(check.characterAndSpacesChecker(characters[i])) {
				case 0: {
					if(!empty_allowed) {
						alert.printEmptyFieldsMessage();
						return false;
					}
					break;
				}
				case -1: {
					alert.printInvalidCharactersMessage();
					return false;
				}
			}
		}
		//Controlla le stringhe che devono contenere solo lettere o numeri
		for(int i=0; i<3; i++) {
			switch(check.characterAndNumberChecker(charandnumb[i])) {
				case 0: {
					if(!empty_allowed) {
						alert.printEmptyFieldsMessage();
						return false;
					}
					break;
				}
				case -1: {
					alert.printInvalidCharactersMessage();
					return false;
				}
			}
		}
		//Controlla che la città sia corretta
		if(!check.cityChecker(city)) {
			alert.printUnknownCityMessage();
			return false;
		}
		//Controlla la correttezza dell'email
		switch(check.emailChecker(email)) {
			case 0: {
				if(!empty_allowed) {
					alert.printEmptyFieldsMessage();
					return false;
				}
				break;
			}
			case -1: {
				alert.printInvalidCharactersMessage();
				return false;
			}
			case -2: {
				alert.printInvalidEmailDomainMessage();
				return false;
			}
		}
		//Controlla la correttezza del numero della via
		switch(check.numberOnlyChecker(street_number)) {
			case 0: {
				if(!empty_allowed) {
					alert.printEmptyFieldsMessage();
					return false;
				}
				break;
			}
			case -1: {
				alert.printInvalidCharactersMessage();
				return false;
			}
		}
		return true;
	}

	public boolean registerUser(String firstname, String surname, String id, String psw, String conf_psw, String email, String cf, String city, String street, String street_number) {
		Alerts alert = new Alerts();
		if(checkText(firstname, surname, id, psw, email, cf, city , street, street_number, false)) {
			if(!verifyPasswordMatch(psw, conf_psw)) {
				alert.printNoPasswordMatchMessage();
			}
			else if(addUser(firstname, surname, id, psw, email, cf, city, street, street_number)) {
				alert.printRegisteredUserMessage();
				return true;
			}
			else {
				alert.printUsernameOrCFTakenMessage();
			}
		}
		return false;
	}

	private boolean addUser(String firstname, String surname, String id, String psw, String email, String cf, String city, String street, String street_number) {
		//BUILDER PATTERN TO IMPLEMENT
		String sql = "INSERT INTO users (firstname, surname, id, psw, email, cf, city, street, street_number) VALUES " + "('"
				+ Hash.getMd5(firstname) + "','" + Hash.getMd5(surname) + "','" + Hash.getMd5(id) + "','" + Hash.getMd5(psw)
				+ "','" + Hash.getMd5(email) + "','" + cf + "','" + city + "','" + street + "','" + street_number + "');";
		//Controllo che il codice fiscale e la coppia id/password non siano già registrati
		if (verifyNewCF(cf) && !searchUser(Hash.getMd5(id), Hash.getMd5(psw))) {
			try {
				getConnection();
				stmt = conn.createStatement();
				stmt.executeUpdate(sql);
				closeStatement();
				closeConnection();

			} catch (SQLException e) {
				Alerts alert = new Alerts();
				alert.printDatabaseConnectionError();
			}
			return true;
		}
		return false;
	}

	public boolean searchUser(String hashedid, String hashedpsw) {
		String sql = "SELECT id, psw FROM users WHERE id = '" + hashedid + "' AND psw = '" + hashedpsw + "';";
		boolean responce = false;
		ResultSet rs;
		DatabaseOperation dbop = new DatabaseOperation();
		try {
			getConnection();
			rs = createStatementAndRSForQuery(sql);
			rs.first();
			responce = dbop.resultSetRows(rs) == 1;
			closeResultSet();
			closeStatement();
			closeConnection();
		} catch (SQLException e) {
			Alerts alert = new Alerts();
			alert.printDatabaseConnectionError();
		}
		return responce;
	}

	//Controlla che il codice fiscale non sia mai stato inserito: restituisce true se è nuovo o false se è già in uso
	public boolean verifyNewCF(String cf) {
		String sql = "SELECT cf FROM users WHERE cf = '" + cf + "';";
		boolean response = true;
		try {
			getConnection();
			rs = createStatementAndRSForQuery(sql);
			rs.first();
			int firstPosition = rs.getRow();
			rs.last();
			int lastPosition = rs.getRow();
			if(firstPosition == 1 && lastPosition == 1) {
				response = false;
			}
			// Mi devo posizionare sulla prima riga e devo analizzare l'ID del primo record
			closeResultSet();
			closeStatement();
			closeConnection();
		}
		catch (SQLException e) {
			Alerts alert = new Alerts();
			alert.printDatabaseConnectionError();
		}
		return response;

	}

	//Verifica che due password siano uguali (ai fini della registrazione o del cambio password)
	public boolean verifyPasswordMatch(String psw, String conf_psw) {
		return psw.compareTo(conf_psw) == 0;
	}

	//Restituisce il codice dell'utente a partire dall'id e dalla password hashati
	int getUseridFromDatabase(String hashedid, String hashedpsw) {
		String sql = "SELECT userid FROM users WHERE(id='" + hashedid + "'AND psw='" + hashedpsw + "');";
		int codUtente;
		try {
			getConnection();
			rs = createStatementAndRSForQuery(sql);
			rs.first();
			codUtente = rs.getInt("userid");
			closeResultSet();
			closeStatement();
			closeConnection();
		} catch (SQLException e) {
			//Indica che il codice utente non esiste
			codUtente = -1;
		}
		return codUtente;
	}

	public String getIdFromDatabase(int userid) {
		String sql = "SELECT id FROM users WHERE userid='" + userid + "';";
		String id;
		try {
			getConnection();
			rs = createStatementAndRSForQuery(sql);
			rs.first();
			id = rs.getString("id");
			closeResultSet();
			closeStatement();
			closeConnection();
		} catch (SQLException e) {
			//Indica che il codice utente non esiste
			id = null;
		}
		return id;
	}

	public String getPswFromDatabase(int userid) {
		String sql = "SELECT psw FROM users WHERE userid='" + userid + "';";
		String psw;
		try {
			getConnection();
			rs = createStatementAndRSForQuery(sql);
			rs.first();
			psw = rs.getString("psw");
			closeResultSet();
			closeStatement();
			closeConnection();
		} catch (SQLException e) {
			//Indica che il codice utente non esiste
			psw = null;
		}
		return psw;
	}
}
