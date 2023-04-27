package travelfy.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import travelfy.dao.UserDAOImpl;
import travelfy.models.User;
import javafx.scene.control.Button;

public class RegisterController {

	@FXML
	private ToggleGroup UserType;

	@FXML
	private TextField emailTextField;

	@FXML
	private Label information1Label;

	@FXML
	private TextField information1TextField;

	@FXML
	private Label information2Label;

	@FXML
	private TextField information2TextField;

	@FXML
	private PasswordField passwordTextField;

	@FXML
	private TextField phoneTextField;

	@FXML
	private ToggleButton travellerButton;

	@FXML
	private ToggleButton vendorButton;
	
	@FXML
	private Button createAccountButton;
	
	@FXML
	private Button backButton;
	

	static public final String defaultButtonStyle = "-fx-background-color: white; -fx-border-radius: 8px; -fx-background-radius: 8px;";
	static public final String selectedButtonStyle = defaultButtonStyle + " -fx-border-color: #8bb249; -fx-border-width: 2px; ";
	    
    public void initialize() {
    	information1Label.setText("");
    	information2Label.setText("");
    	information1TextField.setVisible(false);
    	information2TextField.setVisible(false);
    }
    
    public void navigate(User loggedUser, ActionEvent e) {
		try {
    	// Instantiate the FXMLLoader object for loading the UI 
    	FXMLLoader loader = new FXMLLoader();
    	// specify the file location
    	loader.setLocation(getClass().getResource("/travelfy/views/Dashboard.fxml"));
		// the object representing the root node of the scene; load the UI
		Parent parent = loader.load();
	
		// set the scene
		Scene scene = new Scene(parent);
	   	
    	// get the current window; i.e. the stage
		VendorDashboardController controller = loader.getController();
		
		controller.initData(loggedUser);
    	// change the title of the window
    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    	// change the title of the window
    	stage.setTitle("Travelfy - Dashboard");
    	// set the scene for the stage
    	stage.setResizable(false);
    	stage.setScene(scene);
    	
    	stage.show();
		} catch(Exception ex) {
			System.out.print(ex.getMessage());
		}
}
    
    public void travellerButtonListener() {
    	information1Label.setText("First Name:");
    	information2Label.setText("Last Name:");
    	information1TextField.setVisible(true);
    	information2TextField.setVisible(true); 
    	vendorButton.setStyle(defaultButtonStyle);
    	travellerButton.setStyle(selectedButtonStyle);
    }
    
    public void vendorButtonListener() {
    	information1Label.setText("Company Name:");
    	information2Label.setText("Business Identification Number:");
    	information1TextField.setVisible(true);
    	information2TextField.setVisible(true);
    	travellerButton.setStyle(defaultButtonStyle);
    	vendorButton.setStyle(selectedButtonStyle);
    }
    
    public void createAccountButtonListener(ActionEvent e) {
    	String userType = "";
    	if(passwordTextField.getText().isEmpty() || emailTextField.getText().isEmpty() || phoneTextField.getText().isEmpty()) {
    		Alert alert = new Alert(AlertType.WARNING);
	    	alert.setTitle("Create Account");
	    	alert.setContentText("The email, password and phone fields are required.");

	    	alert.showAndWait();
	    	return;
    	}
    	
    	if(!travellerButton.isSelected() && !vendorButton.isSelected()) {
    		Alert alert = new Alert(AlertType.WARNING);
	    	alert.setTitle("Create Account");
	    	alert.setContentText("The user type field is required.");

	    	alert.showAndWait();
	    	return;
    	}
    	
    	if(information1TextField.getText().isEmpty() || information2TextField.getText().isEmpty()) {
    		Alert alert = new Alert(AlertType.WARNING);
	    	alert.setTitle("Create Account");
	    	if(travellerButton.isSelected()) {
	    		userType = "Customer";
	    		alert.setContentText("The first and last name fields are required.");
	    	}
	    	else {
	    		userType = "Vendor";
	    		alert.setContentText("The company name and the BIN fields are required.");
	    	}
	    	
	    	alert.showAndWait();
	    	return;
    	}
    	
    	try {
    		userType = travellerButton.isSelected() ? "Customer":"Vendor";
    		UserDAOImpl UserDAO = new UserDAOImpl();

    		User loggedUser = new User();
    		
    		loggedUser = UserDAO.register(emailTextField.getText(),passwordTextField.getText(), phoneTextField.getText(), userType, information1TextField.getText(),  information2TextField.getText());
    		
    		if(loggedUser != null) {
    			navigate(loggedUser, e);
    		} else {
    			Alert alert = new Alert(AlertType.WARNING);
    	    	alert.setTitle("Create Account");
    	    	alert.setContentText("Account couldn't be created. Try again later");

    	    	alert.showAndWait();
    		}
    	} catch(Exception ex) {
 			System.out.print(ex.getMessage());
 		}
    	
    	
    }
    
    public void backButtonListener(ActionEvent e) {
    	try {
        	// Instantiate the FXMLLoader object for loading the UI 
        	FXMLLoader loader = new FXMLLoader();
        	// specify the file location
        	loader.setLocation(getClass().getResource("/travelfy/views/Login.fxml"));
    		// the object representing the root node of the scene; load the UI
    		Parent parent = loader.load();
   	
			// set the scene
			Scene scene = new Scene(parent);
	    	// change the title of the window
	    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
	    	// change the title of the window
	    	stage.setTitle("Travelfy - Dashboard");
	    	// set the scene for the stage
	    	stage.setScene(scene);
	    	
	    	stage.show();
			} catch (IOException ex) {
				System.out.print(ex.getMessage());
			}
    }

}
