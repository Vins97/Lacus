package it.unicam.ids.lacus.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ShipmentController {

	//Label contenenti le info sulle spedizioni
	@FXML
	private Label lblDescription, lblRole, lblStatus, lblShippingDate, lblArrivalDate;

	//Stringa che tiene conto dell'id della spedizione (usato per effettuare le modifiche necessarie)
	private String shipmentid;

	void initData(String[] spedizione, int i) {
		shipmentid = spedizione[0];
		lblDescription.setText(spedizione[1]);
		lblRole.setText(spedizione[2]);
		lblStatus.setText(spedizione[3]);
		lblShippingDate.setText(spedizione[4]);
		lblArrivalDate.setText(spedizione[5]);
		//btnAccept.setId("btnAccept" + i);
		//btnRefuse.setId("btnRefuse" + i);
	}
}
