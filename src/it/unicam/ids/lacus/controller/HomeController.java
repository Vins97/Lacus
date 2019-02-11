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

    @FXML
    private AnchorPane ancPnRegister;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtUsernameReg;

    @FXML
    private TextField txtPasswordReg;

    @FXML
    private TextField txtConfermaPassword;

    @FXML
    private TextField txtCodiceFiscale;

    @FXML
    private TextField txtCitta;

    @FXML
    private TextField txtIndirizzo;

    @FXML
    private TextField txtNumero;

    @FXML
    private Button btnRegisterReg;

	@FXML
	private Button btnBackToLogin;

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
    void btnCloseAction(ActionEvent event) {
        Platform.exit();
    }

	@FXML
	void btnMinimizeAction(ActionEvent event) {
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
            if(user.registerUser(txtNome.getText(), txtCognome.getText(), txtUsernameReg.getText(), txtPasswordReg.getText(), txtConfermaPassword.getText(), txtEmail.getText(), txtCodiceFiscale.getText(), txtCitta.getText(), txtIndirizzo.getText(), txtNumero.getText())) {
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
