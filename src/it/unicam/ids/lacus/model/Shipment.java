package it.unicam.ids.lacus.model;

import it.unicam.ids.lacus.database.DatabaseConnection;
import it.unicam.ids.lacus.database.DatabaseOperation;
import it.unicam.ids.lacus.view.Alerts;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Shipment extends DatabaseConnection {
	/*
	Legenda spedizioni:
	0 - Spedizione annullata
	1 - Spedizione creata ma non accettata
	2 - Spedizione accettata dal destinatario
	3 - Spedizione scelta dal corriere ma non pagata
	4 - Spedizione scelta dal corriere e pagata
	5 - Spedizione in transito
	6 - Spedizione consegnata
	*/

	public boolean addShipment(String description, String sender_id, String sender_city, String sender_street, String sender_street_number, String recipient_id, String recipient_city, String recipient_street, String recipient_street_number) {
		Alerts alert = new Alerts();
		if((checkShipmentSpelling(description, sender_city, sender_street, sender_street_number, recipient_id, recipient_city, recipient_street, recipient_street_number))) {
			if(checkDataConsistency(sender_id, sender_city, recipient_id, recipient_city)) {
				String sql = "INSERT INTO shipment(status, description, sender_id, sender_city, sender_street, sender_street_number,"
						+ "carrier_id, recipient_id, recipient_city, recipient_street, recipient_street_number) values ('1','" + description + "','"
						+ sender_id + "','" + sender_city + "','" + sender_street + "','" + sender_street_number + "', '-2', '" + recipient_id + "','"
						+ recipient_city + "','" + recipient_street + "','" + recipient_street_number + "')";
				getConnection();
				createStatementForUpdate(sql);
				alert.printShipmentAddedMessage();
				return true;
			}
		}
		return false;
	}

	private boolean checkShipmentSpelling(String description, String sender_city, String sender_street, String sender_street_number, String recipient_id, String recipient_city, String recipient_street, String recipient_street_number) {
		//Raggruppa le stringhe per tipo (quelle che devono contenere solo lettere e quelle che possono contenere lettere e numeri)
		String[] characters = {sender_city, sender_street, recipient_city, recipient_street};
		String[] numbers = {sender_street_number, recipient_id, recipient_street_number};
		StringChecker check = new StringChecker();
		Alerts alert = new Alerts();
		//Controllo la correttezza della descrizione
		switch(check.characterNumberAndSpacesChecker(description)) {
			case 0: {
				alert.printEmptyFieldsMessage();
				return false;
			}
			case -1: {
				alert.printInvalidCharactersMessage();
				return false;
			}
		}
		//Controlla le stringhe che devono contenere solo lettere o spazi
		for(int i=0; i<4; i++) {
			switch(check.characterAndSpacesChecker(characters[i])) {
				case 0: {
					alert.printEmptyFieldsMessage();
					return false;
				}
				case -1: {
					alert.printInvalidCharactersMessage();
					return false;
				}
			}
		}
		//Controlla le stringhe che devono contenere solo numeri
		for(int i=0; i<3; i++) {
			switch(check.numberOnlyChecker(numbers[i])) {
				case 0: {
					alert.printEmptyFieldsMessage();
					return false;
				}
				case -1: {
					alert.printInvalidCharactersMessage();
					return false;
				}
			}
		}
		return true;
	}

	private boolean checkDataConsistency(String sender_id, String sender_city, String recipient_id, String recipient_city) {
		StringChecker citycheck = new StringChecker();
		Alerts alert = new Alerts();
		if(sender_id.compareTo(recipient_id) == 0) {
			alert.printIdenticalIdsMessage();
			return false;
		}
		if(!citycheck.idChecker(recipient_id)) {
			alert.printUnknownIdMessage();
			return false;
		}
		if(citycheck.cityChecker(sender_city) && citycheck.cityChecker(recipient_city)) {
			return true;
		}
		else {
			alert.printUnknownCityMessage();
			return false;
		}
	}

	public int waitingShipments(String id) {
		String sql = "SELECT * FROM shipment WHERE(sender_id='" + id + "' OR recipient_id='" + id + "') AND NOT(status='0' OR status='5' OR status='6');";
		getConnection();
		createStatementAndRSForQuery(sql);
		DatabaseOperation dbop = new DatabaseOperation();
		return dbop.resultSetRows(rs);
	}

	public int deliveringShipments(String id) {
		String sql = "SELECT * FROM shipment WHERE(sender_id='" + id + "' OR recipient_id='" + id + "') AND(status='5');";
		getConnection();
		createStatementAndRSForQuery(sql);
		DatabaseOperation dbop = new DatabaseOperation();
		return dbop.resultSetRows(rs);
	}

	public int deliveredShipments(String id) {
		String sql = "SELECT * FROM shipment WHERE(sender_id='" + id + "' OR recipient_id='" + id + "') AND(status='6');";
		getConnection();
		createStatementAndRSForQuery(sql);
		DatabaseOperation dbop = new DatabaseOperation();
		return dbop.resultSetRows(rs);
	}

	public ResultSet shipmentRequests(int id) {
		String sql = "SELECT * FROM shipment WHERE(recipient_id='" + id + "' AND status='1') OR(sender_id='" + id + "' AND status='3');";
		getConnection();
		return createStatementAndRSForQuery(sql);
	}

	public void confirmShipment(String shipmentid) {
		String sql = "UPDATE shipment SET status='2' WHERE shipment_id='" + shipmentid + "';";
		getConnection();
		createStatementForUpdate(sql);
	}

	public double getPayment(String shipmentid) {
		String sql = "SELECT * FROM shipment WHERE shipment_id='" + shipmentid + "';";
		getConnection();
		ResultSet rs = createStatementAndRSForQuery(sql);
		double result = 0;
		try {
			rs.first();
			result = Double.parseDouble(rs.getString("payment"));
		} catch (SQLException e) {
			Alerts alert = new Alerts();
			alert.printDatabaseConnectionError();
		}
		return result;
	}

	public String[] getAllDetails(String shipmentid) {
		String sql = "SELECT * FROM shipment WHERE shipment_id='" + shipmentid + "';";
		getConnection();
		ResultSet rs = createStatementAndRSForQuery(sql);
		String[] details = new String[13];
		try {
			rs.first();
			details[0] = shipmentid;
			details[1] = rs.getString("description");
			details[2] = rs.getString("status");
			details[3] = rs.getString("sender_id");
			details[4] = rs.getString("sender_city");
			details[5] = rs.getString("sender_street") + " " + rs.getString("sender_street_number");
			if((details[6] = rs.getString("carrier_id")).compareTo("-2") == 0) {
				details[6] = "Da definire";
			}
			if((details[7] = rs.getString("payment")) == null) {
				details[7] = "Da definire";
			}
			else {
				details[7] = details[7] + " €";
			}
			if((details[8] = rs.getString("date_shipping")) == null) {
				details[8] = "Da definire";
			}
			else {
				StringChecker sc = new StringChecker();
				details[8] = sc.dateConverter(details[8]);
			}
			if((details[9] = rs.getString("date_arrival")) == null) {
				details[9] = "Da definire";
			}
			else {
				StringChecker sc = new StringChecker();
				details[9] = sc.dateConverter(details[9]);
			}
			details[10] = rs.getString("recipient_id");
			details[11] = rs.getString("recipient_city");
			details[12] = rs.getString("recipient_street") + " " + rs.getString("recipient_street_number");
		} catch (SQLException e) {
			Alerts alert = new Alerts();
			alert.printDatabaseConnectionError();
		}
		return details;
	}

	public void confirmPayment(String shipmentid) {
		String sql = "UPDATE shipment SET status='4' WHERE shipment_id='" + shipmentid + "';";
		getConnection();
		createStatementForUpdate(sql);
	}

	public void refusePayment(String shipmentid) {
		String sql = "UPDATE shipment SET status='2', carrier_id='-2', payment=null WHERE shipment_id='" + shipmentid + "';";
		getConnection();
		createStatementForUpdate(sql);
	}

	public void inTransit(String shipmentid) {
		String sql = "UPDATE shipment SET status='5' WHERE shipment_id='" + shipmentid + "';";
		getConnection();
		createStatementForUpdate(sql);
	}

	public void delivered(String shipmentid) {
		String sql = "UPDATE shipment SET status='6' WHERE shipment_id='" + shipmentid + "';";
		getConnection();
		createStatementForUpdate(sql);
	}

	public void cancelShipment(String shipmentid) {
		String sql = "UPDATE shipment SET status='0' WHERE shipment_id='" + shipmentid + "';";
		getConnection();
		createStatementForUpdate(sql);
	}

	public void deleteShipment(String shipmentid) {
		String sql = "DELETE FROM shipment WHERE shipment_id = '" + shipmentid + "';";
		getConnection();
		createStatementForUpdate(sql);
	}

	public ResultSet deliveriesList(int id) {
		String sql = "SELECT * FROM shipment WHERE NOT(sender_id='" + id + "' OR recipient_id='" + id + "') AND status='2';";
		getConnection();
		return createStatementAndRSForQuery(sql);
	}

	public ResultSet myShipments(int id) {
		String sql = "SELECT * FROM shipment WHERE (carrier_id='" + id + "' OR sender_id='" + id + "' OR recipient_id='" + id + "');";
		getConnection();
		return createStatementAndRSForQuery(sql);
	}

	public boolean paymentRequestPaid(String shipmentid) {
		String sql = "SELECT * FROM shipment WHERE shipment_id='" + shipmentid + "';";
		getConnection();
		ResultSet rs = createStatementAndRSForQuery(sql);
		boolean result = false;
		try {
			rs.first();
			result = rs.getString("status").compareTo("4") == 0;
		}
		catch(SQLException e) {
			Alerts alert = new Alerts();
			alert.printDatabaseConnectionError();
		}
		return result;
	}

	public boolean deliveryAvailable(String shipmentid) {
		String sql = "SELECT * FROM shipment WHERE shipment_id='" + shipmentid + "';";
		getConnection();
		ResultSet rs = createStatementAndRSForQuery(sql);
		boolean result = false;
		try {
			rs.first();
			result = rs.getString("status").compareTo("2") == 0;
		}
		catch(SQLException e) {
			Alerts alert = new Alerts();
			alert.printDatabaseConnectionError();
		}
		return result;
	}

	public void confirmDelivery(String shipmentid, int carrier, String iban, double payment, Date shipping, Date arrival) {
		String sql = "UPDATE shipment SET status='3', carrier_id='" + carrier + "', carrier_iban='" + iban + "', payment='" + payment + "', date_shipping='" + shipping + "', date_arrival='" + arrival + "' WHERE shipment_id='" + shipmentid + "';";
		getConnection();
		createStatementForUpdate(sql);
	}

	public String setStatus(String status) {
		switch(Integer.parseInt(status)) {
			//Non accettata dal destinatario o annullata da mittente o destinatario prima del pagamento
			case 0: return "Annullata";
			//Creata dal mittente e in attesa che il destinatario la confermi tramite la sezione "Richieste"
			case 1: return "In attesa del destinatario";
			//Aggiunta alla lista delle consegne, cioè in attesa che un corriere la scelga
			case 2: return "In attesa del corriere";
			//Scelta da un corriere, ovvero in attesa che il mittente la paghi tramite la sezione "Richieste"
			case 3: return "In attesa di pagamento";
			//Pagata, in attesa che il corriere si presenti dal mittente per recuperare il pacco
			case 4: return "In attesa di ritiro";
			//Recuperato il pacco, in viaggio tramite corriere
			case 5: return "In transito";
			//Consegnata al destinatario
			case 6: return "Consegnata";
		}
		return "Sconosciuto";
	}
}

