package it.unicam.ids.lacus.controller;

import it.unicam.ids.lacus.model.Shipment;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class DetailsController {
	@FXML
	public AnchorPane ancPnDetails;

	@FXML
	private TextField txtShipmentId, txtDescription, txtStatus, txtSenderId, txtSenderCity, txtSenderStreet, txtCarrierId, txtRevenue, txtShippingDate, txtArrivalDate, txtRecipientId, txtRecipientCity, txtRecipientStreet;

	//Pulsanti della schermata di pagamento
	@FXML
	private Button btnBackToShipments;

	static String shipmentid;

	@FXML
	public void initialize() {
		Shipment shipment = new Shipment();
		String[] details = shipment.getAllDetails(shipmentid);
		txtShipmentId.setText(details[0]);
		txtDescription.setText(details[1]);
		txtStatus.setText(shipment.setStatus(details[2]));
		txtSenderId.setText(details[3]);
		txtSenderCity.setText(details[4]);
		txtSenderStreet.setText(details[5]);
		txtCarrierId.setText(details[6]);
		txtRevenue.setText(details[7]);
		txtShippingDate.setText(details[8]);
		txtArrivalDate.setText(details[9]);
		txtRecipientId.setText(details[10]);
		txtRecipientCity.setText(details[11]);
		txtRecipientStreet.setText(details[12]);
		//Toglie il focus dalla prima casella di testo
		Platform.runLater( () -> ancPnDetails.requestFocus() );
	}

	@FXML
	private void close() {
		Stage stage = (Stage) btnBackToShipments.getScene().getWindow();
		stage.close();
	}
}
