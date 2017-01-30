package be.telenet.yellowbelt.rmt.helpers;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.io.PrintWriter;
import java.io.StringWriter;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static javafx.scene.layout.Priority.ALWAYS;

/**
 * Created by Jamy-Lee on 30/01/2017.
 */
public class DialogHelper {
	/**
	 * Creates and show an Information Dialog that display the message to the user.
	 */
	public static void createAndShowSuccessDialog(String message) {
		Alert alert = new Alert(INFORMATION);
		alert.setTitle("Successful");
		alert.setHeaderText(null);
		alert.setContentText(message);

		alert.showAndWait();
	}

	/**
	 * Creates and show an Exception Dialog that display the error message and stacktrace to the user.
	 */
	public static void createAndShowExceptionDialog(Throwable e) {
		Alert alert = new Alert(ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(e.getMessage());

		// Create expandable Exception.
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
		GridPane.setVgrow(textArea, ALWAYS);
		GridPane.setHgrow(textArea, ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();
	}
}
