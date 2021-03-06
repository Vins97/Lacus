package it.unicam.ids.lacus.controller;

import it.unicam.ids.lacus.model.Shipment;
import it.unicam.ids.lacus.model.StringChecker;
import it.unicam.ids.lacus.model.Users;
import it.unicam.ids.lacus.view.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Date;

public class CarrierController {
	public AnchorPane ancPnCarrier;

	//Pulsanti della schermata di pagamento
	@FXML
	private Button btnCarrierConfirm, btnCancelCarrier;

	@FXML
	private TextField txtRevenueCarrier, txtIBANCarrier;

	@FXML
	private DatePicker dateCarryCarrier, dateArrivalCarrier;

	static String shipmentid;

	@FXML
	private void confirm() {
		StringChecker sc = new StringChecker();
		Alerts alert = new Alerts();
		if(sc.revenueChecker(txtRevenueCarrier.getText())) {
			switch(sc.characterAndNumberChecker(txtIBANCarrier.getText())) {
				case 0: {
					alert.printEmptyFieldsMessage();
					return;
				}
				case -1: {
					alert.printInvalidCharactersMessage();
					return;
				}
				case 1: {
					if(sc.dateChecker(dateCarryCarrier.getValue()) && sc.dateChecker(dateArrivalCarrier.getValue())) {
						if(alert.printDeliveryPrompt()) {
							Date datecarry = Date.valueOf(dateCarryCarrier.getValue());
							Date dateship = Date.valueOf(dateArrivalCarrier.getValue());
							Shipment shipment = new Shipment();
							Users user = new Users();
							if(shipment.deliveryAvailable(shipmentid)) {
								shipment.confirmDelivery(shipmentid, user.getUserid(), txtIBANCarrier.getText(), Double.parseDouble(txtRevenueCarrier.getText()), datecarry, dateship);
								alert.printDeliveryAcceptedMessage();
								close();
							}
							else {
								alert.printDeliveryAlreadyAcceptedMessage();
							}
						}
					}
					else {
						alert.printInvalidDateMessage();
					}
				}
			}
		}
		else {
			alert.printInvalidRevenueMessage();
		}
	}

	@FXML
	private void close() {
		Stage stage = (Stage) btnCancelCarrier.getScene().getWindow();
		stage.close();
	}
}