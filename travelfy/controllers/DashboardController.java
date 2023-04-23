package travelfy.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import travelfy.models.User;

public class DashboardController {

    @FXML
    private Label attractionLabel;

    @FXML
    private Label dashboardLabel;

    @FXML
    private Label logoutLabel;

    @FXML
    private Label reservationLabel;
    
    public void initData(User loggedUser) {
		System.out.println(loggedUser.getId());
		
	}
	


}
