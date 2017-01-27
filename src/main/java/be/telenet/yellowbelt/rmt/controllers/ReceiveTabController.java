package be.telenet.yellowbelt.rmt.controllers;

import be.telenet.yellowbelt.rmt.components.custom.MessageComponent;
import be.telenet.yellowbelt.rmt.models.Header;
import be.telenet.yellowbelt.rmt.models.Message;
import be.telenet.yellowbelt.rmt.services.RabbitMQManagementToolService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.layout.Priority.ALWAYS;

/**
 * Created by Jerry-Lee on 26/01/2017.
 */
@Controller
public class ReceiveTabController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQManagementToolController.class);

	@Value("${queue.names}")
	private String[] queueNames;

	@Autowired
	private RabbitMQManagementToolService service;

	@FXML
	@Getter
	private Label queueLabel;

	@FXML
	@Getter
	private ComboBox<String> queueComboBox;

	@FXML
	@Getter
	private Button refreshButton;

	@FXML
	@Getter
	private VBox messagesVBox;

	public void initialize() {
		loadQueueComboBox();
	}

	private void loadQueueComboBox() {
		queueComboBox.setItems(FXCollections.observableArrayList(queueNames));
	}

	/**
	 * <p>It will call the {@link RabbitMQManagementToolService#receiveMessages(String)} method.</p>
	 * <p>This method will be called from the onAction of the {@link Button refreshButton}.</p>
	 */
	@FXML
	public void receiveMessages() {
		try {
			String queue = queueComboBox.getValue();
			List<org.apache.camel.Message> messages = service.receiveMessages(queue);
			this.messagesVBox.getChildren().clear();
			messages.forEach(this::logDebugMessageInfo);
			int count = 1;

			for (org.apache.camel.Message message : messages) {
				this.messagesVBox.getChildren().add(
					MessageComponent.createComponent(this.convertCamelMessageToRabbitMQMessage(message))
						.setMessageLabel(String.format("Message %d:", count))
						.setMessagesRemainingLabel(String.format("Messages remaining: %d", messages.size() - count)));
				count++;
			}
		} catch (Throwable t) {
			createAndShowExceptionDialog(t);
		}

	}

	private Message convertCamelMessageToRabbitMQMessage(org.apache.camel.Message message) {
		return new Message(message.getBody(String.class),
			this.convertCamelMessageHeadersToRabbitMQHeaders(message.getHeaders()));
	}

	private List<Header> convertCamelMessageHeadersToRabbitMQHeaders(Map<String, Object> camelMessageHeaders) {
		return camelMessageHeaders.entrySet().stream()
			.map(this::convertCamelMessageHeaderToRabbitMQHeader)
			.collect(Collectors.toList());
	}

	private Header convertCamelMessageHeaderToRabbitMQHeader(Entry<String, Object> camelMessageHeader) {
		return new Header(camelMessageHeader.getKey(), String.valueOf(camelMessageHeader.getValue()));
	}

	private void logDebugMessageInfo(org.apache.camel.Message message) {
		LOGGER.debug(message.getBody(String.class));
		message.getHeaders().entrySet().stream().filter(entry -> !entry.getKey().startsWith("rabbitmq."))
			.map(entry -> String.format("Header key: %s %n Header value: %s", entry.getKey(), entry.getValue()))
			.collect(Collectors.toList())
			.forEach(LOGGER::debug);
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
