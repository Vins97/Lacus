package it.unicam.ids.lacus.model;

import it.unicam.ids.lacus.database.DatabaseConnection;
import it.unicam.ids.lacus.view.Alerts;

import java.sql.ResultSet;

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

	/*
	 * LA CLASSE IMPLEMENTA I SEGUENTI METODI: 1- METODO X EFFETTUARE UNA RICHIESTA
	 * DI SPEDIZIONE 2- METODO CHE PERMETTE AL DESTINATARIO DI VISUALIZZARE UNA
	 * RICHIESTA DI SPEDIZIONE IN ARRIVO 3- METODO PER ACCETTARE UNA RICHIESTA DI
	 * SPEDIZIONE 4- METODO PER VISUALIZZARE LE SPEDIZIONI DISPONIBILI IN UNA CITTA'
	 * 5- METODO PER ACCETTARE UNA SPEDIZIONE DISPONIBILE 6- METODO PER VEDERE IL
	 * RIEPILOGO DELLE SPEDIZIONI EFFETTUATE
	 */

	/*
	 * SOSTITUIRE IL COD_RECIPIENT CON IL RISULTATO DEL METODO SEARCH USER IN QUANTO
	 * IL CODICE DELL'UTENTE E' LA CHIAVE DELL'UTENTE metodo che crea una spedizione
	 * con codice 0 e con stato pagamento impostato a falso, significa che viene
	 * impostata come una richiesta di spedizione, la coppia valore 0/false può
	 * essere usata in futuro
	 */
	// 1- METODO X EFFETTUARE UNA RICHIESTA DI SPEDIZIONE
	public boolean addShipment(String description, String sender_id, String sender_city, String sender_street, String sender_street_number, String recipient_id, String recipient_city, String recipient_street, String recipient_street_number) {
		Alerts alert = new Alerts();
		if((checkShipmentSpelling(description, sender_city, sender_street, sender_street_number, recipient_id, recipient_city, recipient_street, recipient_street_number))) {
			if(checkDataConsistency(sender_id, sender_city, recipient_id, recipient_city)) {
				String sql = "INSERT INTO shipment(status, description, sender_id, sender_city, sender_street, sender_street_number,"
						+ "recipient_id, recipient_city, recipient_street, recipient_street_number) values ('1','" + description + "','"
						+ sender_id + "','" + sender_city + "','" + sender_street + "','" + sender_street_number + "','" + recipient_id + "','"
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

	public ResultSet shipmentRequests(int id) {
		String sql = "SELECT * FROM shipment WHERE(recipient_id='" + id + "' AND status='1') OR(sender_id='" + id + "' And status='3');";
		getConnection();
		return createStatementAndRSForQuery(sql);
	}

	public void confirmShipment(String shipmentid) {
		String sql = "UPDATE shipment SET status='2' WHERE shipment_id='" + shipmentid + "';";
		getConnection();
		createStatementForUpdate(sql);
	}

	public void refuseShipment(String shipmentid) {
		String sql = "UPDATE shipment SET status='0' WHERE shipment_id='" + shipmentid + "';";
		getConnection();
		createStatementForUpdate(sql);
	}

	public ResultSet deliveriesList(int id) {
		String sql = "SELECT * FROM shipment WHERE NOT(sender_id='" + id + "' OR recipient_id='" + id + "') AND status='2'";
		getConnection();
		return createStatementAndRSForQuery(sql);
	}

	public void confirmDelivery(String shipmentid, int carrier, double payment) {
		String sql = "UPDATE shipment SET status='3', carrier_id='" + carrier + "', payment='" + payment + "' WHERE shipment_id='" + shipmentid + "';";
		getConnection();
		createStatementForUpdate(sql);
	}

	/*// 2- METODO CHE PERMETTE AL DESTINATARIO DI VISUALIZZARE UNA RICHIESTA DI
	// SPEDIZIONE IN ARRIVO
	public ResultSet seeShippingRequests(Users utente) {
		String sql = "SELECT * FROM shipping WHERE id_user='" + utente.getId() + "' AND status_shipping=" + 0 + ";";
		DatabaseOperation dbop = new DatabaseOperation();
		getConnection();
		rs = createStatementAndRSForQuery(sql);
		if(dbop.resultSetRows(rs)==0)
			System.err.println("Non ci sono richieste di spedizione!");
		else return rs;

		return rs;

	}
	*//* 3- METODO PER ACCETTARE UNA RICHIESTA DI SPEDIZIONE *//*
	public void recipientAcceptShipping(int codShipping) {
		String sql = "UPDATE shipping SET status_shipping="+
				1+" WHERE cod_shipping="+codShipping+";";
		getConnection();
		createStatementForUpdate(sql);

	}

	// 4- METODO PER VISUALIZZARE LE SPEDIZIONI DISPONIBILI IN UNA CITTA'
	*//*Il metodo dovrebbe visualizzare L'indirizzo mittente , l'indirizzo destinatario
	e l'importo del pagamaneto. QUINDI C'E BISOGNO DI UN INNER JOIN*//*
	public ResultSet seeShippingsAvaibleForCourier(String city) {
		String sql = "SELECT road_sender,road_recipient FROM shipping WHERE city='"+city+";";
		DatabaseOperation dbop = new DatabaseOperation();
		ResultSet rs;
		getConnection();
		rs = createStatementAndRSForQuery(sql);
		if(dbop.resultSetRows(rs)==0) rs=null;
		return rs;
	}

	// 5- METODO PER ACCETTARE UNA SPEDIZIONE DISPONIBILE
	public void acceptToExecAShipping(Users utente , int codShipping) {
		String sql = "UPDATE shipping SET cod_courier='"+utente.getCod(
				utente.getName(),utente.getSurname())+"' AND status_shipping="+
				2+" WHERE cod_shipping="+codShipping+";";
		getConnection();
		createStatementForUpdate(sql);
	}

	// 6- METODO PER VEDERE IL RIEPILOGO DELLE SPEDIZIONI EFFETTUATE
	public ResultSet deliveryList(Users utente) {
		String sql = "SELECT * FROM shipping WHERE cod_sender='"+utente.getCod(utente.getName(),utente.getSurname())+
				"'OR cod_recipient='"+utente.getCod(utente.getName(),utente.getSurname())+"';";
		DatabaseOperation dbop = new DatabaseOperation();
		ResultSet rs;
		getConnection();
		rs = createStatementAndRSForQuery(sql);
		if(dbop.resultSetRows(rs)==0) rs=null;
		return rs;
	}
	*//*7- METODO CHE PERMETTO DI CANCELLARE LE SPEDIZIONI NON ACCETTATE DOPO UN
	 * CERTO ARCO DI TEMPO
	 */
}

