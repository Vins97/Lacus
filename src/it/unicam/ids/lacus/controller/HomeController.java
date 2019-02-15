package it.unicam.ids.lacus.controller;
import it.unicam.ids.lacus.Main;
import it.unicam.ids.lacus.model.Shipment;
import it.unicam.ids.lacus.model.Hash;
import it.unicam.ids.lacus.view.Alerts;
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

import it.unicam.ids.lacus.model.Users;

public class HomeController implements Initializable {

	//Schermate principali del programma
    @FXML
    private AnchorPane ancPnRegister, ancPnDash, ancPnLogin, ancPnNewShipment;

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

    //Titolo della dashboard
	@FXML
	private Label lblTitle;

    //Pannelli della dashboard
	@FXML
	private Pane pnlHome, pnlProfile, pnlShipping;

	//Pulsanti della dashboard
    @FXML
    private Button btnHome, btnProfile, btnShipping, btnLogout;

    //Contatori della shermata home
	@FXML
	private Label lblWaitingHome, lblDeliveringHome, lblDeliveredHome;

	//Caselle di testo della schermata home
	@FXML
	private TextField txtCodBoxHome;

	//Pulsanti della schermata home
	@FXML
	private Button btnCreaSpedizione;

	//Caselle di testo della schermata di creazione di una nuova spedizione
	@FXML
	private TextField txtDescrizioneShip, txtCodiceMittenteSped, txtCittaMittenteSped, txtIndirizzoMittenteSped, txtNumeroMittenteSped, txtCodiceDestinatarioSped, txtCittaDestinatarioSped, txtIndirizzoDestinatarioSped, txtNumeroDestinatarioSped;

	//Pulsanti della schermata di creazione di una nuova spedizione
	@FXML
	private Button btnCreaSpedizioneSped, btnBackToDash;

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

   private void dashboardPanel() {
    	Login login = new Login();
    	if(login.userLogin(txtUsername.getText(), txtPassword.getText())){
    		Users user = new Users();
    		lblWaitingHome.setText("1");
			lblDeliveringHome.setText("2");
			lblDeliveredHome.setText("3");
			txtCodBoxHome.setText(Integer.toString(user.getCod(Hash.getMd5(txtUsername.getText()), Hash.getMd5(txtPassword.getText()))));
			dashPanel();
    	}
    }

	private void loginPanel(){
		ancPnLogin.toFront();
	}
	private void registerPanel() {
		ancPnRegister.toFront();
	}
	private void dashPanel() {
		ancPnDash.toFront();
	}
	private void newShipmentPanel() {
    	ancPnNewShipment.toFront();
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
    void handleClicks(ActionEvent event) {
    	Users user = new Users();
        if(event.getSource() == btnLogin){
            dashboardPanel();
			clearLoginFields();
        }
        if(event.getSource() == btnRegister){
        	clearLoginFields();
            registerPanel();
        }
        if(event.getSource() == btnRegisterReg){
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
		if(event.getSource() == btnCreaSpedizione) {
			txtCodiceMittenteSped.setText(Integer.toString(user.getCod(user.getId(), user.getPsw())));
			newShipmentPanel();
		}
		if(event.getSource() == btnCreaSpedizioneSped){
			Shipment shipment = new Shipment();
			if(shipment.addShipment(txtDescrizioneShip.getText(), txtCodiceMittenteSped.getText(), txtCittaMittenteSped.getText(), txtIndirizzoMittenteSped.getText(), txtNumeroMittenteSped.getText(), txtCodiceDestinatarioSped.getText(), txtCittaDestinatarioSped.getText(), txtIndirizzoDestinatarioSped.getText(), txtNumeroDestinatarioSped.getText())) {
				dashPanel();
				clearNewShipmentFields();
			}
		}
		if(event.getSource() == btnBackToDash){
			dashPanel();
			clearNewShipmentFields();
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

	private void clearNewShipmentFields() {
    	txtDescrizioneShip.clear();
    	txtCodiceMittenteSped.clear();
    	txtCittaMittenteSped.clear();
    	txtIndirizzoMittenteSped.clear();
    	txtNumeroMittenteSped.clear();
    	txtCodiceDestinatarioSped.clear();
    	txtCittaDestinatarioSped.clear();
    	txtIndirizzoDestinatarioSped.clear();
    	txtNumeroDestinatarioSped.clear();
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

	private String getUserCod(String id, String psw) {
    	Users user = new Users();
		return Integer.toString(user.getCod(Hash.getMd5(id), Hash.getMd5(psw)));
	}

	private void editProfile() {
		Users user = new Users();
		Alerts alert = new Alerts();
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
				alert.printUsernameTakenMessage();
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
				alert.printCFTakenMessage();
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
					alert.printInvalidPasswordMessage();
					return;
				}
			}
			else {
				alert.printNoPasswordMatchMessage();
				return;
			}
		}
		clearProfFields();
		alert.printProfileEditedMessage();
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
