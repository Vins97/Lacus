package it.unicam.ids.lacus.controller;

import it.unicam.ids.lacus.model.Shipment;
import it.unicam.ids.lacus.view.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

class ShipmentController {

	//Label contenenti le info sulle spedizioni
	@FXML
	private Label lblDescription, lblRole, lblStatus;

	//Pulsanti della singola spedizione
	@FXML
	private Button btnProgressShip, btnRemoveShip, btnViewDetailsShip;

	//Stringa che tiene conto dell'id della spedizione (usato per effettuare le modifiche necessarie)
	private String shipmentid;

	private double x, y;

	@FXML
	private void progress() {
		Alerts alert = new Alerts();
		Shipment shipment = new Shipment();
		//Quando la spedizione è stata ritirata, il corriere può far avanzare lo stato della spedizione
		if(lblStatus.getText().compareTo("In attesa di ritiro") == 0 && lblRole.getText().compareTo("Corriere") == 0) {
			if(alert.printShipmentStartedPrompt()) {
				shipment.inTransit(shipmentid);
				alert.printShipmentStartedMessage();
			}
		}
		//Quando la spedizione è stata consegnata, il destinatario può far avanzare lo stato della spedizione
		else if(lblStatus.getText().compareTo("In transito") == 0 && lblRole.getText().compareTo("Destinatario") == 0) {
			if(alert.printShipmentDeliveredPrompt()) {
				shipment.delivered(shipmentid);
				alert.printShipmentDeliveredMessage();
			}
		}
		//Negli altri casi, nessuno può far avanzare lo stato della spedizione
		else {
			alert.printInactiveButtonMessage();
		}
	}

	@FXML
	private void remove() {
		Alerts alert = new Alerts();
		Shipment shipment = new Shipment();
		//Quando la spedizione è stata annullata o consegnata, chiunque può decidere di eliminarne il record
		if(lblStatus.getText().compareTo("Annullata") == 0 || lblStatus.getText().compareTo("Consegnata") == 0) {
			if(alert.printDeleteShipmentPrompt()) {
				shipment.deleteShipment(shipmentid);
				alert.printShipmentDeletedMessage();
			}
		}
		//Il corriere non può mai annullare la spedizione
		else if(lblRole.getText().compareTo("Corriere") == 0) {
			alert.printCarrierAccessDeniedMessage();
		}
		//Quando la spedizione è stata pagata, nessuno può più annullarla
		else if(lblStatus.getText().compareTo("In attesa di ritiro") == 0 || lblStatus.getText().compareTo("In transito") == 0) {
			alert.printAccessDeniedMessage();
		}
		//Prima che la spedizione venga pagata, il mittente e il destinatario possono annullare
		else {
			if(alert.printCancelShipmentPrompt()) {
				shipment.cancelShipment(shipmentid);
				alert.printShipmentCancelledMessage();
			}
		}
	}

	@FXML
	private void viewDetails() {
		DetailsController.shipmentid = shipmentid;
		createDetailsStage();
	}

	private void createDetailsStage() {
		Parent details;
		try {
			details = FXMLLoader.load(getClass().getResource("../view/Details.fxml"));
			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setTitle("Dettagli Spedizione");
			stage.setScene(new Scene(details));
			stage.show();
			//fa traslare la finestra senza ridimensionarla
			details.setOnMousePressed(event -> {
				x = event.getSceneX();
				y = event.getSceneY();
			});
			details.setOnMouseDragged(event -> {
				stage.setX(event.getScreenX()-x);
				stage.setY(event.getScreenY()-y);
			});
		}
		catch(IOException e) {
			Alerts alert = new Alerts();
			alert.printMissingFileMessage();
		}
	}

	void initData(String[] spedizione) {
		Shipment shipment = new Shipment();
		shipmentid = spedizione[0];
		lblDescription.setText(spedizione[1]);
		lblRole.setText(spedizione[2]);
		lblStatus.setText(shipment.setStatus(spedizione[3]));
		btnProgressShip.setOnAction((event) -> progress());
		btnRemoveShip.setOnAction((event) -> remove());
		btnViewDetailsShip.setOnAction((event) -> viewDetails());
	}
}
