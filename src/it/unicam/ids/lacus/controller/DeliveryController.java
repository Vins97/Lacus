package it.unicam.ids.lacus.controller;

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

	private double x, y;

	@FXML
	public void accept() {
		Alerts alert = new Alerts();
		if(alert.printDeliveryPrompt()) {
			if(createCarrierStage()) {
				CarrierController.shipmentid = shipmentid;
			}
		}
	}

	private boolean createCarrierStage() {
		Parent carrier;
		try {
			carrier = FXMLLoader.load(getClass().getResource("../view/Carrier.fxml"));
			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setTitle("Consegna");
			stage.setScene(new Scene(carrier));
			stage.show();
			//fa traslare la finestra senza ridimensionarla
			carrier.setOnMousePressed(event -> {
				x = event.getSceneX();
				y = event.getSceneY();
			});
			carrier.setOnMouseDragged(event -> {
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

	void initData(String[] consegna) {
		shipmentid = consegna[0];
		lblCittaMittente.setText(consegna[1]);
		lblIndirizzoMittente.setText(consegna[2]);
		lblCittaDestinatario.setText(consegna[3]);
		lblIndirizzoDestinatario.setText(consegna[4]);
		btnAccept.setOnAction((event) -> accept());
	}
}
