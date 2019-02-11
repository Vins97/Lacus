package it.unicam.ids.lacus.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unicam.ids.lacus.database.DataBaseOperation;

public class StringChecker {
	// RITORNA TRU SE LA STRINGA NON CONTIENE CARATTERI SPECIALI USATO PER: ID , PSW
	// ,
	public static boolean specialCharacterChecker(String s) {
		if (s == null || s.trim().isEmpty())
			return false;

		Pattern p = Pattern.compile("[^A-Za-z0-9]");
		Matcher m = p.matcher(s);
		// boolean b = m.matches();
		boolean b = m.find();
		if (b == true)
			return false;
		else
			return true;
	}
	/*
	 * METODO CHE CONTROLLA: 1) SE LA STRINGA HA CARATTERI SPECIALI 2) SE CONTIENE
	 * SOLO LETTERE String s deve essere passata in toLowerCase()
	 */

	public static boolean nameAndSurnameChecker(String s) {
		if (s == null || s.trim().isEmpty())
			return false;
		Pattern p = Pattern.compile("[^A-Za-z]");
		Matcher m = p.matcher(s);
		boolean b = m.find();
		if (b == true)
			return false;

		else
			return false;
	}

	public static void cityChecker(String city) {

	}

	public static void cityRoadChecker(String cr) {

	}

	// metodo che controlla il formato del CAP e dei numeri civici
	// nato per il controllo dei numeri civici ma utilizzato anche per
	// il CAP in quanto viene effettuato il controllo preliminare sulla
	// lunghezza della scringa che deve essere di 5 caratteri
	public static boolean numberStringChecker(String cr) {
		if (cr == null || cr.trim().isEmpty()) {
			return false;
		}
		Pattern p = Pattern.compile("[1-9]{1,5}");
		Matcher m = p.matcher(cr);
		boolean b = m.find();
		if (b == true)
			return true;
		else
			return false;

	}

	/*
	 * Ritorna: -3 se l'email inserita è vuota -2 se l'email è di un formato errato
	 * -1 se il formato è giusto ma il dominio non va bene +1 se il dominio e
	 * formato email sono giusti
	 */
	public static int emailChecker(String email) {
		if (email == null || email.trim().isEmpty()) {
			return -3;
		}
		Pattern p = Pattern.compile("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}");
		Matcher m = p.matcher(email);
		boolean b = m.find();
		if (b == true) {
			String emailDomain = email.substring((email.indexOf("@") + 1), email.length());
			// ESEGUO LA QUERY SUL DATABASE
			DataBaseOperation operationForEmail = new DataBaseOperation();
			if (operationForEmail.searchEmailDomain(emailDomain))
				return 1;
				// SE IL RESULT HA UNA RIGA ALLORA IL DOMINIO ESISTE
			else
				return -1;
		}

		else
			return -2;
	}

}