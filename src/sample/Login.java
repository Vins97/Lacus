package sample;

import sample.DataBase.Users;

public class Login {
    public static void userLogin(String id , String psw) {
        //1- ESEGUO CONTROLLI SULLA STRINGA
        if(StringChecker.specialCharacterChecker(id) && StringChecker.specialCharacterChecker(psw)){
            //2- SE LA STRINGA E' GIUSTA ESEGUO Users.searchUser(String id , String psw )
            Users utente = new Users();
            try {
                if(utente.searchUser(id,psw)) {
                    utente.setId(id);
                    utente.setPsw(psw);
                    System.out.println("Credenziali corrette!");
                }
                else System.out.println("Id o Password errati!");
            }catch(Exception e ) {
                e.getMessage();
            }
        }else if(!(StringChecker.specialCharacterChecker(id)) || !(StringChecker.specialCharacterChecker(psw))) {
            System.out.println("Il formato delle credenziali non è corretto");
        }

    }

}