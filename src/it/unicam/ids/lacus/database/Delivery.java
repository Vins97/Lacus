package it.unicam.ids.lacus.database;

import java.sql.SQLException;
import java.sql.ResultSet;

public class Delivery extends ConnectionDataBase {
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
	public void addDelivery(Users utente, String nomeDestinatario, String cognomeDestinatario) throws SQLException {
		Users destinatario = new Users();
		int codDest = destinatario.getCod(nomeDestinatario, cognomeDestinatario);
		// codDest = -1 indica che non esiste
		if (codDest != -1) {
			String sql = "INSERT INTO shipping(cod_sender,cod_recipient,status,status_payment"
					+ ",status_shipping) values (" + utente.getCod(utente.getName(), utente.getSurname()) + ","
					+ destinatario.getCod(nomeDestinatario, cognomeDestinatario) + ",'0',false)";
			getConnection();
			createStatementForUpdate(sql);
		} else
			System.out.println("Ricontrollare le credenziali del destinatario!");

	}

	// 2- METODO CHE PERMETTE AL DESTINATARIO DI VISUALIZZARE UNA RICHIESTA DI
	// SPEDIZIONE IN ARRIVO
	public ResultSet seeShippingRequest(Users utente) {
		String sql = "SELECT * FROM shipping WHERE id_user='" + utente.getId() + "' AND status_shipping=" + 0 + ";";
		getConnection();
		rs = createStatementAndRSForQuery(sql);
		if(DataBaseOperation.resultSetRows(rs)==0)
			System.err.println("Non ci sono richieste di spedizione!");
		else return rs;

		return rs;

	}
	/* 3- METODO PER ACCETTARE UNA RICHIESTA DI SPEDIZIONE */
	public void recipientAcceptShipping(int codShipping) {
		String sql = "UPDATE shipping SET status_shipping="+
				1+" WHERE cod_shipping="+codShipping+";";
		ResultSet rs = null ;
		getConnection();
		createStatementForUpdate(sql);

	}

	// 4- METODO PER VISUALIZZARE LE SPEDIZIONI DISPONIBILI IN UNA CITTA'
	/*Il metodo dovrebbe visualizzare L'indirizzo mittente , l'indirizzo destinatario
	e l'importo del pagamaneto. QUINDI C'E BISOGNO DI UN INNER JOIN*/
	public ResultSet seeShippingsAvaibleForCourier(String city) {
		String sql = "SELECT road_sender,road_recipient FROM shipping WHERE city='"+city+";";
		ResultSet rs = null;
		getConnection();
		rs = createStatementAndRSForQuery(sql);
		if(DataBaseOperation.resultSetRows(rs)==0) rs=null;
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
		ResultSet rs = null;
		getConnection();
		rs = createStatementAndRSForQuery(sql);
		if(DataBaseOperation.resultSetRows(rs)==0) rs=null;
		return rs;
	}
	/*7- METODO CHE PERMETTO DI CANCELLARE LE SPEDIZIONI NON ACCETTATE DOPO UN
	 * CERTO ARCO DI TEMPO
	 */

}

