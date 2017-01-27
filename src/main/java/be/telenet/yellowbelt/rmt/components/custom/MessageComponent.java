package be.telenet.yellowbelt.rmt.components.custom;

import be.telenet.yellowbelt.rmt.models.Header;
import be.telenet.yellowbelt.rmt.models.Message;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * FMXL Component to display Message Information.
 *
 * Created by Jamy-Lee on 27/01/2017.
 */
public class MessageComponent extends VBox {
	private String messagesRemaining = "Messages Remaining: 0";
	private String message = "Message 1";

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageComponent.class);

	@FXML
	@Getter
	private Label messageLabel;
	@FXML
	@Getter
	private VBox headersVBox;

	@FXML
	@Getter
	private TextArea contentTextArea;

	@FXML
	@Getter
	private Label messagesRemainingLabel;

	public MessageComponent() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
			"/fxml/components/custom/Message.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.load();
	}

	/**
	 * Creates and instance of {@link MessageComponent} with the data of the given message
	 *
	 * @param message An instance of {@link Message}
	 * @return an instance of {@link MessageComponent}
	 * @throws IOException
	 */
	public static MessageComponent createComponent(Message message) throws IOException {
		MessageComponent component = new MessageComponent();
		component.getContentTextArea().setText(message.getContent());

		for (Header header : message.getHeaders().stream()
			.filter(header -> !header.getKey().startsWith("rabbitmq."))
			.collect(Collectors.toList())) {
			component.getHeadersVBox().getChildren().add(HeaderComponent.createComponent(header));
		}
		return component;
	}

	public MessageComponent setMessageLabel(String messagel) {
		this.getMessageLabel().setText(message);
		return this;
	}

	public MessageComponent setMessagesRemainingLabel(String messagesRemaining) {
		this.getMessagesRemainingLabel().setText(messagesRemaining);
		return this;
	}
}
