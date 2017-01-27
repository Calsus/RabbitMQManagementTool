package be.telenet.yellowbelt.rmt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RabbitMQManagementToolController {
	public static final String FXML_URL = "/fxml/RabbitMQManagementTool.fxml";

	@Autowired
	private TabPaneController tabPaneController;
}
