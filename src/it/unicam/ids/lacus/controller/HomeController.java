package it.unicam.ids.lacus.controller;
import it.unicam.ids.lacus.Main;
import it.unicam.ids.lacus.database.Hash;
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

    //Titolo della schermata home
	@FXML
	private Label lblTitle;

    //Pannelli della schermata home
	@FXML
	private Pane pnlHome, pnlProfile, pnlShipping;

	//Pulsanti della schermata home
    @FXML
    private Button btnHome, btnProfile, btnShipping, btnLogout;

    //Caselle di testo della schermata del profilo
    @FXML
    private TextField txtNomeProf, txtCognomeProf, txtEmailProf, txtUsernameProf, txtCodiceFiscaleProf, txtCittaProf, txtIndirizzoProf, txtNumeroProf;

	//Caselle password della schermata del profilo
    @FXML
    private PasswordField txtPasswordProf, txtConfermaPasswordProf;

    //Pulsanti della schermata del profilo
	@FXML
	private Button btnModificaDatiProf;

    @FXML
    private VBox ordersHistory;

    private void initializeShippingPanel(){
        lblTitle.setText("Lista spedizioni");
        pnlShipping.toFront();
    }
    private void initializeHomePanel(){
        lblTitle.setText("Riepilogo");
        pnlHome.toFront();
    }
    private void initializeProfilePanel(){
        lblTitle.setText("Modifica Profilo");
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
        if(event.getSource() == btnLogin){
            dashboardPanel();
			clearLoginFields();
        }
        if(event.getSource() == btnRegister){
        	clearLoginFields();
            registerPanel();
        }
        if(event.getSource() == btnRegisterReg){
            Users user = new Users();
            if(user.registerUser(txtNomeReg.getText(), txtCognomeReg.getText(), txtUsernameReg.getText(), txtPasswordReg.getText(), txtConfermaPasswordReg.getText(), txtEmailReg.getText(), txtCodiceFiscaleReg.getText(), txtCittaReg.getText(), txtIndirizzoReg.getText(), txtNumeroReg.getText())) {
				clearRegFields();
            	loginPanel();
			}
        }
		if(event.getSource() == btnBackToLogin){
			clearRegFields();
			loginPanel();
		}
		if(event.getSource() == btnHome) {
			initializeHomePanel();
		}
		if(event.getSource() == btnProfile) {
			initializeProfilePanel();
		}
		if(event.getSource() == btnModificaDatiProf) {
			editProfile();
		}
		if(event.getSource() == btnShipping) {
			initializeShippingPanel();
		}
        if(event.getSource() == btnLogout){
        	Users user = new Users();
        	user.setActiveUser(null, null);
            loginPanel();
        }
    }

    private void clearLoginFields() {
    	txtUsername.clear();
    	txtPassword.clear();
	}

    private void clearRegFields() {
		txtNomeReg.clear();
		txtCognomeReg.clear();
		txtUsernameReg.clear();
		txtPasswordReg.clear();
		txtConfermaPasswordReg.clear();
		txtEmailReg.clear();
		txtCodiceFiscaleReg.clear();
		txtCittaReg.clear();
		txtIndirizzoReg.clear();
		txtNumeroReg.clear();
	}

	private void clearProfFields() {
		txtNomeProf.clear();
		txtCognomeProf.clear();
		txtUsernameProf.clear();
		txtPasswordProf.clear();
		txtConfermaPasswordProf.clear();
		txtEmailProf.clear();
		txtCodiceFiscaleProf.clear();
		txtCittaProf.clear();
		txtIndirizzoProf.clear();
		txtNumeroProf.clear();
	}

	private void editProfile() {
		Users user = new Users();
		if(!user.checkText(txtNomeProf.getText(), txtCognomeProf.getText(), txtUsernameProf.getText(), txtPasswordProf.getText(), txtEmailProf.getText(), txtCodiceFiscaleProf.getText(), txtCittaProf.getText(), txtIndirizzoProf.getText(), txtNumeroProf.getText(), true)) {
			return;
		}
		if(!txtNomeProf.getText().trim().isEmpty()) {
			String sql = "UPDATE users SET firstname='" + Hash.getMd5(txtNomeProf.getText()) + "' WHERE id='" + user.getId() + "' AND psw='" + user.getPsw() + "'";
			if(!user.updateUser(sql)) {
				return;
			}
		}
		if(!txtCognomeProf.getText().trim().isEmpty()) {
			String sql = "UPDATE users SET surname='" + Hash.getMd5(txtCognomeProf.getText()) + "' WHERE id='" + user.getId() + "' AND psw='" + user.getPsw() + "'";
			if(!user.updateUser(sql)) {
				return;
			}
		}
		if(!txtEmailProf.getText().trim().isEmpty()) {
			String sql = "UPDATE users SET email='" + Hash.getMd5(txtEmailProf.getText()) + "' WHERE id='" + user.getId() + "' AND psw='" + user.getPsw() + "'";
			if(!user.updateUser(sql)) {
				return;
			}
		}
		if(!txtUsernameProf.getText().trim().isEmpty()) {
			if(!user.searchUser(Hash.getMd5(txtUsernameProf.getText()), user.getPsw())) {
				String sql = "UPDATE users SET id='" + Hash.getMd5(txtUsernameProf.getText()) + "' WHERE id='" + user.getId() + "' AND psw='" + user.getPsw() + "'";
				if(user.updateUser(sql)) {
					user.setId(Hash.getMd5(txtUsernameProf.getText()));
				}
				else {
					return;
				}
			}
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Errore nella modifica del profilo");
				alert.setHeaderText(null);
				alert.setContentText("L'Username inserito è già in uso!");
				alert.showAndWait();
				return;
			}
		}
		if(!txtCodiceFiscaleProf.getText().trim().isEmpty()) {
			if(!user.verifyCFExistency(txtCodiceFiscaleProf.getText())) {
				String sql = "UPDATE users SET cf='" + txtCodiceFiscaleProf.getText() + "' WHERE id='" + user.getId() + "' AND psw='" + user.getPsw() + "'";
				if(!user.updateUser(sql)) {
					return;
				}
			}
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Errore nella modifica del profilo");
				alert.setHeaderText(null);
				alert.setContentText("Il Codice Fiscale inserito è già in uso!");
				alert.showAndWait();
				return;
			}
		}
		if(!txtCittaProf.getText().trim().isEmpty()) {
			String sql = "UPDATE users SET city='" + txtCittaProf.getText() + "' WHERE id='" + user.getId() + "' AND psw='" + user.getPsw() + "'";
			if(!user.updateUser(sql)) {
				return;
			}
		}
		if(!txtIndirizzoProf.getText().trim().isEmpty()) {
			String sql = "UPDATE users SET street='" + txtIndirizzoProf.getText() + "' WHERE id='" + user.getId() + "' AND psw='" + user.getPsw() + "'";
			if(!user.updateUser(sql)) {
				return;
			}
		}
		if(!txtNumeroProf.getText().trim().isEmpty()) {
			String sql = "UPDATE users SET street_number='" + txtNumeroProf.getText() + "' WHERE id='" + user.getId() + "' AND psw='" + user.getPsw() + "'";
			if(!user.updateUser(sql)) {
				return;
			}
		}
		if(!txtPasswordProf.getText().trim().isEmpty()) {
			if(user.verifyPasswordMatch(txtPasswordProf.getText(), txtConfermaPasswordProf.getText())) {
				if(!user.searchUser(user.getId(), txtPasswordProf.getText())) {
					String sql = "UPDATE users SET psw='" + Hash.getMd5(txtPasswordProf.getText()) + "' WHERE id='" + user.getId() + "' AND psw='" + user.getPsw() + "'";
					if(user.updateUser(sql)) {
						user.setPsw(Hash.getMd5(txtPasswordProf.getText()));
					}
					else {
						return;
					}
				}
				else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Errore nella modifica del profilo");
					alert.setHeaderText(null);
					alert.setContentText("La password inserita non è valida!");
					alert.showAndWait();
					return;
				}
			}
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Errore nella modifica del profilo");
				alert.setHeaderText(null);
				alert.setContentText("Le password nei due campi non corrispondono!");
				alert.showAndWait();
				return;
			}
		}
		clearProfFields();
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Modifiche effettuate");
		alert.setHeaderText(null);
		alert.setContentText("Il tuo profilo è stato modificato con successo!");
		alert.showAndWait();
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
