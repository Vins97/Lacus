package it.unicam.ids.lacus.model;

import it.unicam.ids.lacus.view.Alerts;

public class Login {
	public boolean userLogin(String id , String psw) {
		StringChecker check = new StringChecker();
		Users user = new Users();
		Alerts alert = new Alerts();
		switch(check.characterAndNumberChecker(id)) {
			case -1: {
				alert.printInvalidCharactersMessage();
				return false;
			}
			case 0: {
				alert.printEmptyFieldsMessage();
				return false;
			}
			case  1: {
				switch(check.characterAndNumberChecker(psw)) {
					case -1: {
						alert.printInvalidCharactersMessage();
						return false;
					}
					case 0: {
						alert.printEmptyFieldsMessage();
						return false;
					}
					case  1: {
						Hash hash = new Hash();
						try {
							if(user.searchUser(hash.getMd5(id), hash.getMd5(psw))) {
								user.setUserid(user.getUseridFromDatabase(hash.getMd5(id), hash.getMd5(psw)));
								return true;
							}
							alert.printLoginErrorMessage();
						}catch(Exception e ) {
							e.getMessage();
							return false;
						}
					}
				}
			}
		}
		return false;
	}
}

