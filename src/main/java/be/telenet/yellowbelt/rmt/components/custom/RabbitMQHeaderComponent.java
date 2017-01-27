package be.telenet.yellowbelt.rmt.components.custom;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import lombok.Getter;

import java.io.IOException;

/**
 * Created by Jamy-Lee on 27/01/2017.
 */
public class RabbitMQHeaderComponent extends HBox {
	@FXML
	@Getter
	private Text keyText;

	@FXML
	@Getter
	private Text valueText;


	public RabbitMQHeaderComponent() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
			"/fxml/components/custom/RabbitMQHeader.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.load();
	}

}
