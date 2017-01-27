package be.telenet.yellowbelt.rmt.controllers;

import be.telenet.yellowbelt.rmt.eventhandlers.KeyCellOnEditEventHandler;
import be.telenet.yellowbelt.rmt.eventhandlers.ValueCellOnEditEventHandler;
import be.telenet.yellowbelt.rmt.models.Header;
import be.telenet.yellowbelt.rmt.services.RabbitMQManagementToolService;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static javafx.scene.layout.Priority.ALWAYS;

/**
 * Created by Jamy-Lee on 26/01/2017.
 */
@Controller
public class SendTabController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQManagementToolController.class);

	@Value("${queue.names}")
	private String[] queueNames;

	@Autowired
	private RabbitMQManagementToolService service;

	@FXML
	@Getter
	private Tab sendTab;

	@FXML
	@Getter
	private AnchorPane sendPane;

	@FXML
	@Getter
	private Label headerLabel;

	@FXML
	@Getter
	private TableView<Header> headerTable;

	@FXML
	@Getter
	private TableColumn keyColumn;

	@FXML
	@Getter
	private TableColumn valueColumn;

	@FXML
	@Getter
	private Label contentLabel;

	@FXML
	@Getter
	private TextArea contentTextArea;

	@FXML
	@Getter
	private Label queueLabel;

	@FXML
	@Getter
	private ComboBox<String> queueComboBox;

	@FXML
	@Getter
	private Button sendButton;


	public void initialize() {
		initTableData();
		initColumns();
		loadQueueComboBox();
	}

	/**
	 * <p>It will call the {@link RabbitMQManagementToolService#sendMessage(List, String, String)} method.</p>
	 * <p>This method will be called from the onAction of the {@link Button sendButton}.</p>
	 */
	@FXML
	public void send() {
		try {
			String queue = queueComboBox.getValue();
			service.sendMessage(headerTable.getItems(), contentTextArea.getText(), queue);
			createAndShowSuccessDialog("The message is successfully send to the queue: " + queue);
		} catch (Throwable t) {
			createAndShowExceptionDialog(t);
		}

	}

	/**
	 * Initialize the table columns.
	 */
	private void initColumns() {
		initColumn(keyColumn, "key", new KeyCellOnEditEventHandler());
		initColumn(valueColumn, "value", new ValueCellOnEditEventHandler());
	}

	/**
	 * Initialize the table columns.
	 */
	private void initColumn(TableColumn column, String property, EventHandler eventHandler) {
		//Setting the CellValueFactory to the property key so that any changes will be put in this property.
		column.setCellValueFactory(new PropertyValueFactory<Header, String>(property));

		//Setting the CellFactory to a TextFieldTableCellFactory so that we can edit the cell.
		column.setCellFactory(TextFieldTableCell.forTableColumn());

		//Setting the action that has to be done when we edit the cell
		column.setOnEditCommit(eventHandler);
	}

	/**
	 * Initialize the table with empty Headers.
	 */
	private void initTableData() {
		headerTable.setItems(FXCollections.observableArrayList(
			new Header(),
			new Header(),
			new Header(),
			new Header(),
			new Header(),
			new Header(),
			new Header(),
			new Header(),
			new Header(),
			new Header(),
			new Header(),
			new Header()
		));
	}

	/**
	 * Populate the queue combobox with the queuenames
	 */
	private void loadQueueComboBox() {
		queueComboBox.setItems(FXCollections.observableArrayList(queueNames));
	}

	public void enableButton() {
		sendButton.setDisable(false);
	}

	/**
	 * Creates and show an Information Dialog that display the message to the user.
	 */
	private void createAndShowSuccessDialog(String message) {
		Alert alert = new Alert(INFORMATION);
		alert.setTitle("Successful");
		alert.setHeaderText(null);
		alert.setContentText(message);

		alert.showAndWait();
	}

	/**
	 * Creates and show an Exception Dialog that display the error message and stacktrace to the user.
	 */
	private void createAndShowExceptionDialog(Throwable e) {
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
