package it.unicam.ids.lacus.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Users extends ConnectionDataBase {

    private String id, psw, name, surname, email, city, city_road;
    private int age, road_number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_road() {
        return city_road;
    }

    public void setCity_road(String city_road) {
        this.city_road = city_road;
    }

    public int getRoad_number() {
        return road_number;
    }

    public void setRoad_number(int road_number) {
        this.road_number = road_number;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 18)
            System.out.println("L'età deve essere maggiore di 18");
        else
            this.age = age;
    }

    public void addUser(String id_user, String psw_user, String nome, String surname, String email, String user_city, String city_road, String road_number) throws SQLException {
        // TODO Auto-generated method stubString
        String sql = "INSERT INTO Users (id_user,psw_user,nome,surname,email,user_city,"
                + "user_road,user_road_number) values " + "('" + Hash.getMd5(id_user) + "','" + Hash.getMd5(psw_user)
                + "','" + Hash.getMd5(nome) + "','" + Hash.getMd5(surname) + "','" + Hash.getMd5(email)
                + "','" + user_city + "','" + city_road + "','" + road_number + "');";
        if (!verifyIDExistency(id_user)) {
            try {
                getConnection();
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                closeStatement();
                closeConnection();

            } catch (SQLException e) {
                // TODO Auto-generated catch block e.printStackTrace();
                System.err.println(e.getMessage());
            }
            // Semplice stampa per il controllo dell'aggiunta del record
            if (verifyIDExistency(id_user))
                System.out.println("Il recordo è stato aggiunto");
        } else
            System.out.print("L'id inserito esiste");

    }

    public boolean searchUser(String id, String psw) {

        String sql = "SELECT id_user,psw_user FROM Users WHERE id_user='" + Hash.getMd5(id) + "' AND psw_user='" + Hash.getMd5(psw) + "';";
        boolean responce = false;
        ResultSet rs = null;
        try {
            getConnection();
            rs = createStatementAndRSForQuery(sql);
            rs.first();
            if(DataBaseOperation.resultSetRows(rs)==1)
                responce = true;
            else responce =  false;
            /*int firstPosition = rs.getRow();
            if (firstPosition == 0)
                responce = false;
            else if (firstPosition == 1)
                responce = true;*/

            closeResultSet();
            closeStatement();
            closeConnection();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return responce;

    }

    public boolean verifyIDExistency(String id) {
        String sql = "SELECT id_user FROM Users WHERE id_user='" + Hash.getMd5(id) + "';";
        boolean response = false;
        try {
            getConnection();
            rs = createStatementAndRSForQuery(sql);
            rs.first();
            int firstPosition = rs.getRow();
            rs.last();
            int lastPosition = rs.getRow();
            if (firstPosition == 0 && lastPosition == 0)
                response = false;
            else if (firstPosition == 1 && lastPosition == 1)
                response = true;
            // Mi devo posizionare sulla prima riga e devo analizzare l'ID del primo record
            closeResultSet();
            closeStatement();
            closeConnection();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

        return response;

    }

    /*
     * METODO PER LA RESTIDUZIONE DELL'ATTRIBUTO cod_user NEL DATABASE UTILE PER
     * L'AGGIUNTA DI UNA SPEDIZIONE IN QUANTO HA COME ATTRIBUTI LE CHIAVI PRIMARIE
     * DEGLI UTENTI
     */
    public int getCod(String nome, String surname) {
        String sql = "SELECT cod_user FROM Users WHERE(nome='" + nome + "'AND surname='" + surname + "');";
        int codUtente = 0;
        try {
            getConnection();
            rs = createStatementAndRSForQuery(sql);
            rs.first();
            codUtente = rs.getInt("cod_user");
            closeResultSet();
            closeStatement();
            closeConnection();
        } catch (SQLException e) {
            // indica che non esiste
            codUtente = -1;
        }
        return codUtente;
    }

    /*public void listUsers() {
        String sql = "SELECT * FROM Users";
        try {
            getConnection();
            rs = createStatementAndRSForQuery(sql);
            while (rs.next()) {
                String idUtente = rs.getString("id_user");
                String pswUtente = rs.getString("psw_user");
                String nomeUtente = rs.getString("nome");
                String cognomeUtente = rs.getString("surname");
                String emailUtente = rs.getString("email");
                String cityUtente = rs.getString("user_city");
                String roadUtente = rs.getString("user_road");
                int road_numberUtente = rs.getInt("user_road_number");
                // INSERIMENTO DEL SYSTEMOUT A FINI DI TESTING
                System.out.println("L'utente con cod. " + rs.getString("cod_user") + " è: " + idUtente + " " + pswUtente
                        + " " + nomeUtente + " " + cognomeUtente + " " + emailUtente + " " + cityUtente + " "
                        + roadUtente + " " + road_numberUtente);
            }
            closeResultSet();
            closeStatement();
            closeConnection();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            // return false;
        }

    }*/



}
