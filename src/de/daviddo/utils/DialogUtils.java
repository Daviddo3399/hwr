package de.daviddo.utils;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

/**
 * 
 * @author	Daviddo3399
 */
public class DialogUtils {

	public static void showWarningDialog(String warning, String content) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Warnung!");
		alert.setHeaderText(warning);
		alert.setContentText(content);

		alert.showAndWait();
	}

	public static String showChoiceCharacterDialog() {
		ChoiceDialog<String> dialog = new ChoiceDialog<>("A", Utils.ALPHABET);
		dialog.setTitle("Buchstabe wählen");
		dialog.setHeaderText("Wählen Sie den eigentlich gezeichneten Buchstaben");
		dialog.setContentText("Richtiger Buchstabe:");


		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			return result.get();
		}
		return null;
	}

	public static boolean showConfirmationDialog(String titel, String header, String content, String buttonText) {
		final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.setTitle(titel);
		alert.setHeaderText(header);
		alert.setContentText(content);

		ButtonType buttonTypeOne = new ButtonType(buttonText);
		ButtonType buttonTypeCancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne){
			return true;
		} else {
			return false;
		}
	}

	public static void showExceptionDialog(final String hint, final Exception e) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Exception");
		alert.setHeaderText("");
		alert.setContentText(hint);

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		alert.getDialogPane().setExpandableContent(expContent);
		alert.showAndWait();
	}
}
