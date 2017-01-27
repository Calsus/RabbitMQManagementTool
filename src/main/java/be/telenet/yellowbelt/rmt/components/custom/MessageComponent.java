package be.telenet.yellowbelt.rmt.components.custom;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
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
	private Label messageRemainingLabel;

	public MessageComponent() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
			"/fxml/components/custom/Message.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.load();
	}
}
