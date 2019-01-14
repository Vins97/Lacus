package it.unicam.ids.lacus.controller;
import javafx.scene.control.*;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import it.unicam.ids.lacus.model.Login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import it.unicam.ids.lacus.database.Users;

public class HomeController implements Initializable {


    @FXML
    private AnchorPane ancPnDash;

    @FXML
    private AnchorPane ancPnLogin;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnProfile;

    @FXML
    private Button btnShipping;

    @FXML
    private Button btnLogout;

    @FXML
    private Pane pnlHome;

    @FXML
    private Label shpAccettazione;

    @FXML
    private Label shpInAttesaCorriere;

    @FXML
    private Label shpConsegnate;

    @FXML
    private Pane pnlProfile;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtBoxName;

    @FXML
    private TextField txtBoxCognome;

    @FXML
    private TextField txtBoxCodiceFiscale;

    @FXML
    private DatePicker dateDatadinascita;

    @FXML
    private TextField txtBoxEmail;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnLoginFacebook;

    @FXML
    private Button btnLoginGoogle;

    @FXML
    private Pane pnlShipping;

    @FXML
    private VBox ordersHistory;

    @FXML
    private Label lblTitle;

    @FXML
    private Button btnMinimize;

    @FXML
    private Button btnClose;

    private void initializeShippingPanel(){
        lblTitle.setText("Lista spedizioni");
        pnlShipping.toFront();
    }
    private void initializeHomePanel(){
        lblTitle.setText("Riepilogo");
        pnlHome.toFront();
    }
    private void initializeProfilePanel(){
        lblTitle.setText("Profilo");
        pnlProfile.toFront();
    }

    /*private void dashboardPanel(){

        Users user = new Users();
        if(user.searchUser(txtUsername.getText(), txtPassword.getText())) {

            ancPnDash.toFront();
        } else {
            System.out.println("Codice Errato");
        }
    }*/
   private void  dashboardPanel() {
       switch(Login.userLogin(txtUsername.getText(), txtPassword.getText())){
           case -2: {System.out.print("I campi non ammettono caratteri non alfanumerici!");break;}
           case -1: {System.out.print("Id o password errati!");break;}
           case  1: {ancPnDash.toFront();break;}


        }
   }

    private void loginPanel(){
        ancPnLogin.toFront();
    }



    @FXML
    void btnCloseAction(ActionEvent event) {
        Platform.exit();
    }


    @FXML
    void handleClicks(ActionEvent event) throws Exception {
        if (event.getSource() == btnHome) {
            initializeHomePanel();
        }
        if (event.getSource() == btnProfile) {
            initializeProfilePanel();
        }
        if (event.getSource() == btnShipping) {
            initializeShippingPanel();
        }
        if (event.getSource() == btnLogout){
            loginPanel();
        }
        if (event.getSource() == btnLogin){
            Users user = new Users();
            if(user.searchUser(txtUsername.getText(), txtPassword.getText())) {
                dashboardPanel();
            } else {
                System.out.println("Codice Errato");
                //UNA FINESTRA (O ALTRO) SEGNALA USERNAME E PASSWORD ERRATI
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Node[] nodes = new Node[10];
        for (int i = 0; i < nodes.length; i++) {
            try {

                final int j = i;
                nodes[i] = FXMLLoader.load(getClass().getResource("../view/Shipment.fxml"));

                //give the items some effect

                nodes[i].setOnMouseEntered(event -> {
                    nodes[j].setStyle("-fx-background-color : #0A0E3F");
                });
                nodes[i].setOnMouseExited(event -> {
                    nodes[j].setStyle("-fx-background-color : #02030A");
                });
                ordersHistory.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
