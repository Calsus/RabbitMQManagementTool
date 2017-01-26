package be.telenet.yellowbelt.rmt;

import be.telenet.yellowbelt.rmt.controllers.RabbitMQManagementToolController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.stream.Stream;

@SpringBootApplication
public class RabbitMQManagementTool extends Application {
	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQManagementTool.class);
	private static final double WINDOW_WIDTH = 525D;
	private static final double WINDOW_HEIGHT = 600D;
	private static final String WINDOW_TITLE = "RabbitMQ Management Tool";

	private Parent root;

	private ConfigurableApplicationContext applicationContext;


	@Override
	public void start(Stage stage) throws Exception {
		//Setting window title and window width + height
		stage.setTitle(WINDOW_TITLE);
		stage.setWidth(WINDOW_WIDTH);
		stage.setHeight(WINDOW_HEIGHT);
		stage.setScene(new Scene(root));
		stage.setMaximized(true);
		stage.show();
	}

	public static void main(String[] args) throws InterruptedException {
		Application.launch();
	}

	@Override
	public void init() throws IOException {
		applicationContext = SpringApplication.run(RabbitMQManagementTool.class);
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource(RabbitMQManagementToolController.FXML_URL));
		loader.setControllerFactory(applicationContext::getBean);
		root = loader.load();
	}

	@Override
	public void stop() throws Exception {
		applicationContext.close();
	}
}
