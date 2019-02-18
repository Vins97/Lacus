package it.unicam.ids.lacus.controller;

import it.unicam.ids.lacus.model.Shipment;
import it.unicam.ids.lacus.model.Users;
import it.unicam.ids.lacus.view.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DeliveryController {

	//Label contenenti le info sulle consegne
	@FXML
	private Label lblIndirizzoMittente, lblCittaMittente, lblIndirizzoDestinatario, lblCittaDestinatario;

	//Pulsanti della singola consegna
	@FXML
	private Button btnAccept;

	//Stringa che tiene conto dell'id della spedizione (usato per effettuare le modifiche necessarie)
	private String shipmentid;

	//Boolean che tiene conto se la consegna è già stata assegnata o meno
	private boolean taken = false;

	public void accept() {
		Alerts alert = new Alerts();
		if(taken) {
			alert.printDeliveryAlreadyTakenMessage();
		}
		else {
			if(alert.printDeliveryPrompt()) {
				double payment;
				do {
					payment = alert.printPaymentDeliveryDialog();
				}
				while(payment == -1.0);
				if(payment > 0.00) {
					Shipment shipment = new Shipment();
					Users user = new Users();
					shipment.confirmDelivery(shipmentid, user.getCod(user.getId(), user.getPsw()), payment);
					taken = true;
					alert.printDeliveryAcceptedMessage();
				}
			}
		}
	}

	void initData(String[] consegna, int i) {
		shipmentid = consegna[0];
		lblCittaMittente.setText(consegna[1]);
		lblIndirizzoMittente.setText(consegna[2]);
		lblCittaDestinatario.setText(consegna[3]);
		lblIndirizzoDestinatario.setText(consegna[4]);
		btnAccept.setId("btnAccept" + i);
	}
}
