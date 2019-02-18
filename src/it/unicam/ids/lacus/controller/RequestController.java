package it.unicam.ids.lacus.controller;

import it.unicam.ids.lacus.model.Shipment;
import it.unicam.ids.lacus.view.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RequestController {

	//Label contenenti le info sulle spedizioni
	@FXML
	private Label lblSpedizione, lblRichiesta, lblRichiedente;

	//Pulsanti della singola spedizione
	@FXML
	private Button btnAccept, btnRefuse;

	//Stringa che tiene conto dell'id della spedizione (usato per effettuare le modifiche necessarie)
	private String shipmentid;

	//Boolean che tiene conto se la spedizione è già stata accettata/rifiutata o meno
	private boolean accepted = false;
	private boolean refused = false;

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
				/*if(alert.printPaymentPrompt()) {
					accepted = true;
					alert.printPaymentAcceptedMessage();
				}*/
			}
		}
	}

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
			if(alert.printRefusePrompt()) {
				shipment.refuseShipment(shipmentid);
				refused = true;
				alert.printShipmentRefusedMessage();
			}
		}
	}

	void initData(String[] richiesta, int i) {
		shipmentid = richiesta[0];
		lblSpedizione.setText(richiesta[1]);
		lblRichiesta.setText(richiesta[2]);
		lblRichiedente.setText(richiesta[3]);
		btnAccept.setId("btnAccept" + i);
		btnRefuse.setId("btnRefuse" + i);
	}
}
