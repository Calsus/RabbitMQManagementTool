package be.telenet.yellowbelt.rmt.controllers;

import be.telenet.yellowbelt.rmt.eventhandlers.KeyCellOnEditEventHandler;
import be.telenet.yellowbelt.rmt.eventhandlers.ValueCellOnEditEventHandler;
import be.telenet.yellowbelt.rmt.models.RabbitMQHeader;
import be.telenet.yellowbelt.rmt.services.RabbitMQManagementToolService;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static javafx.scene.layout.Priority.ALWAYS;

@Controller
public class RabbitMQManagementToolController {
	public static final String FXML_URL = "/fxml/RabbitMQManagementTool.fxml";

	@Autowired
	private TabPaneController tabPaneController;
}
