package it.unicam.ids.lacus.model;

import it.unicam.ids.lacus.database.Hash;
import it.unicam.ids.lacus.database.Users;

public class Login {
	public static int userLogin(String id , String psw) {
		int responceCheck = 0;
		//1- ESEGUO CONTROLLI SULLA STRINGA
		if(StringChecker.specialCharacterChecker(id) && StringChecker.specialCharacterChecker(psw)){
			//2- SE LA STRINGA E' GIUSTA ESEGUO Users.searchUser(String id , String psw )
			Users utente = new Users();
			try {
				if(utente.searchUser(Hash.getMd5(id), Hash.getMd5(psw))) {
					utente.setActiveUser(id, psw);
					responceCheck= 1;
				}
				else {
					responceCheck= -1; //Credenziali errate
				}
			}catch(Exception e ) {
				e.getMessage();
			}
		}else if(!(StringChecker.specialCharacterChecker(id)) || !(StringChecker.specialCharacterChecker(psw))) {
			responceCheck= -2;
		}
		return responceCheck;
	}

}

