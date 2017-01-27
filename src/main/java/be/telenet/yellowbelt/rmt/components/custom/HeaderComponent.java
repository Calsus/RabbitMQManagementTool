package be.telenet.yellowbelt.rmt.components.custom;

import be.telenet.yellowbelt.rmt.models.Header;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import lombok.Getter;

import java.io.IOException;

/**
 * FMXL Component to display Header Information.
 * <p>
 * Created by Jerry-Lee on 27/01/2017.
 */
public class HeaderComponent extends HBox {
	@FXML
	@Getter
	private Text keyText;

	@FXML
	@Getter
	private Text valueText;


	public HeaderComponent() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
			"/fxml/components/custom/Header.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.load();
	}

	/**
	 * Creates and instance of {@link HeaderComponent} with the data of the given message
	 *
	 * @param header An instance of {@link Header}
	 * @return an instance of {@link HeaderComponent}
	 * @throws IOException
	 */
	public static HeaderComponent createComponent(Header header) throws IOException {
		HeaderComponent component = new HeaderComponent();
		component.keyText.setText(header.getKey());
		component.valueText.setText(header.getValue());
		return component;
	}

}
