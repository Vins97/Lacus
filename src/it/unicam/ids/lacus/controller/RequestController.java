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

class RequestController {

	//Label contenenti le info sulle richieste
	@FXML
	private Label lblSpedizione, lblRichiesta, lblRichiedente;

	//Pulsanti della singola richiesta
	@FXML
	private Button btnAccept, btnRefuse;

	//Stringa che tiene conto dell'id della spedizione (usato per effettuare le modifiche necessarie)
	private String shipmentid;

	//Boolean che tiene conto se la spedizione è già stata accettata/rifiutata o meno
	private boolean accepted = false;
	private boolean refused = false;

	private double x, y;

	@FXML
	public void accept() {
		Alerts alert = new Alerts();
		if(accepted) {
			alert.printShipmentAlreadyAcceptedMessage();
		}
		else if(refused) {
			alert.printShipmentAlreadyRefusedMessage();
		}
		else {
			Shipment shipment = new Shipment();
			if (lblRichiesta.getText().compareTo("Accettazione") == 0) {
				if(alert.printShipmentPrompt()) {
					shipment.confirmShipment(shipmentid);
					accepted = true;
					alert.printShipmentAcceptedMessage();
				}
			}
			else {
				double payment = shipment.getPayment(shipmentid);
				if(alert.printPaymentPrompt(payment)) {
					if(createPaymentStage()) {
						PaymentController.shipmentid = shipmentid;
					}
				}
			}
		}
	}

	@FXML
	public void refuse() {
		Alerts alert = new Alerts();
		if(accepted) {
			alert.printShipmentAlreadyAcceptedMessage();
		}
		else if(refused) {
			alert.printShipmentAlreadyRefusedMessage();
		}
		else {
			Shipment shipment = new Shipment();
			if(lblRichiesta.getText().compareTo("Accettazione") == 0) {
				if(alert.printRefuseShipmentPrompt()) {
					shipment.cancelShipment(shipmentid);
					refused = true;
					alert.printShipmentRefusedMessage();
				}
			}
			else {
				if(alert.printRefusePaymentPrompt()) {
					shipment.refusePayment(shipmentid);
					alert.printPaymentRefusedMessage();
				}
			}
		}
	}

	private boolean createPaymentStage() {
		Parent payment;
		try {
			payment = FXMLLoader.load(getClass().getResource("../view/Payment.fxml"));
			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setTitle("Pagamento");
			stage.setScene(new Scene(payment));
			stage.show();
			//fa traslare la finestra senza ridimensionarla
			payment.setOnMousePressed(event -> {
				x = event.getSceneX();
				y = event.getSceneY();
			});
			payment.setOnMouseDragged(event -> {
				stage.setX(event.getScreenX()-x);
				stage.setY(event.getScreenY()-y);
			});
		}
		catch(IOException e) {
			Alerts alert = new Alerts();
			alert.printMissingFileMessage();
			return false;
		}
		return true;
	}

	void initData(String[] richiesta) {
		shipmentid = richiesta[0];
		lblSpedizione.setText(richiesta[1]);
		lblRichiesta.setText(richiesta[2]);
		lblRichiedente.setText(richiesta[3]);
		btnAccept.setOnAction((event) -> accept());
		btnRefuse.setOnAction((event) -> refuse());
	}
}
