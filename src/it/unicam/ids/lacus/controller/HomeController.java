package it.unicam.ids.lacus.controller;
import it.unicam.ids.lacus.Main;
import it.unicam.ids.lacus.database.DatabaseOperation;
import it.unicam.ids.lacus.model.Shipment;
import it.unicam.ids.lacus.model.Hash;
import it.unicam.ids.lacus.view.Alerts;
import javafx.scene.control.*;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import it.unicam.ids.lacus.model.Login;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unicam.ids.lacus.model.Users;

public class HomeController {

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
	private Pane pnlHome, pnlProfile, pnlDeliveries, pnlShipment, pnlRequests;

	//Pulsanti della dashboard
    @FXML
    private Button btnHome, btnProfile, btnDeliveries, btnShipment, btnRequests, btnLogout;

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

	//Vbox contenenti le info sulle spedizioni
    @FXML
    private VBox deliveriesList, shipmentsList, requestsList;

    //Pulsanti della schermata delle richieste
	@FXML
	public Button btnRefreshReq;

    private void initializeHomePanel(){
        lblTitle.setText("Riepilogo");
        pnlHome.toFront();
    }

    private void initializeProfilePanel(){
        lblTitle.setText("Modifica Profilo");
        pnlProfile.toFront();
    }

	private void initializeDeliveriesPanel() {
		lblTitle.setText("Lista Consegne");
		//Pulisce le consegne precedenti
		deliveriesList.getChildren().clear();
		//Ottiene il resultset con le consegne attive
		Shipment sr = new Shipment();
		Users user = new Users();
		ResultSet consegne = sr.deliveriesList(user.getCod(user.getId(), user.getPsw()));
		//Salvo il numero dei risultati per decidere il numero di cicli for
		DatabaseOperation dbop = new DatabaseOperation();
		int risultati = dbop.resultSetRows(consegne);
		//Crea tanti loader quante sono le righe di consegne da andare a creare
		FXMLLoader[] loaders = new FXMLLoader[risultati];
		try {
			//Punta alla prima delle consegne
			consegne.first();
			//L'array serve a contenere l'id della spedizione, l'indirizzo e la città di mittente e destinatario
			String[] consegna = new String[5];
			for(int i = 0; i < risultati; i++) {
				//Per ogni consegna crea una HBox a partire dall'FXML e gli assegna un nuovo oggetto controller ogni volta
				DeliveryController dc = new DeliveryController();
				loaders[i] = new FXMLLoader(getClass().getResource("../view/Delivery.fxml"));
				loaders[i].setController(dc);
				//Carica l'HBox ed il suo contenuto
				HBox box;
				try {
					box = loaders[i].load();
				}
				catch(IOException e) {
					Alerts alert = new Alerts();
					alert.printMissingFileMessage();
					return;
				}
				//Ottiene i dati della consegna dal resultset
				consegna[0] = consegne.getString("shipment_id");
				consegna[1] = consegne.getString("sender_city");
				consegna[2] = consegne.getString("sender_street") + " " + consegne.getString("sender_street_number");
				consegna[3] = consegne.getString("recipient_city");
				consegna[4] = consegne.getString("recipient_street") + " " + consegne.getString("recipient_street_number");
				//Scrive i dati della consegna nel controller
				dc.initData(consegna, i);
				//Dà alle HBox qualche effetto
				box.setOnMouseEntered(event -> box.setStyle("-fx-background-color : #0A0E3F"));
				box.setOnMouseExited(event -> box.setStyle("-fx-background-color : #02030A"));
				//Aggiunge la HBox alla finestra delle consegne
				deliveriesList.getChildren().add(box);
				consegne.next();
			}
		}
		catch (SQLException e) {
			Alerts alert = new Alerts();
			alert.printDatabaseConnectionError();
		}
		pnlDeliveries.toFront();
	}

	private void initializeShipmentPanel() {
		lblTitle.setText("Lista Spedizioni");
		Node[] nodes = new Node[5];
		for (int i = 0; i < nodes.length; i++) {
			try {
				final int j = i;
				nodes[i] = FXMLLoader.load(getClass().getResource("../view/Shipment.fxml"));
				//give the items some effect
				nodes[i].setOnMouseEntered(event -> nodes[j].setStyle("-fx-background-color : #0A0E3F"));
				nodes[i].setOnMouseExited(event -> nodes[j].setStyle("-fx-background-color : #02030A"));
				shipmentsList.getChildren().add(nodes[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		pnlShipment.toFront();
	}

	private void initializeRequestsPanel() {
		lblTitle.setText("Lista Richieste");
		//Pulisce le richieste precedenti
		requestsList.getChildren().clear();
		//Ottiene il resultset con le richieste di spedizione attive
		Shipment sr = new Shipment();
		Users user = new Users();
		ResultSet richieste = sr.shipmentRequests(user.getCod(user.getId(), user.getPsw()));
		//Salvo il numero dei risultati per decidere il numero di cicli for
		DatabaseOperation dbop = new DatabaseOperation();
		int risultati = dbop.resultSetRows(richieste);
		//Crea tanti loader quante sono le righe di spedizione da andare a creare
		FXMLLoader[] loaders = new FXMLLoader[risultati];
		try {
			//Punta alla prima delle richieste
			richieste.first();
			//L'array serve a contenere l'id della spedizione, la descrizione, il tipo di richiesta e il mittente
			String[] richiesta = new String[4];
			for(int i = 0; i < risultati; i++) {
				//Per ogni richiesta crea una HBox a partire dall'FXML e gli assegna un nuovo oggetto controller ogni volta
				RequestController rc = new RequestController();
				loaders[i] = new FXMLLoader(getClass().getResource("../view/Request.fxml"));
				loaders[i].setController(rc);
				//Carica l'HBox ed il suo contenuto
				HBox box;
				try {
					box = loaders[i].load();
				}
				catch(IOException e) {
					Alerts alert = new Alerts();
					alert.printMissingFileMessage();
					return;
				}
				//Ottiene i dati della richiesta dal resultset
				richiesta[0] = richieste.getString("shipment_id");
				richiesta[1] = richieste.getString("description");
				if(Integer.parseInt(richieste.getString("status")) == 1) {
					richiesta[2] = "Accettazione";
					richiesta[3] = richieste.getString("sender_id");
				}
				else {
					richiesta[2] = "Pagamento";
					richiesta[3] = richieste.getString("carrier_id");
				}
				//Scrive i dati della richiesta nel controller
				rc.initData(richiesta, i);
				//Dà alle HBox qualche effetto
				box.setOnMouseEntered(event -> box.setStyle("-fx-background-color : #0A0E3F"));
				box.setOnMouseExited(event -> box.setStyle("-fx-background-color : #02030A"));
				//Aggiunge la HBox alla finestra delle richieste
				requestsList.getChildren().add(box);
				richieste.next();
			}
		}
		catch (SQLException e) {
			Alerts alert = new Alerts();
			alert.printDatabaseConnectionError();
		}
		pnlRequests.toFront();
	}

	private void dashboardPanel() {
		lblTitle.setText("Riepilogo");
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
		if(event.getSource() == btnDeliveries) {
			initializeDeliveriesPanel();
		}
		if(event.getSource() == btnShipment) {
			initializeShipmentPanel();
		}
		if(event.getSource() == btnRequests || event.getSource() == btnRefreshReq) {
			initializeRequestsPanel();
		}
        if(event.getSource() == btnLogout){
        	Alerts alert = new Alerts();
        	if(alert.printLogoutPrompt()) {
				user.setActiveUser(null, null);
				loginPanel();
			}
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
			if(user.verifyNewCF(txtCodiceFiscaleProf.getText())) {
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
}
