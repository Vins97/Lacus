package it.unicam.ids.lacus.controller;

import it.unicam.ids.lacus.model.Shipment;
import it.unicam.ids.lacus.view.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Scanner;

public class PaymentController {
	public AnchorPane ancPnPayment;

	//Pulsanti della schermata di pagamento
	@FXML
	private Button btnPaymentPay, btnCancelPayment;

	static String shipmentid;

	@FXML
	private void pay() {
		Alerts alert = new Alerts();
		if(alert.printPaymentConfirmationPrompt()) {
			if(fakePayment()) {
				Shipment shipment = new Shipment();
				shipment.confirmPayment(shipmentid);
				alert.printPaymentAcceptedMessage();
				close();
			}
			else {
				alert.printPaymentRefusedMessage();
				close();
			}
		}
	}

	@FXML
	private void close() {
		Stage stage = (Stage) btnCancelPayment.getScene().getWindow();
		stage.close();
	}

	private boolean fakePayment() {
		Scanner in = new Scanner(System.in);
		return Integer.parseInt(in.next()) == 1;
	}
}