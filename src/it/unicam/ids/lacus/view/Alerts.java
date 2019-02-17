package it.unicam.ids.lacus.view;

import javafx.scene.control.Alert;

public class Alerts {
	public void printDatabaseConnectionError() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Errore di connessione al Database");
		alert.setHeaderText(null);
		alert.setContentText("La tua operazione non è andata a buon fine a causa di un problema di connessione al Database!");
		alert.showAndWait();
	}

	public void printEmptyFieldsMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Errore di inserimento");
		alert.setHeaderText(null);
		alert.setContentText("Uno o più campi sono vuoti!");
		alert.showAndWait();
	}

	public void printInvalidCharactersMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Errore di inserimento");
		alert.setHeaderText(null);
		alert.setContentText("Uno o più campi contengono caratteri non validi!");
		alert.showAndWait();
	}

	public void printInvalidEmailDomainMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Errore di inserimento");
		alert.setHeaderText(null);
		alert.setContentText("Il dominio della mail non è valido!");
		alert.showAndWait();
	}

	public void	printNoPasswordMatchMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Errore di inserimento");
		alert.setHeaderText(null);
		alert.setContentText("Le password nei due campi non corrispondono!");
		alert.showAndWait();
	}

	public void printNotAlphanumericMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Errore di login");
		alert.setHeaderText(null);
		alert.setContentText("I campi ammettono solo caratteri alfanumerici!");
		alert.showAndWait();
	}

	public void printLoginErrorMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Errore di login");
		alert.setHeaderText(null);
		alert.setContentText("Username o password errati!");
		alert.showAndWait();
	}

	public void printUsernameTakenMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Errore nella modifica del profilo");
		alert.setHeaderText(null);
		alert.setContentText("L'Username inserito è già in uso!");
		alert.showAndWait();
	}

	public void printCFTakenMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Errore nella modifica del profilo");
		alert.setHeaderText(null);
		alert.setContentText("Il Codice Fiscale inserito è già in uso!");
		alert.showAndWait();
	}

	public void	printInvalidPasswordMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Errore nella modifica del profilo");
		alert.setHeaderText(null);
		alert.setContentText("La password inserita non è valida!");
		alert.showAndWait();
	}

	public void printProfileEditedMessage() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Modifiche effettuate");
		alert.setHeaderText(null);
		alert.setContentText("Il tuo profilo è stato modificato con successo!");
		alert.showAndWait();
	}

	public void printRegisteredUserMessage() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Utente Registrato");
		alert.setHeaderText(null);
		alert.setContentText("La tua registrazione è avvenuta con successo!");
		alert.showAndWait();
	}

	public void printUsernameOrCFTakenMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Errore di registrazione");
		alert.setHeaderText(null);
		alert.setContentText("Il tuo Codice Fiscale o il tuo Username sono già stati registrati su un altro account!");
		alert.showAndWait();
	}

	public void printShipmentAddedMessage() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Spedizione creata");
		alert.setHeaderText(null);
		alert.setContentText("La tua spedizione è stata creata con successo!\nAppena il destinatario accetterà la tua richiesta, la spedizione sarà aggiunta alla lista e potrà essere presa in carico!");
		alert.showAndWait();
	}

	public void printIdenticalIdsMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Codice destinatario errato");
		alert.setHeaderText(null);
		alert.setContentText("Il codice del mittente non può essere uguale al codice del destinatario!");
		alert.showAndWait();
	}

	public void printUnknownIdMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Codice destinatario sconosciuto");
		alert.setHeaderText(null);
		alert.setContentText("Il codice destinatario che hai inserito non esiste!");
		alert.showAndWait();
	}

	public void printUnknownCityMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Città non disponibile");
		alert.setHeaderText(null);
		alert.setContentText("La città che hai inserito non esiste oppure non è disponibile per la spedizione!");
		alert.showAndWait();
	}

	public void printMissingFileMessage() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("File Mancante");
		alert.setHeaderText(null);
		alert.setContentText("Nella directory del programma manca uno o più file!");
		alert.showAndWait();
	}
}
