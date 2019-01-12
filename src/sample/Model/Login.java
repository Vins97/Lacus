package sample.Model;

import sample.DataBase.ConnectionDataBase;
import sample.DataBase.Users;

import java.sql.SQLException;

public class Login {
    public static int userLogin(String id , String psw) {
        int responceCheck = 0;
        //1- ESEGUO CONTROLLI SULLA STRINGA
        if(StringChecker.specialCharacterChecker(id) && StringChecker.specialCharacterChecker(psw)){
            //2- SE LA STRINGA E' GIUSTA ESEGUO Users.searchUser(String id , String psw )
            Users utente = new Users();
            try {
                if(utente.searchUser(id,psw)) {
                    utente.setId(id);
                    utente.setPsw(psw);
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

