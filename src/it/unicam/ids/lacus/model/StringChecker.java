package it.unicam.ids.lacus.model;

import it.unicam.ids.lacus.database.DatabaseOperation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringChecker {
	public int characterAndNumberChecker(String s) {
		if (s == null || s.trim().isEmpty())
			return 0; //La stringa è vuota
		Pattern p = Pattern.compile("[^A-Za-z0-9]");
		Matcher m = p.matcher(s);
		boolean b = m.find();
		if (b == true)
			return -1; //La stringa non è valida
		else
			return 1; //La stringa è valida
	}
	/*
	 * METODO CHE CONTROLLA: 1) SE LA STRINGA HA CARATTERI SPECIALI 2) SE CONTIENE
	 * SOLO LETTERE String s deve essere passata in toLowerCase()
	 */

	public int characterNumberAndSpacesChecker(String s) {
		if (s == null || s.trim().isEmpty())
			return 0; //La stringa è vuota
		Pattern p = Pattern.compile("[^A-Za-z0-9]+$");
		Matcher m = p.matcher(s);
		boolean b = m.find();
		if (b == true)
			return -1; //La stringa non è valida
		else
			return 1; //La stringa è valida
	}

	public int characterAndSpacesChecker(String s) {
		if (s == null || s.trim().isEmpty())
			return 0; //La stringa è vuota
		Pattern p = Pattern.compile("[^A-Za-z]+$");
		Matcher m = p.matcher(s);
		boolean b = m.find();
		if (b == true)
			return -1; //La stringa non è valida

		else
			return 1; //La stringa è valida
	}

	// metodo che controlla il formato del CAP e dei numeri civici
	// nato per il controllo dei numeri civici ma utilizzato anche per
	// il CAP in quanto viene effettuato il controllo preliminare sulla
	// lunghezza della scringa che deve essere di 5 caratteri
	public int numberOnlyChecker(String cr) {
		if (cr == null || cr.trim().isEmpty()) {
			return 0; //La stringa è vuota
		}
		Pattern p = Pattern.compile("[1-9]");
		Matcher m = p.matcher(cr);
		boolean b = m.find();
		if (b == false)
			return -1; //La stringa non è valida
		else
			return 1; //La stringa è valida

	}

	/*
	 * Ritorna: 0 se l'email inserita è vuota -1 se l'email è di un formato errato
	 * -2 se il formato è giusto ma il dominio non va bene +1 se il dominio e
	 * formato email sono giusti
	 */
	public int emailChecker(String email) {
		if (email == null || email.trim().isEmpty()) {
			return 0; //La stringa è vuota
		}
		Pattern p = Pattern.compile("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}");
		Matcher m = p.matcher(email);
		boolean b = m.find();
		if (b == true) {
			String emailDomain = email.substring((email.indexOf("@") + 1), email.length());
			// ESEGUO LA QUERY SUL DATABASE
			String sql = "SELECT * FROM email WHERE email='" + emailDomain + "';";
			DatabaseOperation emailOperation = new DatabaseOperation();
			if (emailOperation.searchElement(sql))
				return 1; //La stringa è valida
			else
				return -2; //Il dominio della mail non esiste
		} else
			return -1; //La stringa non è valida
	}

	public boolean cityChecker(String city) {
		// ESEGUO LA QUERY SUL DATABASE
		String sql = "SELECT * FROM cities WHERE city='" + city + "';";
		DatabaseOperation cityOperation = new DatabaseOperation();
		return cityOperation.searchElement(sql);
	}

	public boolean idChecker(String id) {
		// ESEGUO LA QUERY SUL DATABASE
		String sql = "SELECT * FROM users WHERE userid='" + id + "';";
		DatabaseOperation userOperation = new DatabaseOperation();
		return userOperation.searchElement(sql);
	}
}