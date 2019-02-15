package it.unicam.ids.lacus.model;

import it.unicam.ids.lacus.database.DatabaseConnection;
import it.unicam.ids.lacus.database.DatabaseOperation;

import it.unicam.ids.lacus.view.Alerts;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Users extends DatabaseConnection {

	private static String id = null;
	private static String psw = null;
	private static String name, surname;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPsw() {
		return this.psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}

	public boolean addUser(String firstname, String surname, String id, String psw, String email, String cf, String city, String street, String street_number) {
		String sql = "INSERT INTO users (firstname, surname, id, psw, email, cf, city, street, street_number) VALUES " + "('"
				+ Hash.getMd5(firstname) + "','" + Hash.getMd5(surname) + "','" + Hash.getMd5(id) + "','" + Hash.getMd5(psw)
				+ "','" + Hash.getMd5(email) + "','" + cf + "','" + city + "','" + street + "','" + street_number + "');";
		//Controllo che il codice fiscale e la coppia id/password non siano già registrati
		if (!verifyCFExistency(cf) && !searchUser(Hash.getMd5(id), Hash.getMd5(psw))) {
			try {
				getConnection();
				stmt = conn.createStatement();
				stmt.executeUpdate(sql);
				closeStatement();
				closeConnection();

			} catch (SQLException e) {
				alert.databaseConnectionError();
			}
			return true;
		}
		return false;
	}

	//Metodi che modificano i dati degli utenti quando essi li inseriscono dalla schermata del profilo
	public boolean updateUser(String sql) {
		try {
			getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			closeStatement();
			closeConnection();

		} catch (SQLException e) {
			return false;
		}
		return true;
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

	public boolean searchUser(String hashedid, String hashedpsw) {
		String sql = "SELECT id, psw FROM users WHERE id = '" + hashedid + "' AND psw = '" + hashedpsw + "';";
		boolean responce = false;
		ResultSet rs;
		DatabaseOperation dbop = new DatabaseOperation();
		try {
			getConnection();
			rs = createStatementAndRSForQuery(sql);
			rs.first();
			if(dbop.resultSetRows(rs)==1)
				responce = true;
			else responce =  false;
			closeResultSet();
			closeStatement();
			closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return responce;
	}

	public void setActiveUser(String id, String psw) {
		if(id == null || psw == null) {
			setId(null);
			setPsw(null);
		}
		else {
			setId(Hash.getMd5(id));
			setPsw(Hash.getMd5(psw));
		}
	}

	public boolean verifyCFExistency(String cf) {
		String sql = "SELECT cf FROM users WHERE cf = '" + cf + "';";
		boolean response = false;
		try {
			getConnection();
			rs = createStatementAndRSForQuery(sql);
			rs.first();
			int firstPosition = rs.getRow();
			rs.last();
			int lastPosition = rs.getRow();
			if (firstPosition == 0 && lastPosition == 0)
				response = false;
			else if (firstPosition == 1 && lastPosition == 1)
				response = true;
			// Mi devo posizionare sulla prima riga e devo analizzare l'ID del primo record
			closeResultSet();
			closeStatement();
			closeConnection();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;

	}

	public boolean verifyPasswordMatch(String psw, String conf_psw) {
		if(psw.compareTo(conf_psw) == 0) {
			return true;
		}
		return false;
	}

	/*
	 * METODO PER LA RESTIDUZIONE DELL'ATTRIBUTO cod_user NEL DATABASE UTILE PER
	 * L'AGGIUNTA DI UNA SPEDIZIONE IN QUANTO HA COME ATTRIBUTI LE CHIAVI PRIMARIE
	 * DEGLI UTENTI
	 */

	public int getCod(String hashedid, String hashedpsw) {
		String sql = "SELECT userid FROM users WHERE(id='" + hashedid + "'AND psw='" + hashedpsw + "');";
		int codUtente = 0;
		try {
			getConnection();
			rs = createStatementAndRSForQuery(sql);
			rs.first();
			codUtente = rs.getInt("userid");
			closeResultSet();
			closeStatement();
			closeConnection();
		} catch (SQLException e) {
			// indica che non esiste
			codUtente = -1;
		}
		return codUtente;
	}

    /*public void listUsers() {
        String sql = "SELECT * FROM Users";
        try {
            getConnection();
            rs = createStatementAndRSForQuery(sql);
            while (rs.next()) {
                String idUtente = rs.getString("id_user");
                String pswUtente = rs.getString("psw_user");
                String nomeUtente = rs.getString("nome");
                String cognomeUtente = rs.getString("surname");
                String emailUtente = rs.getString("email");
                String cityUtente = rs.getString("user_city");
                String roadUtente = rs.getString("user_road");
                int road_numberUtente = rs.getInt("user_road_number");
                // INSERIMENTO DEL SYSTEMOUT A FINI DI TESTING
                System.out.println("L'utente con cod. " + rs.getString("cod_user") + " è: " + idUtente + " " + pswUtente
                        + " " + nomeUtente + " " + cognomeUtente + " " + emailUtente + " " + cityUtente + " "
                        + roadUtente + " " + road_numberUtente);
            }
            closeResultSet();
            closeStatement();
            closeConnection();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            // return false;
        }

    }*/



}
