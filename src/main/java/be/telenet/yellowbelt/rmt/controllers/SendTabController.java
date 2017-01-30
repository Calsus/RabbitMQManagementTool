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
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.util.List;

import static be.telenet.yellowbelt.rmt.helpers.DialogHelper.createAndShowExceptionDialog;
import static be.telenet.yellowbelt.rmt.helpers.DialogHelper.createAndShowSuccessDialog;

/**
 * Created by Jerry-Lee on 26/01/2017.
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



}
