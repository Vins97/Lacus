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
						try {
							if(user.searchUser(Hash.getMd5(id), Hash.getMd5(psw))) {
								user.setActiveUser(id, psw);
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

