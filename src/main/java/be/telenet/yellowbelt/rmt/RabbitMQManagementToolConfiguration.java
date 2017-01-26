package be.telenet.yellowbelt.rmt;

import be.telenet.yellowbelt.rmt.controllers.RabbitMQManagementToolController;
import javafx.fxml.FXMLLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Default configuration for the RabbitMQ Management Tool
 * Created by Jerry-Lee on 23/01/2017.
 */
@Configuration
public class RabbitMQManagementToolConfiguration {

	@Bean
	public RabbitMQManagementToolController mainPaneController() throws IOException {
		return loadController(RabbitMQManagementToolController.VIEW);
	}

	private <T extends RabbitMQManagementToolController> T loadController(String url) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.load(this.getClass().getResource(url).openStream());
		return loader.getController();

	}
}
