package it.unicam.ids.lacus.controller;

import it.unicam.ids.lacus.database.DataBaseOperation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringChecker {
	// RITORNA TRUE SE LA STRINGA NON CONTIENE CARATTERI SPECIALI: USATO PER: ID E PSW
	public int characterAndNumberChecker(String s) {
		if (s == null || s.trim().isEmpty())
			return 0; //La stringa è vuota
		Pattern p = Pattern.compile("[^A-Za-z0-9]");
		Matcher m = p.matcher(s);
		// boolean b = m.matches();
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

	public int characterOnlyChecker(String s) {
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
			DataBaseOperation operationForEmail = new DataBaseOperation();
			if (operationForEmail.searchEmailDomain(emailDomain))
				return 1; //La stringa è valida
			else
				return -2; //Il dominio della mail non esiste
		} else
			return -1; //La stringa non è valida
	}
}