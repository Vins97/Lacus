package it.unicam.ids.lacus.controller;
import it.unicam.ids.lacus.Main;
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

	//Schermate principali del programma
    @FXML
    private AnchorPane ancPnRegister, ancPnDash, ancPnLogin;

    //Caselle di testo della schermata di login
	@FXML
	private TextField txtUsername;

	//Caselle password della schermata di login
	@FXML
	private PasswordField txtPassword;

	//Pulsanti della schermata di login
	@FXML
	private Button btnLogin, btnRegister;

    //Caselle di testo della schermata di registrazione
    @FXML
    private TextField txtNomeReg, txtCognomeReg, txtEmailReg, txtUsernameReg, txtCodiceFiscaleReg, txtCittaReg, txtIndirizzoReg, txtNumeroReg;

	//Caselle di testo della schermata di registrazione
	@FXML
	private PasswordField txtPasswordReg, txtConfermaPasswordReg;

    //Pulsanti della schermata di registrazione
    @FXML
    private Button btnRegisterReg, btnBackToLogin;

    //Pannelli della schermata home
	@FXML
	private Pane pnlHome, pnlProfile, pnlShipping;

	//Pulsanti della schermata home
    @FXML
    private Button btnHome, btnProfile, btnShipping, btnLogout;

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
    private VBox ordersHistory;

    @FXML
    private Label lblTitle;

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

   private void  dashboardPanel() {
       switch(Login.userLogin(txtUsername.getText(), txtPassword.getText())){
           case -2: {
           	Alert alert = new Alert(Alert.AlertType.ERROR);
           	alert.setTitle("Errore di login");
           	alert.setHeaderText(null);
           	alert.setContentText("I campi non ammettono caratteri non alfanumerici!");
           	alert.showAndWait();
           	break;
           }
           case -1: {
           	Alert alert = new Alert(Alert.AlertType.ERROR);
           	alert.setTitle("Errore di login");
			alert.setHeaderText(null);
			alert.setContentText("Username o password errati!");
			alert.showAndWait();
           	break;
           }
           case  1: {
           	ancPnDash.toFront();
           	break;
           }
       }
   }

   private void loginPanel(){
   		ancPnLogin.toFront();
   }

   private void registerPanel() {
        ancPnRegister.toFront();
    }

    @FXML
    void btnCloseAction() {
        Platform.exit();
    }

	@FXML
	void btnMinimizeAction() {
		Main main = new Main();
		main.minimize();
	}

    @FXML
    void handleClicks(ActionEvent event) throws Exception {
        if (event.getSource() == btnLogin){
            dashboardPanel();
        }
        if (event.getSource() == btnRegister){
            registerPanel();
        }
        if (event.getSource() == btnRegisterReg){
            Users user = new Users();
            if(user.registerUser(txtNomeReg.getText(), txtCognomeReg.getText(), txtUsernameReg.getText(), txtPasswordReg.getText(), txtConfermaPasswordReg.getText(), txtEmailReg.getText(), txtCodiceFiscaleReg.getText(), txtCittaReg.getText(), txtIndirizzoReg.getText(), txtNumeroReg.getText())) {
				loginPanel();
			}
        }
		if (event.getSource() == btnBackToLogin){
			loginPanel();
		}
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
