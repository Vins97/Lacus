package it.unicam.ids.lacus.view;

import javafx.scene.control.*;
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

	public void printDateErrorMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("C'è un errore nella data di ritiro o di consegna di questa spedizione!");
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

	public void printInvalidCFMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("Il codice fiscale non è valido!");
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

	public boolean printPaymentPrompt(double payment) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		initAlertsStyle(alert);
		alert.setContentText("Il costo della spedizione è di " + payment + "€. Sei sicuro di voler procedere al pagamento?");
		Optional<ButtonType> result = alert.showAndWait();
		return result.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
	}

	public boolean printPaymentConfirmationPrompt() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		initAlertsStyle(alert);
		alert.setContentText("Desideri confermare il pagamento?");
		Optional<ButtonType> result = alert.showAndWait();
		return result.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
	}

	public boolean printRefuseShipmentPrompt() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		initAlertsStyle(alert);
		alert.setContentText("Sei sicuro di voler rifiutare la spedizione?");
		Optional<ButtonType> result = alert.showAndWait();
		return result.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
	}

	public boolean printRefusePaymentPrompt() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		initAlertsStyle(alert);
		alert.setContentText("Sei sicuro di voler rifiutare l'offerta di pagamento?\nLa tua spedizione verrà aggiunta nuovamente alla lista delle consegne.");
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

	public void printPaymentOfferRefusedMessage() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		initAlertsStyle(alert);
		alert.setContentText("L'offerta di pagamento è stata rifiutata con successo!");
		alert.showAndWait();
	}

	public void printPaymentRefusedMessage() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		initAlertsStyle(alert);
		alert.setContentText("Il pagamento della spedizione è stato rifiutato! Riprova più tardi.");
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

	public void printInvalidRevenueMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("Il compenso inserito non è valido!");
		alert.showAndWait();
	}

	public void printInvalidDateMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("La data inserita non è valida!");
		alert.showAndWait();
	}

	public void printDeliveryAcceptedMessage() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		initAlertsStyle(alert);
		alert.setContentText("La consegna è stata assegnata con successo!");
		alert.showAndWait();
	}

	public void printDeliveryAlreadyAcceptedMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("La consegna è stata già assegnata a qualcun altro!");
		alert.showAndWait();
	}

	public void printPaymentOfferAlreadyAcceptedMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("La richiesta è già stata accettata!");
		alert.showAndWait();
	}

	public void printPaymentOfferAlreadyRefusedMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("La richiesta è già stata rifiutata!");
		alert.showAndWait();
	}

	public boolean printShipmentStartedPrompt() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		initAlertsStyle(alert);
		alert.setContentText("Sei sicuro di voler segnalare la spedizione come \"In transito\"?");
		Optional<ButtonType> result = alert.showAndWait();
		return result.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
	}

	public void printShipmentStartedMessage() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		initAlertsStyle(alert);
		alert.setContentText("La spedizione è stata segnalata come \"In transito\"!");
		alert.showAndWait();
	}

	public boolean printShipmentDeliveredPrompt() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		initAlertsStyle(alert);
		alert.setContentText("Sei sicuro di voler segnalare la spedizione come \"Consegnata\"?");
		Optional<ButtonType> result = alert.showAndWait();
		return result.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
	}

	public void printShipmentDeliveredMessage() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		initAlertsStyle(alert);
		alert.setContentText("La spedizione è stata segnalata come \"Consegnata\"!");
		alert.showAndWait();
	}

	public boolean printDeleteShipmentPrompt() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		initAlertsStyle(alert);
		alert.setContentText("Sei sicuro di voler eliminare la spedizione?");
		Optional<ButtonType> result = alert.showAndWait();
		return result.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
	}

	public void printShipmentDeletedMessage() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		initAlertsStyle(alert);
		alert.setContentText("La spedizione è stata eliminata!");
		alert.showAndWait();
	}

	public boolean printCancelShipmentPrompt() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		initAlertsStyle(alert);
		alert.setContentText("Sei sicuro di voler annullare la spedizione?");
		Optional<ButtonType> result = alert.showAndWait();
		return result.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
	}

	public void printShipmentCancelledMessage() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		initAlertsStyle(alert);
		alert.setContentText("La spedizione è stata annullata!");
		alert.showAndWait();
	}

	public void printCarrierAccessDeniedMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("Il corriere non può annullare la spedizione!");
		alert.showAndWait();
	}

	public void printAccessDeniedMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("La spedizione non può più essere annullata dopo il pagamento!");
		alert.showAndWait();
	}

	public void printInactiveButtonMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		initAlertsStyle(alert);
		alert.setContentText("Non puoi eseguire questa operazione adesso!");
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
