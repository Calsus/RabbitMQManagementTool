package be.telenet.yellowbelt.rmt;

import be.telenet.yellowbelt.rmt.controllers.RabbitMQManagementToolController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.stream.Stream;

@SpringBootApplication
public class RabbitMQManagementTool extends Application {
	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQManagementTool.class);
	private static String[] args;

	@Override
	public void start(Stage stage) throws Exception {
		// Bootstrap Spring context here.
		ApplicationContext context = SpringApplication.run(RabbitMQManagementTool.class, args);

		String[] queueNames = context.getEnvironment().getProperty("queue.names", String[].class);
		logDebugQueueNames(queueNames);
		// Create a Scene
		RabbitMQManagementToolController rabbitMQManagementToolController = context.getBean(RabbitMQManagementToolController.class);
		rabbitMQManagementToolController.loadQueueComboBox(queueNames);
		Scene scene = new Scene(rabbitMQManagementToolController.getRootPane());

		//Setting window title and window width + height
		stage.setTitle("RabbitMQ Management Tool");
		stage.setWidth(485D);
		stage.setHeight(575D);
		stage.setScene(scene);
		stage.show();
	}

	private void logDebugQueueNames(String[] queueNames) {
		Stream.of(queueNames).forEach(LOGGER::debug);
	}

	public static void main(String[] args) {
		RabbitMQManagementTool.args = args;
		launch(args);
	}
}
