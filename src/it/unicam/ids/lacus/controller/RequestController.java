package it.unicam.ids.lacus.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RequestController {
	//Label contenenti le info sulle spedizioni
	@FXML
	private Label lblSpedizione, lblRichiesta, lblRichiedente;

	//Pulsanti della singola spedizione
	@FXML
	public Button btnAccept, btnRefuse;

	//Stringa che tiene conto dell'id della spedizione (usato per effettuare le modifiche necessarie)
	private String shipmentid;

	public void accept() {
		System.out.println(shipmentid);
	}

	void initData(String[] richiesta) {
		shipmentid = richiesta[0];
		lblSpedizione.setText(richiesta[1]);
		lblRichiesta.setText(richiesta[2]);
		lblRichiedente.setText(richiesta[3]);
	}
}
