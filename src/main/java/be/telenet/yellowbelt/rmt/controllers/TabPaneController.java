package be.telenet.yellowbelt.rmt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by Jamy-Lee on 26/01/2017.
 */
@Controller
public class TabPaneController {


	@Autowired
	private SendTabController sendTabController;

	@Autowired
	private ReceiveTabController receiveTabController;

}