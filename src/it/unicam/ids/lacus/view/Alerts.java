package it.unicam.ids.lacus.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
import javafx.stage.StageStyle;

import java.util.Optional;

public class Alerts {
	private double x, y;

	private void initAlertsStyle(Alert alert) {
		alert.setHeaderText(null);
		alert.initStyle(StageStyle.UNDECORATED);
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.setOnMousePressed(event -> {
			x = event.getSceneX();
			y = event.getSceneY();
		});
		dialogPane.setOnMouseDragged(event -> {
			alert.setX(event.getScreenX()-x);
			alert.setY(event.getScreenY()-y);
		});
		dialogPane.getStylesheets().add(getClass().getResource("Alerts.css").toExternalForm());
		dialogPane.getStyleClass().add("alert");
	}

	public void printDatabaseConnectionError() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("La tua operazione non è andata a buon fine a causa di un problema di connessione al Database!");
		alert.showAndWait();
	}

	public void printEmptyFieldsMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("Uno o più campi sono vuoti!");
		alert.showAndWait();
	}

	public void printInvalidCharactersMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("Uno o più campi contengono caratteri non validi!");
		alert.showAndWait();
	}

	public void printInvalidEmailDomainMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("Il dominio della mail non è valido!");
		alert.showAndWait();
	}

	public void	printNoPasswordMatchMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("Le password nei due campi non corrispondono!");
		alert.showAndWait();
	}

	public void printLoginErrorMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("Username o password errati!");
		alert.showAndWait();
	}

	public void printUsernameTakenMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("L'Username inserito è già in uso!");
		alert.showAndWait();
	}

	public void printCFTakenMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("Il Codice Fiscale inserito è già in uso!");
		alert.showAndWait();
	}

	public void	printInvalidPasswordMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("La password inserita non è valida!");
		alert.showAndWait();
	}

	public void printProfileEditedMessage() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		initAlertsStyle(alert);
		alert.setContentText("Il tuo profilo è stato modificato con successo!");
		alert.showAndWait();
	}

	public void printRegisteredUserMessage() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		initAlertsStyle(alert);
		alert.setContentText("La tua registrazione è avvenuta con successo!");
		alert.showAndWait();
	}

	public void printUsernameOrCFTakenMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("Il tuo Codice Fiscale o il tuo Username sono già stati registrati su un altro account!");
		alert.showAndWait();
	}

	public void printShipmentAddedMessage() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		initAlertsStyle(alert);
		alert.setContentText("La tua spedizione è stata creata con successo!\nAppena il destinatario accetterà la tua richiesta, la spedizione sarà aggiunta alla lista e potrà essere presa in carico!");
		alert.showAndWait();
	}

	public void printIdenticalIdsMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("Il codice del mittente non può essere uguale al codice del destinatario!");
		alert.showAndWait();
	}

	public void printUnknownIdMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("Il codice destinatario che hai inserito non esiste!");
		alert.showAndWait();
	}

	public void printUnknownCityMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("La città che hai inserito non esiste oppure non è disponibile per la spedizione!");
		alert.showAndWait();
	}

	public void printMissingFileMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("Nella directory del programma manca uno o più file!");
		alert.showAndWait();
	}

	public boolean printShipmentPrompt() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		initAlertsStyle(alert);
		alert.setContentText("Sei sicuro di voler confermare la spedizione?");
		Optional<ButtonType> result = alert.showAndWait();
		return result.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
	}

	public boolean printPaymentPrompt() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		initAlertsStyle(alert);
		alert.setContentText("Sei sicuro di voler procedere al pagamento della spedizione?");
		Optional<ButtonType> result = alert.showAndWait();
		return result.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
	}

	public boolean printRefusePrompt() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		initAlertsStyle(alert);
		alert.setContentText("Sei sicuro di voler rifiutare la spedizione?");
		Optional<ButtonType> result = alert.showAndWait();
		return result.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
	}

	public void printShipmentAcceptedMessage() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		initAlertsStyle(alert);
		alert.setContentText("La spedizione è stata confermata con successo!");
		alert.showAndWait();
	}

	public void printPaymentAcceptedMessage() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		initAlertsStyle(alert);
		alert.setContentText("Il pagamento della spedizione è stato accettato con successo!");
		alert.showAndWait();
	}

	public void printShipmentRefusedMessage() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		initAlertsStyle(alert);
		alert.setContentText("La spedizione è stata rifiutata correttamente!");
		alert.showAndWait();
	}

	public void printShipmentAlreadyAcceptedMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("La spedizione è già stata confermata!");
		alert.showAndWait();
	}

	public void printShipmentAlreadyRefusedMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("La spedizione è già stata rifiutata!");
		alert.showAndWait();
	}

	public boolean printDeliveryPrompt() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		initAlertsStyle(alert);
		alert.setContentText("Sei sicuro di volerti prendere a carico la consegna?");
		Optional<ButtonType> result = alert.showAndWait();
		return result.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
	}

	public void printDeliveryAcceptedMessage() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		initAlertsStyle(alert);
		alert.setContentText("La consegna è stata assegnata con successo!");
		alert.showAndWait();
	}

	public double printPaymentDeliveryDialog() {
		TextInputDialog dialog = new TextInputDialog("5.00");
		dialog.setContentText("Inserisci il compenso che desideri per la consegna (almeno 0.01): €");
		Optional<String> result = dialog.showAndWait();
		try {
			return result.map(Double::parseDouble).orElse(0.0);
		}
		catch(NumberFormatException e) {
			return -1.0;
		}
	}

	public void printDeliveryAlreadyTakenMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("La consegna è già stata assegnata ad un altro utente!");
		alert.showAndWait();
	}

	public boolean printLogoutPrompt() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		initAlertsStyle(alert);
		alert.setContentText("Sei sicuro di voler effettuare il logout?");
		Optional<ButtonType> result = alert.showAndWait();
		return result.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
	}
}
